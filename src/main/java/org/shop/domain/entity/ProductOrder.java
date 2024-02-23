package org.shop.domain.entity;

import lombok.*;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class ProductOrder {

    private String orderNo;

    private String userId;

    private String addr;

    private String orderPhone;

    private String orderMemo;

    private Long orderPrice;

    private int orderStat;

    private Date orderDate;

    private char orderPayment;

    private String recipient;

    public ProductOrder(String orderNo
                        , String userId
                        , String addr
                        , String orderPhone
                        , String orderMemo
                        , Long orderPrice
                        , int orderStat
                        , Date orderDate
                        , char orderPayment
                        , String recipient) {
        StringBuffer sb = new StringBuffer();
        this.orderNo = orderNo != null ? orderNo : sb.append(
                                                            new SimpleDateFormat("yyyyMMddHHmmss")
                                                                    .format(System.currentTimeMillis())
                                                    ).toString();
        this.userId = userId;
        this.addr = addr;
        this.orderPhone = orderPhone;
        this.orderMemo = orderMemo;
        this.orderPrice = orderPrice;
        this.orderStat = orderStat;
        this.orderDate = orderDate;
        this.orderPayment = orderPayment;
        this.recipient = recipient;
    }
}
