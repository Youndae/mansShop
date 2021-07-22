package org.shop.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.MemberVO;
import org.shop.mapper.MyPageMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.security.Principal;
import java.util.List;

@Service
@Log4j
@AllArgsConstructor
public class MyPageServiceImpl implements MyPageService{

    private MyPageMapper myPageMapper;
    private PasswordEncoder pwEncoder;

    @Override
    public void deleteCart(String id, List<String> pOpNoList) {
        for(int i = 0; i < pOpNoList.size(); i++){
            String pOpNo = pOpNoList.get(i);

            myPageMapper.deleteCart(id, pOpNo);
        }
    }

    @Override
    public int modifyCheckProc(MemberVO memberVO) {

        log.info("modifyCheckProc");

        String checkPw = myPageMapper.modifyCheck(memberVO);

        if(pwEncoder.matches(memberVO.getUserPw(), checkPw)){
            log.info("true");
            return 1;
        }else{
            log.info("false");
            return 0;
        }


    }
}
