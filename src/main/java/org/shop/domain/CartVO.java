package org.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartVO extends ProductOpVO{
    private String cartNo;
    private String userId;
    private String pOpNo;
    private Long cCount;
    private Long cPrice;
}
