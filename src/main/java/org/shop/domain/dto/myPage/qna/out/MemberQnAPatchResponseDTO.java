package org.shop.domain.dto.myPage.qna.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.entity.MemberQnA;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberQnAPatchResponseDTO {

    private long id;

    private String title;

    private long classificationId;

    private String content;

    public MemberQnAPatchResponseDTO(MemberQnA entity) {
        this.id = entity.getId();
        this.title = entity.getMemberQnATitle();
        this.classificationId = entity.getQnaClassificationId();
        this.content = entity.getMemberQnAContent();
    }
}
