package org.shop.domain.entity;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDetail {

    private String cdNo;

    private String cartNo;

    private String pOpNo;

    private int cCount;

    private Long cPrice;

}
