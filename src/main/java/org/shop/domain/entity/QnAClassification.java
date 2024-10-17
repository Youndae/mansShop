package org.shop.domain.entity;

import lombok.*;
import org.shop.domain.dto.myPage.qna.out.MyPageQnAClassificationDTO;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class QnAClassification {

    private long id;

    private String qnaClassificationName;

    public MyPageQnAClassificationDTO fromDTO() {
        return MyPageQnAClassificationDTO.builder()
                .id(this.id)
                .name(this.qnaClassificationName)
                .build();
    }
}
