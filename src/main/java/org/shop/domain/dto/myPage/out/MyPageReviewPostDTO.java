package org.shop.domain.dto.myPage.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class MyPageReviewPostDTO {
    private long orderDetailId;

    private String productName;

    private String productSize;

    private String productColor;

}
