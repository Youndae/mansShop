package org.shop.service;

import org.shop.domain.CartDetailVO;
import org.shop.domain.CartVO;
import org.shop.domain.OrderVO;

import javax.servlet.http.Cookie;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

public interface OrderService {

    public List<CartDetailVO> orderProduct(HashMap<String, Object> commandMap);

    public void orderPayment(OrderVO orderVO, List<String> cdNo, List<String> pOpNo, List<String> orderCount, List<String> odPrice, List<String> pno, String oType, Cookie cookie);
}
