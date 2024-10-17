package org.shop.domain.dto.myPage.qna.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.entity.ProductQnA;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyPageProductQnAPatchDTO {
    private long id;

    private String productName;

    private String content;

}
