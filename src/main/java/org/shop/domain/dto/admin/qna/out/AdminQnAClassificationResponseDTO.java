package org.shop.domain.dto.admin.qna.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.entity.QnAClassification;

@Getter
@ToString
@NoArgsConstructor
public class AdminQnAClassificationResponseDTO {

    private long id;

    private String name;

    public AdminQnAClassificationResponseDTO(QnAClassification entity) {
        this.id = entity.getId();
        this.name = entity.getQnaClassificationName();
    }
}
