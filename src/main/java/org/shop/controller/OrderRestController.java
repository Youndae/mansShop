package org.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.dto.cart.business.CartMemberDTO;
import org.shop.domain.dto.cart.business.OptionAndCountListDTO;
import org.shop.domain.dto.order.in.OrderPaymentRequestDTO;
import org.shop.domain.dto.order.out.OrderProductResponseDTO;
import org.shop.domain.enumeration.Result;
import org.shop.service.CookieService;
import org.shop.service.OrderService;
import org.shop.service.PrincipalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;

    private final CookieService cookieService;

    @PostMapping("/product")
    public ResponseEntity<String> orderProduct(@RequestBody OptionAndCountListDTO optionAndCountListDTO
                                                , HttpSession session) {
        OrderProductResponseDTO dto = orderService.orderProduct(optionAndCountListDTO);

        session.setAttribute("orderResponse", dto);

        return new ResponseEntity<>(Result.SUCCESS.getResultKey(), HttpStatus.OK);
    }

    @PostMapping("/cart")
    public ResponseEntity<String> orderCart(@RequestBody List<Long> cartDetailIds
                                            , Principal principal
                                            , HttpServletRequest request
                                            , HttpServletResponse response
                                            , HttpSession session) {
        CartMemberDTO cartMemberDTO = cookieService.createCartMemberDTOToCartDetail(principal, request, response);
        OrderProductResponseDTO dto = orderService.orderCart(cartDetailIds, cartMemberDTO);

        session.setAttribute("orderResponse", dto);

        return new ResponseEntity<>(Result.SUCCESS.getResultKey(), HttpStatus.OK);
    }

    @PostMapping("/payment/product")
    public ResponseEntity<String> paymentProduct(HttpSession session
                                        , @RequestBody OrderPaymentRequestDTO paymentDTO
                                        , Principal principal) {
        OrderProductResponseDTO dto = (OrderProductResponseDTO) session.getAttribute("orderResponse");
        String result = orderService.orderPaymentProduct(dto, paymentDTO, principal);
        session.removeAttribute("orderResponse");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/payment/cart")
    public ResponseEntity<String> paymentCart(HttpSession session
                                            , @RequestBody OrderPaymentRequestDTO paymentDTO
                                            , Principal principal
                                            , HttpServletRequest request
                                            , HttpServletResponse response) {
        CartMemberDTO cartMemberDTO = cookieService.createCartMemberDTOToCartDetail(principal, request, response);
        OrderProductResponseDTO dto = (OrderProductResponseDTO) session.getAttribute("orderResponse");
        String result = orderService.orderPaymentCart(dto, paymentDTO, cartMemberDTO);
        session.removeAttribute("orderResponse");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
