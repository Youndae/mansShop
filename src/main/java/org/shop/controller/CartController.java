package org.shop.controller;

import lombok.RequiredArgsConstructor;
import org.shop.domain.dto.cart.business.CartMemberDTO;
import org.shop.domain.dto.cart.out.CartDetailResponseDTO;
import org.shop.service.CartService;
import org.shop.service.CookieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final CookieService cookieService;

    @GetMapping("/")
    public String cart(Model model, Principal principal, HttpServletRequest request, HttpServletResponse response) {
        CartMemberDTO cartMemberDTO = cookieService.createCartMemberDTOToCartDetail(principal, request, response);
        CartDetailResponseDTO dto = cartService.getCartDetail(cartMemberDTO);
        model.addAttribute("cart", dto);

        return "cart/cart";
    }
}
