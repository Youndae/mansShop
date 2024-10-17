package org.shop.domain.dto.admin.product.in;

import lombok.*;
import org.shop.domain.entity.ProductOption;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminPatchOptionDTO {

    private Long optionId;

    private String size;

    private String color;

    private int stock;

    private String isOpen;

    public ProductOption toEntity(String productId) {
        return ProductOption.builder()
                            .id(this.optionId)
                            .productId(productId)
                            .productSize(this.size)
                            .productColor(this.color)
                            .stock(this.stock)
                            .isOpen(this.isOpen.equals("true"))
                            .build();
    }

}
