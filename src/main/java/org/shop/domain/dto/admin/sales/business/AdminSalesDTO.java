package org.shop.domain.dto.admin.sales.business;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AdminSalesDTO {

    private long sales;

    private long salesQuantity;
}
