package org.shop.service;

import org.shop.domain.dto.cart.business.CartMemberDTO;
import org.shop.domain.dto.cart.business.OptionAndCountListDTO;
import org.shop.domain.dto.order.in.ProductOrderListDTO;
import org.shop.domain.dto.order.OrderListRequestDTO;
import org.shop.domain.dto.order.ProductOrderDTO;
import org.shop.domain.dto.order.in.OrderPaymentRequestDTO;
import org.shop.domain.dto.order.out.OrderListDTO;
import org.shop.domain.dto.order.out.OrderListResponseDTO;
import org.shop.domain.dto.order.out.OrderProductResponseDTO;
import org.shop.domain.dto.paging.PagingResponseDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

public interface OrderService {

    OrderProductResponseDTO orderProduct(OptionAndCountListDTO optionAndCountListDTO);

    OrderProductResponseDTO orderCart(List<Long> cartDetailIds, CartMemberDTO cartMemberDTO);


    String orderPaymentProduct(OrderProductResponseDTO dto, OrderPaymentRequestDTO paymentDTO, Principal principal);

    String orderPaymentCart(OrderProductResponseDTO dto, OrderPaymentRequestDTO paymentDTO, CartMemberDTO cartMemberDTO);

    OrderListResponseDTO<OrderListDTO> getAnonymousOrderList(ProductOrderListDTO dto, OrderListRequestDTO orderDTO);

    PagingResponseDTO<OrderListDTO> getMemberOrderList(ProductOrderListDTO dto, OrderListRequestDTO orderDTO);
}
