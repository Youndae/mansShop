package org.shop.service;

import org.shop.domain.Criteria;
import org.shop.domain.ProductQnADTO;
import org.shop.domain.ProductReviewDTO;

public interface MainService {

    public ProductReviewDTO getProductReview(Criteria cri, String pno);

    public ProductQnADTO getProductQnA(Criteria cri, String pno);
}
