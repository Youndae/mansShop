package org.shop.domain.dto.myPage;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;

@Getter
@ToString
@EqualsAndHashCode
public class MemberReviewListDTO {

    private String pno;

    private long rNum;

    private String pName;

    private String firstThumbnail;

    private Date reviewDate;
}
