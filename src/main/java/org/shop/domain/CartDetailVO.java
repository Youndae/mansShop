package org.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailVO extends ProductOpVO{

    private String cdNo;
    private String cartNo;
    private String pOpNo;
    private int cCount;
    private Long cPrice;

    private ProductVO productVO;

}
