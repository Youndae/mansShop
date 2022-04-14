package org.shop.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.CartVO;
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

    @Override
    public void cartCount(String pOpNo, String cPrice, String countType, Principal principal) {

        /*CartVO cartVO = new CartVO();

        log.info("count impl type : " + countType);

        if(countType.equals("up")){
            log.info("up?");

                cartVO.setPOpNo(pOpNo);
                cartVO.setUserId(principal.getName());
                cartVO.setCPrice(Long.parseLong(cPrice));
                log.info("up!");
                myPageMapper.cartUp(cartVO);

        }else if(countType.equals("down")){
            log.info("down");
                cartVO.setPOpNo(pOpNo);
                cartVO.setUserId(principal.getName());
                cartVO.setCPrice(Long.parseLong(cPrice));
                myPageMapper.cartDown(cartVO);

        }*/

        log.info("impl complete");
    }
}
