package org.shop.domain.entity;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductInfoImage {

    private long id;

    private String productId;

    private String imageName;

    public ProductInfoImage(String productId, String imageName) {
        this.productId = productId;
        this.imageName = imageName;
    }
}
