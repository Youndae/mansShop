package org.shop.domain.entity;

import lombok.*;

import java.sql.Date;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReview {

    private Long rNum;

    private String pno;

    private String userId;

    private String reviewContent;

    private Date reviewDate;
}
