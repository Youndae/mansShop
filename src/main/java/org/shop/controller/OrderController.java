package org.shop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.OrderVO;
import org.shop.mapper.OrderMapper;
import org.shop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/order")
@Controller
@Log4j
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    private OrderMapper orderMapper;

    @PostMapping("/orderPayment")
    public void orderPayment(@RequestParam HashMap<String, Object> commandMap, Model model){

        log.info("order payments");
        model.addAttribute("oList", orderService.orderProduct(commandMap));

    }

    @GetMapping("/orderComplete")
    public void orderComplete(){

    }

    @PostMapping("/payment")
    public void payment(OrderVO orderVO,
                        @RequestParam("pOpNo") List<String> pOpNo,
                        @RequestParam("orderCount")List<String> orderCount,
                        @RequestParam("odPrice")List<String> odPrice,
                        Principal principal){
        //결제처리

        orderVO.setUserId(principal.getName());

        log.info("payment : " + orderVO);

        log.info("pOpNo : " + pOpNo);
        log.info("orderCount : " + orderCount);
        log.info("odPrice : " + odPrice);

        orderService.orderPayment(orderVO, pOpNo, orderCount, odPrice);

    }


}
