package org.shop.domain.dto.myPage.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyPageReviewListDTO {

    private long id;

    private String thumbnail;

    private String productName;

    private String updatedAt;
}
