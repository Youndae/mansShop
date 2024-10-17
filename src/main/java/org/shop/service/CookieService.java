package org.shop.service;

import org.shop.domain.dto.cart.business.CartMemberDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

public interface CookieService {

    CartMemberDTO createCartMemberDTOToAddCart(HttpServletRequest request, Principal principal, HttpServletResponse response);

    CartMemberDTO createCartMemberDTOToCartDetail(Principal principal, HttpServletRequest request, HttpServletResponse response);

    void deleteCookie(HttpServletRequest request, HttpServletResponse response);

    void createMemberCheckCookie(String userId, HttpServletResponse response);

    void checkMemberCheckCookie(String userId, HttpServletRequest request);
}
