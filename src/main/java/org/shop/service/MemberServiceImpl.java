package org.shop.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.MemberVO;
import org.shop.mapper.MemberMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Log4j
@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService{

    MemberMapper memberMapper;

    @Override
    public void join(MemberVO memberVO) throws Exception {

        log.info("service join");

        String bcryptPw = BCrypt.hashpw(memberVO.getUserPw(), BCrypt.gensalt());
        memberVO.setUserPw(bcryptPw);

        memberMapper.join(memberVO);

        memberMapper.joinAuth(memberVO.getUserId());
    }
}
