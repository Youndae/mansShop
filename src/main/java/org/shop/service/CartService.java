package org.shop.service;

import org.shop.domain.entity.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

public interface CartService {

    public String deleteCartCheck(List<String> cdNoList, Principal principal, HttpServletRequest request, HttpServletResponse response);

    public String cartCount(String cdNo, String cPrice, String countType);
}
