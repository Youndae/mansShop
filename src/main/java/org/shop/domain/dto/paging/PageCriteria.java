package org.shop.domain.dto.paging;


import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageCriteria extends Criteria{

    private String searchType;

    public PageCriteria(int page, int amount, String keyword, String searchType) {
        super(page, amount, keyword);
        this.searchType = searchType;
    }
}
