package org.shop.domain.entity;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOp {

    private String pOpNo;

    private String pno;

    private String pSize;

    private String pColor;

    private Long opSalesRate;

    private Long opSales;

    private Long pStock;

    private int opClosed;

    /*private List<Thumbnail> pThumbList;
    private List<ProductImg> pImgList;*/
}
