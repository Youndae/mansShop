package org.shop.domain.entity;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOrderDetail {

    private String odNo;

    private String orderNo;

    private String pOpNo;

    private int orderCount;

    private int odPrice;

    private String pno;
}
