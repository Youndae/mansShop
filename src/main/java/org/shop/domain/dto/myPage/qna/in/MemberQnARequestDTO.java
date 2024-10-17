package org.shop.domain.dto.myPage.qna.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.entity.MemberQnA;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberQnARequestDTO {
    private String title;

    private String content;

    private int classificationId;

    public MemberQnA toEntity(String userId) {
        return MemberQnA.builder()
                .userId(userId)
                .memberQnATitle(this.title)
                .memberQnAContent(this.content)
                .qnaClassificationId(this.classificationId)
                .build();
    }
}
