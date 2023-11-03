package org.shop.domain.entity;

import lombok.*;

import java.sql.Date;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
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
}
