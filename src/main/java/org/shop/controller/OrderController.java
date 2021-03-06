package org.shop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.OrderVO;
import org.shop.mapper.OrderMapper;
import org.shop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
        //주문 페이지
        log.info("order payments");
        model.addAttribute("oList", orderService.orderProduct(commandMap));
        model.addAttribute("orderType", commandMap.get("oType").toString());

    }

    @GetMapping("/orderComplete/{oType}")
    public String orderComplete(@PathVariable("oType") String oType, Model model){
        //주문 완료 페이지
        log.info("orderComplete");
        model.addAttribute("type", oType);

        return "order/orderComplete";
    }



    @PostMapping("/payment")
    @ResponseBody
    public void payment(OrderVO orderVO, @RequestParam("oType") String oType,
                        @RequestParam("cdNo") List<String> cdNo,
                        @RequestParam("pOpNo") List<String> pOpNo,
                        @RequestParam("orderCount")List<String> orderCount,
                        @RequestParam("odPrice")List<String> odPrice,
                        @RequestParam("pno")List<String> pno,
                        Principal principal,
                        HttpServletRequest request){
        //결제처리

        String id = null;

        log.info("oType : " + oType);

        Cookie cookie = WebUtils.getCookie(request, "cartCookie");

        if(principal == null){
            id = "Anonymous";
        }else{
            id = principal.getName();
        }


        orderVO.setUserId(id);

        log.info("userId : " + orderVO.getUserId());

        log.info("payment : " + orderVO);

        log.info("pOpNo : " + pOpNo);
        log.info("orderCount : " + orderCount);
        log.info("odPrice : " + odPrice);

        orderService.orderPayment(orderVO, cdNo, pOpNo, orderCount, odPrice, pno, oType, cookie);

    }


}
