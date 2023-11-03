package org.shop.domain.dto.myPage;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;

@Getter
@ToString
@EqualsAndHashCode
public class MemberReviewDetailDTO {

    private String reviewContent;

    private Date reviewDate;

    private long rNum;

    private String firstThumbnail;

    private String pName;
}
