package org.shop.domain.dto.admin.sales.business;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AdminPeriodSalesStatisticsDTO {

    private long sales;

    private long salesQuantity;

    private long orderQuantity;

    private long deliveryFee;

    private long cashTotalPrice;

    private long cardTotalPrice;
}
