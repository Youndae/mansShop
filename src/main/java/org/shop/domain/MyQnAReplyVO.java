package org.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyQnAReplyVO {
    private Long replyNo;
    private Long qno;
    private String userId;
    private String qrContent;
    private Date qrRegDate;
}
