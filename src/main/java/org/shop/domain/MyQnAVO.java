package org.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyQnAVO {
    private Long qno;
    private String userId;
    private String qTitle;
    private String qContent;
    private int qStat;
    private Date qRegDate;
}
