package org.shop.domain.dto.admin.product.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.entity.Product;

@Getter
@ToString
@NoArgsConstructor
public class AdminDiscountSelectProductDTO {

    private String id;

    private String productName;

    private int price;

    public AdminDiscountSelectProductDTO(Product entity) {
        this.id = entity.getId();
        this.productName = entity.getProductName();
        this.price = entity.getProductPrice();
    }
}
