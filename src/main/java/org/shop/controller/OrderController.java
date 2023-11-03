package org.shop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.ResultProperties;
import org.shop.domain.dto.order.ProductOrderDTO;
import org.shop.mapper.OrderMapper;
import org.shop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/order")
@Controller
@Log4j
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    //주문 페이지
    @PostMapping("/orderPayment")
    public void orderPayment(@RequestParam HashMap<String, Object> commandMap, Model model){

        log.info("order payments");
        model.addAttribute("oList", orderService.orderProduct(commandMap));
        model.addAttribute("orderType", commandMap.get("oType").toString());

    }

    //결제 완료 페이지
    @GetMapping("/orderComplete/{oType}")
    public String orderComplete(@PathVariable("oType") String oType, Model model){

        log.info("orderComplete");
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
                        , HttpServletRequest request){

        log.info("oType : " + oType);

        log.info("pOpNo : " + pOpNo);
        log.info("orderCount : " + orderCount);
        log.info("odPrice : " + odPrice);

        int result;

        try{
            result = orderService.orderPayment(dto, cdNo, pOpNo, orderCount, odPrice, pno, oType, request, principal);
        }catch (Exception e){
            result = ResultProperties.ERROR;
        }

        return String.valueOf(result);
    }


}
