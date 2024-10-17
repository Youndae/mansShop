package org.shop.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.dto.member.in.MemberJoinDTO;
import org.shop.domain.dto.member.in.MemberResetPwDTO;
import org.shop.domain.dto.member.in.MemberSearchIdDTO;
import org.shop.domain.dto.member.in.MemberSearchPwDTO;
import org.shop.domain.entity.Auth;
import org.shop.domain.entity.Member;
import org.shop.domain.enumeration.Result;
import org.shop.domain.enumeration.Role;
import org.shop.mapper.AuthMapper;
import org.shop.mapper.MemberMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Log4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberMapper memberMapper;

    private final AuthMapper authMapper;

    private final JavaMailSender javaMailSender;

    private final StringRedisTemplate redisTemplate;

    @Override
    public String checkJoinUserId(String userId) {
        int countValue = memberMapper.countByUserId(userId);

        return countValue == 0 ? Result.OK.getResultKey() : Result.DUPLICATE.getResultKey();
    }

    @Override
    public String checkJoinNickname(String nickname) {
        int countValue = memberMapper.countByNickname(nickname);

        return countValue == 0 ? Result.OK.getResultKey() : Result.DUPLICATE.getResultKey();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String join(MemberJoinDTO dto) {
        Member member = new Member(dto);
        memberMapper.join(member);
        authMapper.saveAuth(Auth.builder()
                                .userId(member.getUserId())
                                .auth(Role.MEMBER.getKey())
                                .build())
        ;

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public String searchId(MemberSearchIdDTO searchIdDTO) {
        if(searchIdDTO.getUserName() == null ||
                (searchIdDTO.getUserPhone() == null && searchIdDTO.getUserEmail() == null))
            return Result.FAIL.getResultKey();

        String userId = memberMapper.searchId(searchIdDTO.toEntity());

        return userId == null ? Result.NOTFOUND.getResultKey() : userId;
    }

    @Override
    public String searchPw(MemberSearchPwDTO searchPwDTO) {
        if(searchPwDTO.getUserId() == null || searchPwDTO.getUserName() == null)
            return null;

        int checkResult = memberMapper.checkUser(searchPwDTO.toEntity());

        if(checkResult == 0)
            return Result.FAIL.getResultKey();

        Random ran = new Random();
        int certificationNo = ran.nextInt(899999) + 100001;

        try{
            ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
            stringValueOperations.set(searchPwDTO.getUserId(), String.valueOf(certificationNo), 6L, TimeUnit.MINUTES);

            MimeMessage mailForm = createEmailForm(searchPwDTO.getUserEmail(), certificationNo);
            javaMailSender.send(mailForm);

            return Result.SUCCESS.getResultKey();
        }catch (Exception e){
            e.printStackTrace();
            return Result.ERROR.getResultKey();
        }
    }

    public MimeMessage createEmailForm(String userEmail, int certificationNo) throws MessagingException {
        String mailTitle = "Man's Shop 비밀번호 변경";

        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, userEmail);
        message.setSubject(mailTitle);

        String msgOfEmail="";
        msgOfEmail += "<div style='margin:20px;'>";
        msgOfEmail += "<h1> 안녕하세요 test 입니다. </h1>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p>아래 코드를 입력해주세요<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p>감사합니다.<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgOfEmail += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgOfEmail += "<div style='font-size:130%'>";
        msgOfEmail += "CODE : <strong>";
        msgOfEmail += certificationNo + "</strong><div><br/> ";
        msgOfEmail += "</div>";

        message.setText(msgOfEmail, "UTF-8", "html");

        return message;
    }

    @Override
    public String checkCno(MemberResetPwDTO resetPwDTO) {
        String result = null;

        try{
            ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
            result = stringValueOperations.get(resetPwDTO.getUserId());
        }catch(Exception e){
            log.error(e.getMessage());
            return Result.ERROR.getResultKey();
        }

        if(result != null && resetPwDTO.getCno() == Integer.parseInt(result))
            return Result.SUCCESS.getResultKey();

        return Result.FAIL.getResultKey();
    }

    @Override
    public String resetPw(MemberResetPwDTO resetPwDTO) {

        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        String certificationValue = stringValueOperations.get(resetPwDTO.getUserId());
        redisTemplate.delete(resetPwDTO.getUserId());

        if(certificationValue == null || Integer.parseInt(certificationValue) != resetPwDTO.getCno())
            return Result.ERROR.getResultKey();

        int modifyResult = memberMapper.modifyPw(new Member(resetPwDTO));

        if(modifyResult == 0)
            return Result.ERROR.getResultKey();

        return Result.SUCCESS.getResultKey();
    }
}
