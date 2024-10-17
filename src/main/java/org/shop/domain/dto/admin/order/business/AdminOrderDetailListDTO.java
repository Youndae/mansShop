package org.shop.domain.dto.admin.order.business;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AdminOrderDetailListDTO {

    private String classification;

    private String productName;

    private String productSize;

    private String productColor;

    private int count;

    private int price;

    private boolean reviewStatus;
}
