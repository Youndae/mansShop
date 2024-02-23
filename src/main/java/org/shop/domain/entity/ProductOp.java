package org.shop.domain.entity;

import lombok.*;

import java.text.SimpleDateFormat;

@Getter
@ToString
@EqualsAndHashCode
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

    public ProductOp(String pOpNo, String pno, String pSize, String pColor, Long opSalesRate, Long opSales, Long pStock, int opClosed) {

        StringBuffer sb = new StringBuffer();

        this.pOpNo = pOpNo != null ? pOpNo : sb.append("OP_")
                                                .append(pno)
                                                .append(pSize)
                                                .append(pColor)
                                                .toString();
        this.pno = pno;
        this.pSize = pSize;
        this.pColor = pColor;
        this.opSalesRate = opSalesRate;
        this.opSales = opSales;
        this.pStock = pStock;
        this.opClosed = opClosed;
    }
}
