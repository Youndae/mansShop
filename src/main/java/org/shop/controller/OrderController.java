package org.shop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.mapper.OrderMapper;
import org.shop.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/order")
@RestController
@Log4j
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    private OrderMapper orderMapper;

    @PostMapping("/orderPayment")
    public void orderPayment(){
        //결제창
        //배송정보 입력하는 화면
    }

    @PostMapping("/payment")
    public String payment(){
        //결제처리
        return "";
    }


}
