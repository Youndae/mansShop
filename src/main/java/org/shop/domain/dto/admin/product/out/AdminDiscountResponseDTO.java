package org.shop.domain.dto.admin.product.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminDiscountResponseDTO {
    private String productId;

    private String classification;

    private String productName;

    private int price;

    private int discount;

    private int totalPrice;

    public int getTotalPrice() {
        return this.price - (this.price * this.discount / 100);
    }
}
