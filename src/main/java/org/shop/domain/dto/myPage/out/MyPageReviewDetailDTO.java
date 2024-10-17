package org.shop.domain.dto.myPage.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyPageReviewDetailDTO {

    private long id;

    private String productId;

    private String productName;

    private String content;

    private LocalDate updatedAt;

    private String replyContent;

    private LocalDate replyUpdatedAt;
}
