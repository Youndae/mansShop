package org.shop.domain.dto.order;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderDTO {

    private String addr;

    private String orderPhone;

    private String orderMemo;

    private long orderPrice;

    private char orderPayment;

    private String recipient;
}
