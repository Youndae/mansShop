package org.shop.domain.dto.admin.sales.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.dto.admin.sales.business.AdminClassificationSalesDTO;
import org.shop.domain.dto.admin.sales.business.AdminClassificationSalesListDTO;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class AdminClassificationSalesResponseDTO {

    private String classification;

    private long totalSales;

    private long totalSalesQuantity;

    private List<AdminClassificationSalesListDTO> productSales;

    public AdminClassificationSalesResponseDTO(String classification, AdminClassificationSalesDTO salesDTO, List<AdminClassificationSalesListDTO> productList) {
        this.classification = classification;
        this.totalSales = salesDTO.getSales();
        this.totalSalesQuantity = salesDTO.getSalesQuantity();
        this.productSales = productList;
    }

}
