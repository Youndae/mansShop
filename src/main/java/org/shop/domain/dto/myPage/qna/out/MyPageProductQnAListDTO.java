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
public class MyPageProductQnAListDTO {

    private long id;

    private String productName;

    private boolean status;

    private LocalDate createdAt;
}
