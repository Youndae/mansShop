package org.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.ResultProperties;
import org.shop.domain.entity.Cart;
import org.shop.domain.entity.CartDetail;
import org.shop.mapper.MyPageMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService{

    private final CookieService cookieService;

    private final MyPageMapper myPageMapper;

    @Override
    public String deleteCart(List<String> cdNoList, Principal principal, HttpServletRequest request, HttpServletResponse response) {
        /**
         * 장바구니 데이터 삭제 시 전체삭제라면 true
         * 부분 삭제라면 false를 리턴
         *
         * @Param
         * cart -> 데이터 조회를 위해 필요.
         *
         *
         *
         *
         * 장바구니 삭제.
         * Cart, cdNoList
         *
         * 전체 삭제하면 cart를 넘겨 삭제.
         * 일부 삭제라면 cdNoList를 넘겨 삭제.
         */

        new Exception("Exception 발생!!");

        return "";


/*

        Cart cart;

        try{
            cart = cookieService.checkCookie(request, principal, response, false);
        }catch (Exception e){
            e.printStackTrace();
            return ResultProperties.ERROR;
        }

        try{
            if(myPageMapper.cartCount(cart) == cdNoList.size()){ //전체 삭제라면
                //cart테이블에서 해당 아이디의 데이터 삭제
                myPageMapper.deleteCart(cart);

                //비회원이 전체 삭제를 한 경우에는 쿠키를 삭제한다.
                if(cart.getUserId() == null)
                    cookieService.deleteCookie(request, response);

            }else{
                myPageMapper.deleteCartDetail(cdNoList);
            }

            return ResultProperties.SUCCESS;
        }catch (Exception e) {
            log.error("deleteCart Exception : " + e.getMessage());
            return ResultProperties.ERROR;
        }*/
    }

    @Override
    public String cartCount(String cdNo, String cPrice, String countType) {

        try{
            CartDetail cartDetail = CartDetail.builder()
                    .cdNo(cdNo)
                    .cPrice(Long.parseLong(cPrice))
                    .build();

            if(countType.equals("up"))
                myPageMapper.cartUp(cartDetail);
            else if(countType.equals("down"))
                myPageMapper.cartDown(cartDetail);

            return ResultProperties.SUCCESS;
        }catch (Exception e){
            log.error("cartCount Exception : " + e.getMessage());
            return ResultProperties.ERROR;
        }
    }
}
