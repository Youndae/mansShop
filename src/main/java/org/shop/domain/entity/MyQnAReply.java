package org.shop.domain.entity;

import lombok.*;

import java.sql.Date;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyQnAReply {

    private Long replyNo;

    private Long qno;

    private String userId;

    private String qrContent;

    private Date qrRegDate;
}
