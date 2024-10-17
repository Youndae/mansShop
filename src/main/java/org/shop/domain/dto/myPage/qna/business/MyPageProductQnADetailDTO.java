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
public class MyPageProductQnADetailDTO {
    private long qnaId;

    private String productId;

    private String productName;

    private String writer;

    private String content;

    private LocalDate createdAt;

    private boolean status;
}
