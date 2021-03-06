package org.shop.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.*;
import org.shop.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
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
    public List<CartDetailVO> orderProduct(HashMap<String, Object> commandMap) {

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

        String[] pno_array = null;
        String pno = commandMap.get("pno").toString();
        pno_array = pno.split(",");

        String[] cd_array = null;
        String cdNo = commandMap.get("cdNo").toString();
        cd_array = cdNo.split(",");

        List<CartDetailVO> vo = new ArrayList<>();

        for(int i = 0; i < no_array.length; i++){
            log.info("order payments each");
            log.info("size_array length : " + size_array.length);

            CartDetailVO cartDetailVO = new CartDetailVO();

            cartDetailVO.setPOpNo(no_array[i]);
            cartDetailVO.setPName(name_array[i]);
            cartDetailVO.setCCount(Integer.parseInt(count_array[i]));
            cartDetailVO.setCPrice(Long.parseLong(price_array[i]));
            cartDetailVO.setPno(pno_array[i]);
            cartDetailVO.setCdNo(cd_array[i]);

            if(size_array.length != 0 && size_array[i] != null){
                cartDetailVO.setPSize(size_array[i]);
            }

            if(color_array.length != 0 && color_array[i] != null){
                cartDetailVO.setPColor(color_array[i]);
            }

            vo.add(cartDetailVO);

            /*cartVO.setPOpNo(no_array[i]);
            cartVO.setPName(name_array[i]);
            cartVO.setCCount(Long.parseLong(count_array[i]));
            cartVO.setCPrice(Long.parseLong(price_array[i]));
            cartVO.setPno(pno_array[i]);*/

            /*if(size_array.length != 0 && size_array[i] != null){
                cartVO.setPSize(size_array[i]);
            }

            if(color_array.length != 0 && color_array[i] != null){
                cartVO.setPColor(color_array[i]);
            }

            vo.add(cartVO);*/
        }

        log.info("Last VO : " + vo);

        return vo;
    }


    @Override
    public void orderPayment(OrderVO orderVO, List<String> cdNo, List<String> pOpNo, List<String> orderCount, List<String> odPrice, List<String> pno, String oType, Cookie cookie) {
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
            orderDetailVO.setPno(pno.get(i));

            orderMapper.orderPaymentOp(orderDetailVO);
            orderMapper.productSales(orderDetailVO);
            orderMapper.productOpSales(orderDetailVO);

            totalCount = totalCount + Long.parseLong(orderCount.get(i));
        }




        String now = new SimpleDateFormat("yyyy/MM").format(System.currentTimeMillis()).toString();
        SalesVO salesVO = new SalesVO();

        int salesTermCount = orderMapper.maxSalesTerm(now);
        log.info("max salesTerm : " + salesTermCount);

        if(orderVO.getOrderPrice() < 100000){
            salesVO.setSalesSum(orderVO.getOrderPrice() - 2500);
        }else{
            salesVO.setSalesSum(orderVO.getOrderPrice());
        }

        salesVO.setSalesOrders(totalCount);
        salesVO.setSalesTerm(now);

        if(salesTermCount != 1){
            log.info("salesTerm null");

            orderMapper.addTotalSales(salesVO);
        }else{
            log.info("salesTerm not null");
            orderMapper.updateTotalSales(salesVO);
        }


        //???????????? ????????? ????????? ??????

        if(oType != "d"){ //oType = d??? ??????????????? ????????? ?????? ?????? ???????????? ?????? ????????? ??????.

            CartVO cartVO = new CartVO();

            if(cookie != null){ //??????????????????
                cartVO.setCkId(cookie.getValue());
            }else{
                cartVO.setUserId(orderVO.getUserId());
            }

            if(orderMapper.deleteCartCheck(cartVO) == cdNo.size()){ //???????????? ????????? ?????? ????????? ????????????
                orderMapper.deleteOrderCart(cartVO); //cart ??????????????? ?????? ????????? ????????? ??????
            }else{
                for(int i = 0; i < cdNo.size(); i++){
                    orderMapper.deleteOrderCartDetail(cdNo.get(i)); //cartDetail ??????????????? ?????? ???????????? ??????
                }
            }
        }

    }

}
