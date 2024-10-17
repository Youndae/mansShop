package org.shop.domain.dto.product.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductQnARequestDTO {
    private String productId;

    private String content;

    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
