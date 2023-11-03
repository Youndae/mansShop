package org.shop.domain.dto.myPage;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;

@Getter
@ToString
@EqualsAndHashCode
public class MemberQnAListDTO {

    private long qno;
    private String userId;

    private String qTitle;

    private String qContent;

    private int qStat;

    private Date qRegDate;
}
