package org.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyQnAReplyVO {
    private Long replyNo;
    private Long qno;
    private String userId;
    private String qrContent;
    private String qrRegDate;
}
