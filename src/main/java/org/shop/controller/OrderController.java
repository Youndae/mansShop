package org.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.dto.order.out.OrderProductResponseDTO;
import org.shop.service.CookieService;
import org.shop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequestMapping("/order")
@Controller
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    @GetMapping("/product")
    public String orderProduct(HttpSession session
                                , Model model) {

        OrderProductResponseDTO dto = (OrderProductResponseDTO) session.getAttribute("orderResponse");
        model.addAttribute("order", dto);

        return "order/orderPayment";
    }


    @GetMapping("/cart")
    public String orderCart(HttpSession session
                            , Model model) {

        OrderProductResponseDTO dto = (OrderProductResponseDTO) session.getAttribute("orderResponse");
        model.addAttribute("order", dto);

        return "order/orderPayment";
    }

    //결제 완료 페이지
    @GetMapping("/{oType}")
    public String orderComplete(@PathVariable("oType") String oType, Model model){
        model.addAttribute("type", oType);

        return "order/orderComplete";
    }
}
