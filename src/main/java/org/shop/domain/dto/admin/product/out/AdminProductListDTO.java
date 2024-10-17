package org.shop.domain.dto.admin.product.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminProductListDTO {
    private String productId;

    private String classification;

    private String productName;

    private int stock;

    private long optionCount;

    private int price;
}
