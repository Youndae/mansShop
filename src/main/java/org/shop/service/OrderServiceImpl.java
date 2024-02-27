package org.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.ResultProperties;
import org.shop.domain.dto.order.OrderPaymentDTO;
import org.shop.domain.dto.order.ProductOrderDTO;
import org.shop.domain.entity.ProductOrder;
import org.shop.domain.entity.ProductOrderDetail;
import org.shop.domain.entity.Sales;
import org.shop.mapper.OrderMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Log4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderMapper orderMapper;

    private final CartService cartService;

    @Override
    public List<OrderPaymentDTO> orderProduct(HashMap<String, Object> commandMap) {

        log.info("order payments impl");

        String[] no_array = splitMapData("pOpNo", commandMap);
        String[] name_array = splitMapData("pName", commandMap);
        String[] size_array = splitMapData("pSize", commandMap);
        String[] color_array = splitMapData("pColor", commandMap);
        String[] count_array = splitMapData("cCount", commandMap);
        String[] price_array = splitMapData("cPrice", commandMap);
        String[] pno_array = splitMapData("pno", commandMap);
        String[] cd_array = splitMapData("cdNo", commandMap);

        List<OrderPaymentDTO> dtoList = new ArrayList<>();

        for(int i = 0; i < no_array.length; i++){
            OrderPaymentDTO dto = OrderPaymentDTO.builder()
                    .pOpNo(no_array[i])
                    .pName(name_array[i])
                    .cCount(Integer.parseInt(count_array[i]))
                    .cPrice(Long.parseLong(price_array[i]))
                    .pno(pno_array[i])
                    .build();

            if(size_array.length != 0 && size_array[i] != null){
                dto.setPSize(size_array[i]);
            }

            if(color_array.length != 0 && color_array[i] != null){
                dto.setPColor(color_array[i]);
            }

            if(cd_array != null){
                dto.setCdNo(cd_array[i]);
            }

            dtoList.add(dto);
        }

        return dtoList;
    }


    public String[] splitMapData(String key, HashMap<String, Object> commandMap){
        if(commandMap.get(key) != null)
            return commandMap.get(key).toString().split(",");

        return null;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int orderPayment(ProductOrderDTO dto
                            , List<String> cdNo
                            , List<String> pOpNo
                            , List<String> orderCount
                            , List<String> odPrice
                            , List<String> pno
                            , String oType
                            , HttpServletRequest request
                            , HttpServletResponse response
                            , Principal principal) throws Exception{
        String id;

        if(principal == null)
            id = "Anonymous";
        else
            id = principal.getName();

        ProductOrder productOrder = ProductOrder.builder()
                .userId(id)
                .addr(dto.getAddr())
                .orderPhone(dto.getOrderPhone())
                .orderMemo(dto.getOrderMemo())
                .orderPrice(dto.getOrderPrice())
                .orderPayment(dto.getOrderPayment())
                .recipient(dto.getRecipient())
                .build();

        long totalCount = 0;
        List<ProductOrderDetail> orderDetailList = new ArrayList<>();

        for(int i = 0; i < pOpNo.size(); i++){
            orderDetailList.add(ProductOrderDetail.builder()
                    .odNo(productOrder.getOrderNo() + pOpNo.get(i))
                    .orderNo(productOrder.getOrderNo())
                    .pOpNo(pOpNo.get(i))
                    .orderCount(Integer.parseInt(orderCount.get(i)))
                    .odPrice(Integer.parseInt(odPrice.get(i)))
                    .pno(pno.get(i))
                    .build());

            totalCount = totalCount + Long.parseLong(orderCount.get(i));
        }

        orderMapper.orderPayment(productOrder);
        orderMapper.orderPaymentOp(orderDetailList);
        orderMapper.productSales(orderDetailList);
        orderMapper.productOpSales(orderDetailList);
        sales(productOrder.getOrderPrice(), totalCount);

        if(oType != "d")
            cartService.deleteCart(cdNo, principal, request, response);


        return ResultProperties.SUCCESS;
    }

    public void sales(long orderPrice, long totalCount) {
        String now = new SimpleDateFormat("yyyy/MM").format(System.currentTimeMillis()).toString();
        int salesTermCount = orderMapper.maxSalesTerm(now);

        if(orderPrice < 100000)
            orderPrice -= 2500;

        Sales sales = Sales.builder()
                            .salesOrders(totalCount)
                            .salesSum(orderPrice)
                            .salesTerm(now)
                            .build();

        if(salesTermCount != 1)
            orderMapper.addTotalSales(sales);
        else
            orderMapper.updateTotalSales(sales);

    }
}
