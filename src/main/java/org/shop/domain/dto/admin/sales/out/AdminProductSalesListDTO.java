package org.shop.domain.dto.admin.sales.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AdminProductSalesListDTO {

    private String classification;

    private String productId;

    private String productName;

    private long sales;

    private long salesQuantity;
}
