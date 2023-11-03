package org.shop.domain.dto.order;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPaymentDTO {

    private String pOpNo;

    private String pName;

    private int cCount;

    private long cPrice;

    private String pno;

    private String cdNo;

    private String pSize;

    private String pColor;


    public void setPSize(String pSize) {
        this.pSize = pSize;
    }

    public void setPColor(String pColor) {
        this.pColor = pColor;
    }

    public void setCdNo(String cdNo){this.cdNo = cdNo;}
}
