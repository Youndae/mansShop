package org.shop.service;

import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.shop.domain.entity.Cart;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Service
@Log4j
public class CookieServiceImpl implements CookieService{


    @Override
    public Cart checkCookie(HttpServletRequest request, Principal principal, HttpServletResponse response, boolean addStat) {

        Cookie cookie = WebUtils.getCookie(request, "cartCookie");

        Cart cart = new Cart();

        if(principal != null){//회원이라면 아이디값만 있으면 되기 때문에 아이디 값만 set
            cart = Cart.builder().userId(principal.getName()).build();
        }else if(addStat){//장바구니 추가

            if(cookie == null)//쿠키가 존재하지 않는 사용자라면
                cookie = createCookie();

            cookie = extendedCookie(cookie, response);

            cart = Cart.builder().ckId(cookie.getValue()).userId("Anonymous").build();

        }else {// 장바구니 페이지 및 delete
            if(cookie != null){//쿠키가 존재하는 사용자라면
                cookie = extendedCookie(cookie, response);

                cart = Cart.builder().ckId(cookie.getValue()).userId(null).build();
            }
            //장바구니 페이지에 접근 시 쿠키가 존재하지 않는다면 데이터가 없으므로
            //굳이 처리하지 않는다.
        }

        return cart;

    }

    @Override
    public void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = WebUtils.getCookie(request, "cartCookie");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public Cookie createCookie(){
        //쿠키 생성
        log.info("create Cookie");
        String ckId = RandomStringUtils.random(8, true, true);
        Cookie cookie = new Cookie("cartCookie", ckId);

        return cookie;
    }

    public Cookie extendedCookie(Cookie cookie, HttpServletResponse response){
        //쿠키 기간 설정 및 연장
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);

        return cookie;
    }


}
