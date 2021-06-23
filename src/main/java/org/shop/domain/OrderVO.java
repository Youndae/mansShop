package org.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO {
    private String orderNo;
    private String userId;
    private String addr;
    private String orderPhone;
    private String orderMemo;
    private Long orderPrice;
    private int orderStat;
    private Date orderDate;
    private char orderPayment;
}
