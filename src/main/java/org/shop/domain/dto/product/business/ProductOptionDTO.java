package org.shop.domain.dto.product.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductOptionDTO {

    private long optionId;

    private String pSize;

    private String color;

    private int stock;

    private boolean isOpen;
}
