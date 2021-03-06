package org.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOpVO extends ProductVO{
    private String pOpNo;
    private String pno;
    private String pSize;
    private String pColor;
    private Long opSalesRate;
    private Long opSales;
    private Long pStock;
    private int opClosed;

    private List<ThumbnailVO> pThumbList;
    private List<ProductImgVO> pImgList;
}
