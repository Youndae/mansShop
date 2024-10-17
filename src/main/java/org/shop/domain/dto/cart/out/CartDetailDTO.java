package org.shop.domain.dto.cart.out;

import lombok.*;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailDTO {
    private long cartDetailId;

    private String productId;

    private long optionId;

    private String productName;

    private String thumbnail;

    private String pSize;

    private String pColor;

    private int count;

    private int price;

    private int discount;

    private int totalPrice;

    public int getTotalPrice() {
        return (price - (price * this.discount / 100)) * this.count;
    }
}
