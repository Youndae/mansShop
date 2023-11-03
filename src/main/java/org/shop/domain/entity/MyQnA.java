package org.shop.domain.entity;

import lombok.*;

import java.sql.Date;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyQnA {

    private long qno;

    private String userId;

    private String qTitle;

    private String qContent;

    private int qStat;

    private Date qRegDate;
}
