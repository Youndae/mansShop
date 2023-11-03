package org.shop.domain.dto.product;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDTO {

    private String firstThumbnail;

    private String pName;

    private long pPrice;

    private String pno;
}
