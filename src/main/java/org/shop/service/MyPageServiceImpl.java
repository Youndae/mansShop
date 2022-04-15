package org.shop.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.CartDetailVO;
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
    public void deleteCart(CartVO cartVO, List<String> cdNoList) {

        /**
         * 전체삭제인지 아닌지 여부에 따라 cart 데이터를 삭제해야하는 상황이 생길 수 있으므로
         * 체크가 필요.
         *
         * count(*)로 해당 사용자의 데이터 개수를 체크하고 그것과 동일하다면 그냥 cart자체를 삭제하도록.
         * 어차피 cascade로 설정했기 때문에 cart만 삭제하는 쪽으로 하는것이 더 간단할듯.
         *
         * 전체삭제가 아니라면 기존 코드처럼 처리하는 방법으로 하면 될듯.
         */

        if(myPageMapper.cartCount(cartVO) == cdNoList.size()){ //전체 삭제라면
            //cart테이블에서 해당 아이디의 데이터 삭제
            log.info("all delete");
            myPageMapper.deleteCart(cartVO);
        }else{
            log.info("delete detail");
            for(int i = 0; i < cdNoList.size(); i++){
                String cdNo = cdNoList.get(i);

                myPageMapper.deleteCartDetail(cdNo);
            }
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
    public void cartCount(String cdNo, String cPrice, String countType) {

        CartDetailVO cartDetailVO = new CartDetailVO();
        cartDetailVO.setCdNo(cdNo);
        cartDetailVO.setCPrice(Long.parseLong(cPrice));

        log.info("count impl type : " + countType);

        if(countType.equals("up")){
            log.info("up");
            myPageMapper.cartUp(cartDetailVO);
        }else if(countType.equals("down")){
            log.info("down");
            myPageMapper.cartDown(cartDetailVO);
        }

        log.info("impl complete");
    }
}
