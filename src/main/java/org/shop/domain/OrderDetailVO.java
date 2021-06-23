package org.shop.domain;

import lombok.Data;

@Data
public class OrderDetailVO {
    private String odNo;
    private String orderNo;
    private String pOpNo;
    private int orderCount;
    private int odPrice;
}
