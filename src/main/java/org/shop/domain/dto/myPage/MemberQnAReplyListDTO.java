package org.shop.domain.dto.myPage;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;

@Getter
@ToString
@EqualsAndHashCode
public class MemberQnAReplyListDTO {

    private String userId;

    private Date qrRegDate;

    private String qrContent;
}
