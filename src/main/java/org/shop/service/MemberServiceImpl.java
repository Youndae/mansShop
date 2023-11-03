package org.shop.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.ResultProperties;
import org.shop.domain.dto.member.JoinDTO;
import org.shop.domain.dto.member.SearchIdDTO;
import org.shop.domain.entity.Member;
import org.shop.mapper.MemberMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final BCryptPasswordEncoder passwordEncoder;

    private final JavaMailSender javaMailSender;

    private final RedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public int join(JoinDTO dto) throws Exception {

        log.info("service join");

        Member member = Member.builder()
                .userId(dto.getUserId())
                .userPw(passwordEncoder.encode(dto.getUserPw()))
                .userName(dto.getUserName())
                .userEmail(dto.getUserEmail())
                .userBirth(dto.getUserBirth())
                .userPhone(dto.getUserPhone())
                .joinRegDate(dto.getJoinRegDate())
                .build();

        memberMapper.join(member);

        memberMapper.joinAuth(member.getUserId());

        return ResultProperties.SUCCESS;

    }

    @Override
    public String searchPw(String userId, String userName, String userEmail, String searchType) {

        if(userId == null || userName == null)
            return null;

        Member member = Member.builder()
                .userId(userId)
                .userName(userName)
                .userEmail(userEmail)
                .build();

        int checkResult = memberMapper.checkUser(member);

        if(checkResult == 0)
            return null;

        Random ran = new Random();

        int certificationNo = ran.nextInt(899999) + 100001;


        try{
            ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
            stringValueOperations.set(userId, String.valueOf(certificationNo), 6L, TimeUnit.MINUTES);

            MimeMessage mailForm = createEmailForm(userEmail, certificationNo);

            javaMailSender.send(mailForm);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }



       /*
       기존 DB에서 cno를 관리하던 코드

       Certify certify = Certify.builder()
                .cno(certificationNo)
                .userId(userId)
                .userName(userName)
                .userEmail(userEmail)
                .build();

        int insertResult = certifyMapper.addCertify(certify);

        if(insertResult == 1) {
            // 메일 전송
            try{
                MimeMessage mailForm = createEmailForm(userEmail, certificationNo);

                javaMailSender.send(mailForm);
            }catch (Exception e){
                e.printStackTrace();
                return "error";
            }

            return "success";
        }else
            return "error";*/
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
    public SearchIdDTO checkResetUser(SearchIdDTO dto) {

//        int result = certifyMapper.checkCertify(dto);

        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        String result = stringValueOperations.get(dto.getUserId());

        if(result != null)
            return dto;

        return null;
    }

    @Override
    public int resetPw(String userId, int cno, String password) {




        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();


        String certificationValue = stringValueOperations.get(userId);

        redisTemplate.delete(userId);

        int checkResult = Integer.parseInt(certificationValue);

        if(certificationValue == null || checkResult != cno)
            return 0;


        /*
        기존 DB에서 cno를 관리하던 코드

        int checkResult = certifyMapper.checkCertify(SearchIdDTO.builder()
                .userId(userId)
                .cno(cno)
                .build());

        if(checkResult == 0) {
            certifyMapper.deleteCertify(cno);
            return 0;
        }

        int certifyResult = certifyMapper.deleteCertify(cno);

        if(certifyResult == 0)
            return 0;*/

        password = passwordEncoder.encode(password);

        int modifyResult = memberMapper.modifyPw(Member.builder().userId(userId).userPw(password).build());

        if(modifyResult == 0)
            return 0;

        return 1;
    }
}
