package org.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOpVO {
    private String pOpNo;
    private String pno;
    private String pSize;
    private String pColor;
    private Long pSalesRate;
    private Long pSales;
    private Long pStock;
}
