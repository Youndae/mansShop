package org.shop.domain.dto.admin;

import lombok.Data;

import java.sql.Date;

@Data
public class MyQnAReplyGetDTO {

    private String userId;

    private String qrContent;

    private Date qrRegDate;
}
