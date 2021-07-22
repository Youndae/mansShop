package org.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewVO extends ProductVO{
    private Long rNum;
    private String pno;
    private String userId;
    private String reviewContent;
    private Date reviewDate;
}
