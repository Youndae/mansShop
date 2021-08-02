package org.shop.domain;

import lombok.Data;

import java.sql.Date;


@Data
public class ProductVO {

    private String pno;
    private String pClassification;
    private String pName;
    private Long pPrice;
    private Date pRegDate;
    private String firstThumbnail;
    private String pClosed;
    private Long pSales;
}
