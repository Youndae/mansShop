package org.shop.service;

import org.shop.domain.entity.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

public interface CookieService {

    Cart checkCookie(HttpServletRequest request, Principal principal, HttpServletResponse response, boolean addStat);

    void deleteCookie(HttpServletRequest request, HttpServletResponse response);
}
