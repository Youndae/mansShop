package org.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVO extends ProductOpVO{
    private String odNo;
    private String orderNo;
    private String pOpNo;
    private int orderCount;
    private int odPrice;
    private String pno;

}
