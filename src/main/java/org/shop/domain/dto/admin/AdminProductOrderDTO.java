package org.shop.domain.dto.admin;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class AdminProductOrderDTO {

    private String orderNo;

    private String recipient;

    private String userId;

    private String orderPhone;

    private String addr;

    private String orderMemo;

    private int orderStat;
}
