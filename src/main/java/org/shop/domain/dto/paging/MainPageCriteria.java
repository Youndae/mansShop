package org.shop.domain.dto.paging;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainPageCriteria extends Criteria{
    private String classification;

    public MainPageCriteria(int page, int amount, String keyword, String classification) {
        super(page == 0 ? 1 : page, amount, keyword);
        this.classification = classification == null ? null : classification.toUpperCase();
    }
}
