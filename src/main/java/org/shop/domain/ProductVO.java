package org.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO {
    private String pno;
    private String pClassification;
    private String pName;
    private Long pPrice;
    private Date pRegDate;
    private String firstThumbnail;
    private String pInfo;
}
