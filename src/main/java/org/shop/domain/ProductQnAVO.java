package org.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductQnAVO {
    private Long pQnANo;
    private String pno;
    private String userId;
    private String pQnAContent;
    private Date pQnARegDate;
    private int pQnAAnswer;
    private Long pQnAGroupNo;
    private Long pQnAIndent;
}
