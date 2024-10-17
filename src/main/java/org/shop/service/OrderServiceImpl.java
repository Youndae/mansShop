package org.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.dto.cart.business.CartMemberDTO;
import org.shop.domain.dto.cart.business.OptionAndCountListDTO;
import org.shop.domain.dto.order.in.ProductOrderListDTO;
import org.shop.domain.dto.order.OrderListRequestDTO;
import org.shop.domain.dto.order.ProductOrderDTO;
import org.shop.domain.dto.order.business.OrderCartDetailDTO;
import org.shop.domain.dto.order.business.OrderDetailDTO;
import org.shop.domain.dto.order.business.OrderProductPatchDTO;
import org.shop.domain.dto.order.business.UserOrderData;
import org.shop.domain.dto.order.in.OrderPaymentRequestDTO;
import org.shop.domain.dto.order.out.OrderListDTO;
import org.shop.domain.dto.order.out.OrderListResponseDTO;
import org.shop.domain.dto.order.out.OrderProductDTO;
import org.shop.domain.dto.order.out.OrderProductResponseDTO;
import org.shop.domain.dto.paging.PageCriteria;
import org.shop.domain.dto.paging.PageDTO;
import org.shop.domain.dto.paging.PagingResponseDTO;
import org.shop.domain.entity.*;
import org.shop.domain.enumeration.Result;
import org.shop.exception.customException.CustomAccessDeniedException;
import org.shop.exception.enumeration.ErrorCode;
import org.shop.mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final ProductOrderMapper productOrderMapper;

    private final ProductMapper productMapper;

    private final ProductOptionMapper productOptionMapper;

    private final CartDetailMapper cartDetailMapper;

    private final CartMapper cartMapper;

    private final ProductOrderDetailMapper productOrderDetailMapper;

    private static final String anonymous = "Anonymous";


    @Override
    public OrderProductResponseDTO orderProduct(OptionAndCountListDTO optionAndCountListDTO) {
        List<OrderProductDTO> products = productMapper.findOrderProductByOptionIds(optionAndCountListDTO);

        return new OrderProductResponseDTO(products);
    }

    @Override
    public OrderProductResponseDTO orderCart(List<Long> cartDetailIds, CartMemberDTO cartMemberDTO) {
        Long cartId = cartMapper.findCartIdByUserIdOrCookieId(cartMemberDTO);
        Long checkCartId = cartDetailMapper.findCartIdByCartIdAndDetailId(cartDetailIds.get(0));

        if(cartId == null || !cartId.equals(checkCartId))
            throw new CustomAccessDeniedException(ErrorCode.ACCESS_DENIED, ErrorCode.ACCESS_DENIED.getMessage());

        List<OrderProductDTO> products = cartDetailMapper.findOrderProductByOptionIds(cartDetailIds);

        return new OrderProductResponseDTO(products);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String orderPaymentProduct(OrderProductResponseDTO dto, OrderPaymentRequestDTO paymentDTO, Principal principal) {
        saveOrderAndDetails(dto, paymentDTO, principal == null ? anonymous : principal.getName());
        patchProductAndOption(dto.getProducts());

        return Result.SUCCESS.getResultKey();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String orderPaymentCart(OrderProductResponseDTO dto, OrderPaymentRequestDTO paymentDTO, CartMemberDTO cartMemberDTO) {
        saveOrderAndDetails(dto, paymentDTO, cartMemberDTO.getUserId() == null ? anonymous : cartMemberDTO.getUserId());
        patchProductAndOption(dto.getProducts());

        List<OrderCartDetailDTO> detailList = cartDetailMapper.findAllDetailByUserIdOrCookieId(cartMemberDTO);
        List<Long> deleteList = new ArrayList<>();
        for(OrderProductDTO productDTO : dto.getProducts()) {
            for(OrderCartDetailDTO detail : detailList) {
                if(productDTO.getProductOptionId() == detail.getOptionId()){
                    deleteList.add(detail.getDetailId());
                    break;
                }
            }
        }

        if(deleteList.size() == detailList.size())
            cartMapper.deleteById(detailList.get(0).getCartId());
        else
            cartDetailMapper.deleteByIds(deleteList);

        return Result.SUCCESS.getResultKey();
    }

    public void saveOrderAndDetails(OrderProductResponseDTO dto, OrderPaymentRequestDTO paymentDTO, String userId) {
        ProductOrder orderEntity = new ProductOrder(paymentDTO, dto, userId);
        productOrderMapper.saveOrder(orderEntity);
        Long orderId = orderEntity.getId();
        List<ProductOrderDetail> orderDetails = dto.getProducts()
                                                    .stream()
                                                    .map(v -> new ProductOrderDetail(v, orderId))
                                                    .collect(Collectors.toList());

        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("orderDetails", orderDetails);

        productOrderDetailMapper.saveOrderDetails(params);
    }

    public void patchProductAndOption(List<OrderProductDTO> list) {
        Map<String, Integer> productPatchMap = new HashMap<>();
        List<OrderProductPatchDTO<Long>> optionPatchList = new ArrayList<>();

        for(OrderProductDTO dto : list){
            productPatchMap.put(dto.getProductId(), productPatchMap.getOrDefault(dto.getProductId(), 0) + dto.getCount());
            optionPatchList.add(new OrderProductPatchDTO<>(dto.getProductOptionId(), dto.getCount()));
        }

        List<OrderProductPatchDTO<String>> productPatchList = productPatchMap.keySet()
                .stream()
                .map(k ->
                        new OrderProductPatchDTO<>(k, productPatchMap.get(k))
                )
                .collect(Collectors.toList());

        productMapper.patchProductToOrder(productPatchList);
        productOptionMapper.patchProductOptionToOrder(optionPatchList);
    }

    @Override
    public OrderListResponseDTO<OrderListDTO> getAnonymousOrderList(ProductOrderListDTO dto, OrderListRequestDTO orderDTO) {
        List<ProductOrder> orderList = productOrderMapper.findOrderList(dto, orderDTO);
        int totalElements = productOrderMapper.findOrderListCount(dto, orderDTO);
        List<OrderListDTO> content = orderList.size() == 0 ? null : mappingOrderListContent(orderList);
        PageCriteria cri = createOrderListPageCriteria(orderDTO);

        return new OrderListResponseDTO<>(
                content
                , new PageDTO<>(cri, totalElements)
                , new UserOrderData(dto.getRecipient(), dto.getOrderPhone())
        );
    }

    @Override
    public PagingResponseDTO<OrderListDTO> getMemberOrderList(ProductOrderListDTO dto, OrderListRequestDTO orderDTO) {
        List<ProductOrder> orderList = productOrderMapper.findOrderList(dto, orderDTO);
        int totalElements = productOrderMapper.findOrderListCount(dto, orderDTO);
        List<OrderListDTO> content = orderList.size() == 0 ? null : mappingOrderListContent(orderList);
        PageCriteria cri = createOrderListPageCriteria(orderDTO);

        return new PagingResponseDTO<>(content, new PageDTO<>(cri, totalElements));
    }

    public List<OrderListDTO> mappingOrderListContent(List<ProductOrder> orderList) {
        List<Long> orderIdList = orderList.stream().map(ProductOrder::getId).collect(Collectors.toList());
        List<OrderDetailDTO> detailList = productOrderDetailMapper.findByDetailList(orderIdList);
        List<OrderListDTO> content = new ArrayList<>();

        for(ProductOrder entity : orderList){
            List<OrderDetailDTO> details = detailList.stream()
                    .filter(v -> entity.getId() == v.getOrderId())
                    .collect(Collectors.toList());

            content.add(new OrderListDTO(entity, details));
        }

        return content;
    }

    public PageCriteria createOrderListPageCriteria(OrderListRequestDTO dto) {
        return new PageCriteria(dto.getPage(), dto.getAmount(), dto.getTermValue(), null);
    }
}
