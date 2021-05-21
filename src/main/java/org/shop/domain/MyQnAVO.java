package org.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyQnAVO {
    private Long qNo;
    private String userId;
    private String qTithe;
    private String qContent;
    private int qStat;
    private Date qRegDate;
}
