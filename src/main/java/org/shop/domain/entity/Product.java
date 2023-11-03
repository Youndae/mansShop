package org.shop.domain.entity;

import lombok.*;

import java.sql.Date;


@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    private String pno;

    private String pClassification;

    private String pName;

    private Long pPrice;

    private Date pRegDate;

    private String firstThumbnail;

    private String pClosed;

    private Long pSales;
}
