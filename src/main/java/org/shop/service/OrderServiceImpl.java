package org.shop.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.ResultProperties;
import org.shop.domain.dto.order.OrderPaymentDTO;
import org.shop.domain.dto.order.ProductOrderDTO;
import org.shop.domain.entity.*;
import org.shop.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Log4j
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{

    private OrderMapper orderMapper;

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
            log.info("order payments each");
            log.info("size_array length : " + size_array.length);

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

        log.info("Last VO : " + dtoList);

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
                            , Principal principal) throws Exception{


        StringBuffer sb =  new StringBuffer();

        String orderNo = sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())).toString();

        String id;

        if(principal == null){
            id = "Anonymous";
        }else{
            id = principal.getName();
        }

        ProductOrder productOrder = ProductOrder.builder()
                .orderNo(orderNo)
                .userId(id)
                .addr(dto.getAddr())
                .orderPhone(dto.getOrderPhone())
                .orderMemo(dto.getOrderMemo())
                .orderPrice(dto.getOrderPrice())
                .orderPayment(dto.getOrderPayment())
                .recipient(dto.getRecipient())
                .build();

        orderMapper.orderPayment(productOrder);

        long totalCount = 0;

        List<ProductOrderDetail> orderDetailList = new ArrayList<>();

        for(int i = 0; i < pOpNo.size(); i++){

            orderDetailList.add(ProductOrderDetail.builder()
                    .odNo(orderNo + pOpNo.get(i))
                    .orderNo(orderNo)
                    .pOpNo(pOpNo.get(i))
                    .orderCount(Integer.parseInt(orderCount.get(i)))
                    .odPrice(Integer.parseInt(odPrice.get(i)))
                    .pno(pno.get(i))
                    .build());

            totalCount = totalCount + Long.parseLong(orderCount.get(i));
        }

        orderMapper.orderPaymentOp(orderDetailList);
        orderMapper.productSales(orderDetailList);
        orderMapper.productOpSales(orderDetailList);

        String now = new SimpleDateFormat("yyyy/MM").format(System.currentTimeMillis()).toString();

        int salesTermCount = orderMapper.maxSalesTerm(now);
        log.info("max salesTerm : " + salesTermCount);

        long salesSum;

        if(productOrder.getOrderPrice() < 100000){
            salesSum = productOrder.getOrderPrice() - 2500;
        }else{
            salesSum = productOrder.getOrderPrice();
        }

        Sales sales = Sales.builder()
                .salesOrders(totalCount)
                .salesSum(salesSum)
                .salesTerm(now)
                .build();

        if(salesTermCount != 1){
            log.info("salesTerm null");

            orderMapper.addTotalSales(sales);
        }else{
            log.info("salesTerm not null");
            orderMapper.updateTotalSales(sales);
        }


        //카트에서 결제된 상품은 삭제

        Cookie cookie = WebUtils.getCookie(request, "cartCookie");

        if(oType != "d"){ //oType = d는 장바구니를 거치지 않은 상품 정보에서 바로 구매한 상품.

            Cart cart = new Cart();

            if(cookie != null){ //비회원이라면
                cart.setCkId(cookie.getValue());
            }else{
                cart.setUserId(productOrder.getUserId());
            }

            if(orderMapper.deleteCartCheck(cart) == cdNo.size()){ //장바구니 상품을 전체 구매한 경우라면
                orderMapper.deleteOrderCart(cart); //cart 테이블에서 해당 사용자 데이터 삭제
            }else{

                orderMapper.deleteOrderCartDetail(cdNo); //cartDetail 테이블에서 해당 데이터만 삭제

            }
        }
        return ResultProperties.SUCCESS;

    }

}
