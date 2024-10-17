package org.shop.domain.entity;

import lombok.*;

import java.text.SimpleDateFormat;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ProductThumbnail {

    private long id;

    private String productId;

    private String imageName;

    public ProductThumbnail(String productId, String imageName) {
        this.productId = productId;
        this.imageName = imageName;
    }

}
