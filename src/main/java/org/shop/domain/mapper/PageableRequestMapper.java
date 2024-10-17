package org.shop.domain.mapper;

import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.dto.paging.MainPageCriteria;
import org.shop.domain.dto.paging.PageCriteria;
import org.shop.domain.enumeration.PageAmount;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class PageableRequestMapper {

    public static PageCriteria createDefaultAmountPageCriteria(int page, String keyword, String searchType) {
//        keyword = keywordCheck(keyword);

        return new PageCriteria(page, PageAmount.DEFAULT_AMOUNT.getAmount(), keyword, searchType);
    }

    public static PageCriteria createReviewAndQnAAmountPageCriteria(int page, String keyword, String searchType) {
//        keyword = keywordCheck(keyword);

        return new PageCriteria(page, PageAmount.PRODUCT_REVIEW_AND_QNA_AMOUNT.getAmount(), keyword, searchType);
    }

    public static MainPageCriteria createMainPageCriteria(int page, String keyword, String classification) {
//        keyword = keywordCheck(keyword);

        return new MainPageCriteria(page, PageAmount.MAIN_AMOUNT.getAmount(), keyword, classification);
    }

    private static String keywordCheck(String keyword) {
        return keyword.equals("") ? null : keyword;
    }
}
