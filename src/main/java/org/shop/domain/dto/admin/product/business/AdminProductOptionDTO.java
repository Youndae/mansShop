package org.shop.domain.dto.admin.product.business;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.entity.ProductOption;

@Getter
@ToString
@NoArgsConstructor
public class AdminProductOptionDTO {

    private String productSize;

    private String productColor;

    private int stock;

    private boolean isOpen;

    public AdminProductOptionDTO(ProductOption entity) {
        this.productSize = entity.getProductSize();
        this.productColor = entity.getProductColor();
        this.stock = entity.getStock();
        this.isOpen = entity.isOpen();
    }
}
