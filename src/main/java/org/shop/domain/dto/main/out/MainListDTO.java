package org.shop.domain.dto.main.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MainListDTO {

    private String productId;

    private String productName;

    private String thumbnail;

    private int price;

    private int discount;

    private int stock;

    private int discountPrice;

    public int getDisCountPrice() {
        return this.price - (this.price * this.discount / 100);
    }
}
