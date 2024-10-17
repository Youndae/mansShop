package org.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.shop.domain.dto.cart.business.CartMemberDTO;
import org.shop.domain.entity.Cart;
import org.shop.exception.customException.CustomAccessDeniedException;
import org.shop.exception.enumeration.ErrorCode;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class CookieServiceImpl implements CookieService{

    private final StringRedisTemplate redisTemplate;

    @Override
    public void createMemberCheckCookie(String userId, HttpServletResponse response) {
        String cookieValue = RandomStringUtils.random(20, true, true);
        Cookie cookie = new Cookie("checkCookie", cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(10 * 60);
        response.addCookie(cookie);

        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        stringValueOperations.set(userId, cookieValue, 10L, TimeUnit.MINUTES);
    }

    @Override
    public void checkMemberCheckCookie(String userId, HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "checkCookie");

        if(cookie == null)
            throw new CustomAccessDeniedException(ErrorCode.ACCESS_DENIED, ErrorCode.ACCESS_DENIED.getMessage());


        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        String redisResult = stringValueOperations.get(userId);

        if(!cookie.getValue().equals(redisResult))
            throw new CustomAccessDeniedException(ErrorCode.ACCESS_DENIED, ErrorCode.ACCESS_DENIED.getMessage());
    }


    /**
     * 장바구니에 담기 기능을 위한 cartMemberDTO 생성에서는 둘다 null인 경우 cookie 생성이 필요하다.
     * 장바구니 기능을 위한 cartMemberDTO 생성에서는 둘다 null인 경우 그대로 null값을 담아 반환하거나
     */

    @Override
    public CartMemberDTO createCartMemberDTOToAddCart(HttpServletRequest request, Principal principal, HttpServletResponse response) {

        CartMemberDTO cartMemberDTO = checkMember(principal, request, response);

        if(cartMemberDTO == null){
            Cookie cookie = createCookie();
            cookie = extendedCookie(cookie, response);
            cartMemberDTO = new CartMemberDTO(null, cookie.getValue());
        }

        return cartMemberDTO;
    }


    @Override
    public CartMemberDTO createCartMemberDTOToCartDetail(Principal principal, HttpServletRequest request, HttpServletResponse response) {

        return checkMember(principal, request, response);
    }

    public CartMemberDTO checkMember(Principal principal, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = WebUtils.getCookie(request, "cartCookie");
        String userId = null;

        if(principal != null){
            userId = principal.getName();

            if(cookie != null)
                deleteCookie(request, response);
        }else if(cookie != null)
            cookie = extendedCookie(cookie, response);
        else
            return null;

        return new CartMemberDTO(
                userId
                , cookie == null ? null : cookie.getValue()
        );
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
