package org.shop.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class memberOrderListDTO {

    private String orderNo;
    private String userId;
    private Date orderDate;
    private int orderStat;
    private int orderCount;
    private long odPrice;
    private int reviewStat;
    private String pSize;
    private String pColor;
    private String pName;
    private String firstThumbnail;
}
