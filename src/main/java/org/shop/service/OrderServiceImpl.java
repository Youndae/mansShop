package org.shop.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.CartVO;
import org.shop.domain.OrderDetailVO;
import org.shop.domain.OrderVO;
import org.shop.domain.SalesVO;
import org.shop.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Log4j
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{

    private OrderMapper orderMapper;

    @Override
    public List<CartVO> orderProduct(HashMap<String, Object> commandMap) {

        log.info("order payments impl");

        String[] no_array = null;

        String pOpNo = commandMap.get("pOpNo").toString();
        no_array = pOpNo.split(",");


        String[] name_array = null;
        String pName = commandMap.get("pName").toString();
        name_array = pName.split(",");



        String[] size_array = null;
        String pSize = commandMap.get("pSize").toString();
        size_array = pSize.split(",");



        String[] color_array = null;
        String pColor = commandMap.get("pColor").toString();
        color_array = pColor.split(",");



        String[] count_array = null;
        String cCount = commandMap.get("cCount").toString();
        count_array = cCount.split(",");



        String[] price_array = null;
        String cPrice = commandMap.get("cPrice").toString();
        price_array = cPrice.split(",");

        List<CartVO> vo = new ArrayList<>();

        for(int i = 0; i < no_array.length; i++){
            log.info("order payments each");
            log.info("size_array length : " + size_array.length);
            CartVO cartVO = new CartVO();

            cartVO.setPOpNo(no_array[i]);
            cartVO.setPName(name_array[i]);
            cartVO.setCCount(Long.parseLong(count_array[i]));
            cartVO.setCPrice(Long.parseLong(price_array[i]));

            if(size_array.length != 0 && size_array[i] != null){
                cartVO.setPSize(size_array[i]);
            }

            if(color_array.length != 0 && color_array[i] != null){
                cartVO.setPColor(color_array[i]);
            }

            vo.add(cartVO);
        }

        log.info("Last VO : " + vo);

        return vo;
    }


    @Override
    public void orderPayment(OrderVO orderVO, List<String> pOpNo, List<String> orderCount, List<String> odPrice, String oType) {
        StringBuffer sb =  new StringBuffer();

        String orderNo = sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())).toString();

        orderVO.setOrderNo(orderNo);



        orderMapper.orderPayment(orderVO);

        long totalCount = 0;

        for(int i = 0; i < pOpNo.size(); i++){
            OrderDetailVO orderDetailVO = new OrderDetailVO();

            orderDetailVO.setOdNo(orderNo+pOpNo.get(i));
            orderDetailVO.setOrderNo(orderNo);
            orderDetailVO.setPOpNo(pOpNo.get(i));
            orderDetailVO.setOrderCount(Integer.parseInt(orderCount.get(i)));
            orderDetailVO.setOdPrice(Integer.parseInt(odPrice.get(i)));

            orderMapper.orderPaymentOp(orderDetailVO);
            orderMapper.productSales(orderDetailVO);
            orderMapper.productOpSales(orderDetailVO);

            totalCount = totalCount + Long.parseLong(orderCount.get(i));
        }




        String now = new SimpleDateFormat("yyyy/MM").format(System.currentTimeMillis()).toString();
        SalesVO salesVO = new SalesVO();

        int salesTermCount = orderMapper.maxSalesTerm(now);
        log.info("max salesTerm : " + salesTermCount);

        salesVO.setSalesSum(orderVO.getOrderPrice());
        salesVO.setSalesOrders(totalCount);
        salesVO.setSalesTerm(now);

        if(salesTermCount != 1){
            log.info("salesTerm null");

            orderMapper.addTotalSales(salesVO);
        }else{
            log.info("salesTerm not null");
            orderMapper.updateTotalSales(salesVO);
        }


        //카트에서 결제된 상품은 삭제

        if(oType != "d"){
            for(int i = 0; i < pOpNo.size(); i++){
                orderMapper.deleteOrderCart(orderVO.getUserId(), pOpNo.get(i));
            }
        }

    }
}
