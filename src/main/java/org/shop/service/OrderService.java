package org.shop.service;

import org.shop.domain.dto.order.OrderPaymentDTO;
import org.shop.domain.dto.order.ProductOrderDTO;
import org.shop.domain.entity.CartDetail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

public interface OrderService {

    public List<OrderPaymentDTO> orderProduct(HashMap<String, Object> commandMap);

    public String orderPayment(ProductOrderDTO dto
                                , List<String> cdNo
                                , List<String> pOpNo
                                , List<String> orderCount
                                , List<String> odPrice
                                , List<String> pno
                                , String oType
                                , HttpServletRequest request
                                , HttpServletResponse response
                                , Principal principal);
}
