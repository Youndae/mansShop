package org.shop.domain.dto.order.out;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProductDTO {

    private String productId;

    private String productName;

    private long productOptionId;

    private String pSize;

    private String pColor;

    private int count;

    private int price;

    private int discount;

    private int discountPrice;

    private int productTotalPrice;

    public int getDiscountPrice() {
        return price - (price * this.discount / 100);
    }

    public int getProductTotalPrice() {
        return (price - (price * this.discount / 100)) * count;
    }
}
