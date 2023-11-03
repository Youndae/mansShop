package org.shop.domain.dto.admin;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class SalesProductListDTO {

    private String pClassification;

    private String pName;

    private String pSize;

    private String pColor;

    private long pPrice;

    private long opSalesRate;

    private long opSales;
}
