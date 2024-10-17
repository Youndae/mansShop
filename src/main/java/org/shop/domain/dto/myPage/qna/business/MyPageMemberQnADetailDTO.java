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
public class MyPageMemberQnADetailDTO {

    private long qnaId;

    private String classification;

    private String title;

    private String writer;

    private String content;

    private LocalDate updatedAt;

    private boolean status;
}
