package org.shop.domain.dto.admin.sales.business;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.entity.ProductOption;

@Getter
@ToString
@NoArgsConstructor
public class AdminProductSalesOptionDTO {

    private long optionId;

    private String productSize;

    private String productColor;

    private long optionSales;

    private long optionSalesQuantity;

    public AdminProductSalesOptionDTO(ProductOption option, long optionSales, long optionSalesQuantity) {
        this.optionId = option.getId();
        this.productSize = option.getProductSize();
        this.productColor = option.getProductColor();
        this.optionSales = optionSales;
        this.optionSalesQuantity = optionSalesQuantity;
    }
}
