package org.shop.domain.dto.admin.product.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AdminProductStockInfoDTO {

    private String productId;

    private String classification;

    private String productName;

    private int totalStock;

    private boolean isOpen;
}
