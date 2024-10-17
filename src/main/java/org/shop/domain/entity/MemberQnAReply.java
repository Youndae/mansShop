package org.shop.domain.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberQnAReply {

    private long id;

    private String userId;

    private long qnaId;

    private String replyContent;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    public void setReplyContent(String content) {
        this.replyContent = content;
    }
}
