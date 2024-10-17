package org.shop.domain.dto.myPage.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyPageReviewPatchDTO {
    private long id;

    private String content;

    private String productName;
}
