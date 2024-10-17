package org.shop.domain.dto.admin.product.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.dto.admin.product.business.AdminProductOptionDTO;
import org.shop.domain.dto.admin.product.business.AdminProductStockInfoDTO;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminProductStockDTO {
    private String productId;

    private String classification;

    private String productName;

    private int totalStock;

    private boolean isOpen;

    private List<AdminProductOptionDTO> options;

    public AdminProductStockDTO(AdminProductStockInfoDTO dto, List<AdminProductOptionDTO> options) {
        this.productId = dto.getProductId();
        this.classification = dto.getClassification();
        this.productName = dto.getProductName();
        this.totalStock = dto.getTotalStock();
        this.isOpen = dto.isOpen();
        this.options = options;
    }
}
