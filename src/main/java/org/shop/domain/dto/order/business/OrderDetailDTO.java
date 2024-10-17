package org.shop.domain.dto.order.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {

    private long orderId;

    private String productId;

    private long optionId;

    private long detailId;

    private String productName;

    private String pSize;

    private String color;

    private int detailCount;

    private int detailPrice;

    private boolean reviewStatus;

    private String thumbnail;
}
