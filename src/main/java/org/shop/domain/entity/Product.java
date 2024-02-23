package org.shop.domain.entity;

import lombok.*;

import java.sql.Date;
import java.text.SimpleDateFormat;


@Getter
@ToString
@EqualsAndHashCode
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

    public Product(String pno, String pClassification, String pName, Long pPrice, Date pRegDate, String firstThumbnail, String pClosed, Long pSales) {
        StringBuffer sb = new StringBuffer();

        this.pno = pno != null ? pno : sb.append(pClassification)
                                            .append(
                                                    new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())
                                            ).toString();
        this.pClassification = pClassification;
        this.pName = pName;
        this.pPrice = pPrice;
        this.pRegDate = pRegDate;
        this.firstThumbnail = firstThumbnail;
        this.pClosed = pClosed;
        this.pSales = pSales;
    }
}
