package org.shop.domain.dto.admin.sales.business;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminPeriodSalesListDTO {

    private int month;

    private long sales;

    private long salesQuantity;

    private long orderQuantity;

    public AdminPeriodSalesListDTO(int date) {
        this.month = date;
        this.sales = 0;
        this.salesQuantity = 0;
        this.orderQuantity = 0;
    }
}
