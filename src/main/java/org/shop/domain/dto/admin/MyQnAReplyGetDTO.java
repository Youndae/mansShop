package org.shop.domain.dto.admin;

import lombok.Data;

import java.sql.Date;

@Data
public class MyQnAReplyGetDTO {

    private Long replyNo;

    private String userId;

    private String qrContent;

    private Date qrRegDate;
}
