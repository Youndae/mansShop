package org.shop.domain.entity;

import lombok.*;

import java.sql.Date;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductQnA {

    private Long pQnANo;

    private String pno;

    private String userId;

    private String pQnAContent;

    private Date pQnARegDate;

    private int pQnAAnswer;

    private Long pQnAGroupNo;

    private int pQnAIndent;
}
