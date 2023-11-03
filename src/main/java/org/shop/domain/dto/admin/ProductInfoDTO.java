package org.shop.domain.dto.admin;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoDTO {

    private String pno;

    private String pClassification;

    private String pName;

    private long pPrice;

    private String firstThumbnail;

    private String pClosed;

    private String pOpNo;

    private String pSize;

    private String pColor;

    private long pStock;

    private int opClosed;
}
