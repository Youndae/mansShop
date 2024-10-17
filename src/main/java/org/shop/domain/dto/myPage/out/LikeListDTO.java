package org.shop.domain.dto.myPage.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LikeListDTO {
    private long likeId;

    private String productId;

    private String productName;

    private int productPrice;

    private int productDiscount;

    private int productTotalPrice;

    private String thumbnail;

    private int stock;

    LocalDate createdAt;

    public int getProductTotalPrice() {
        return this.productPrice - (this.productPrice * this.productDiscount / 100);
    }
}
