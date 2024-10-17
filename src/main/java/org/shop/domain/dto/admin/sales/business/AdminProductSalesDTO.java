package org.shop.domain.dto.admin.sales.business;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AdminProductSalesDTO {

    private String productName;

    private long totalSales;

    private long totalSalesQuantity;
}
