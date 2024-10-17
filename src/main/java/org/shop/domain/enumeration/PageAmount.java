package org.shop.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PageAmount {
    MAIN_AMOUNT(12)
    , DEFAULT_AMOUNT(20)
    , PRODUCT_REVIEW_AND_QNA_AMOUNT(10)
    , LIKE_PRODUCT_AMOUNT(10);

    private final int amount;
}
