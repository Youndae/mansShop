package org.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesVO {
    private String SalesTerm;
    private Long salesSum;
    private Long salesOrders;
}
