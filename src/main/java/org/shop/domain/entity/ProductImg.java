package org.shop.domain.entity;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImg {

    private Long pImgNo;

    private String pno;

    private String pImg;

    private int pImgStep;
}
