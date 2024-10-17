package org.shop.domain.dto.admin.sales.business;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AdminClassificationSalesListDTO {

    private String productName;

    private String productSize;

    private String productColor;

    private long productSales;

    private long productQuantity;
}
