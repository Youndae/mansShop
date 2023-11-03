package org.shop.domain.dto.myPage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailDTO {

    private String cartNo;
    private String cdNo;
    private String pOpNo;
    private int cCount;
    private Long cPrice;
    private String pName;
    private String pno;
    private String pSize;
    private String pColor;

}
