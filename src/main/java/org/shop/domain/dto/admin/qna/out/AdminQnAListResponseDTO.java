package org.shop.domain.dto.admin.qna.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
public class AdminQnAListResponseDTO {

    private long qnaId;

    private String classification;

    private String title;

    private String writer;

    private LocalDate createdAt;

    private boolean status;
}
