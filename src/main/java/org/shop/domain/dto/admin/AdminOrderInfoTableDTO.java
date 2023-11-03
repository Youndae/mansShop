package org.shop.domain.dto.admin;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class AdminOrderInfoTableDTO {

    private String pClassification;

    private String pName;

    private String pSize;

    private String pColor;

    private long pPrice;

    private String pOpNo;

    private String orderNo;

    private int orderCount;
}
