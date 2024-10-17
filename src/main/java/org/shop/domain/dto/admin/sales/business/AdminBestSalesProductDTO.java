package org.shop.domain.dto.admin.sales.business;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AdminBestSalesProductDTO {

    private String productName;

    private long salesQuantity;

    private long sales;
}
