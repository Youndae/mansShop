package org.shop.domain.dto.myPage.qna.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QnAReplyListDTO {

    private long replyId;

    private String writer;

    private String content;

    private LocalDate updatedAt;
}
