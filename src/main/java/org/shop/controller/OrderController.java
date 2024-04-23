package org.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.ResultProperties;
import org.shop.domain.dto.order.ProductOrderDTO;
import org.shop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/order")
@Controller
@Log4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //주문 페이지
    @PostMapping("/")
    public String orderPayment(@RequestParam HashMap<String, Object> commandMap, Model model){
        model.addAttribute("oList", orderService.orderProduct(commandMap));
        model.addAttribute("orderType", commandMap.get("oType").toString());

        return "order/orderPayment";
    }

    //결제 완료 페이지
    @GetMapping("/{oType}")
    public String orderComplete(@PathVariable("oType") String oType, Model model){
        model.addAttribute("type", oType);

        return "order/orderComplete";
    }

    //결제 처리
    @PostMapping("/payment")
    @ResponseBody
    public String payment(ProductOrderDTO dto
                        , @RequestParam("oType") String oType
                        , @RequestParam("cdNo") List<String> cdNo
                        , @RequestParam("pOpNo") List<String> pOpNo
                        , @RequestParam("orderCount")List<String> orderCount
                        , @RequestParam("odPrice")List<String> odPrice
                        , @RequestParam("pno")List<String> pno
                        , Principal principal
                        , HttpServletRequest request
                        , HttpServletResponse response){


        return orderService.orderPayment(dto, cdNo, pOpNo, orderCount, odPrice, pno, oType, request, response, principal);
    }
}
