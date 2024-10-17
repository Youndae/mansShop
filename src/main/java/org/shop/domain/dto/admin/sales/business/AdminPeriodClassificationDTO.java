package org.shop.domain.dto.admin.sales.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminPeriodClassificationDTO {

    private String classification;

    private long sales;

    private long salesQuantity;

    public AdminPeriodClassificationDTO(String classification) {
        this.classification = classification;
        this.sales = 0;
        this.salesQuantity = 0;
    }
}
