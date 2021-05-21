package org.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO {
    private String orderListNo;
    private String pOpNo;
    private String userId;
    private String orderNo;
    private String orderPhone;
    private String orderMemo;
    private int orderCount;
    private Long orderPrice;
    private int orderStat;
    private Date orderDate;
    private char orderPayment;
}
