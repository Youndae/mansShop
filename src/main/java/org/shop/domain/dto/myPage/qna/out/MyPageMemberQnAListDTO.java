package org.shop.domain.dto.myPage.qna.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyPageMemberQnAListDTO {

    private long id;

    private String title;

    private boolean status;

    private String classification;

    private LocalDate updatedAt;
}
