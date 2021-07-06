package org.shop.service;

import org.shop.domain.CartVO;
import org.shop.domain.Criteria;
import org.shop.domain.ProductQnADTO;
import org.shop.domain.ProductReviewDTO;

import java.util.List;

public interface MainService {

    public ProductReviewDTO getProductReview(Criteria cri, String pno);

    public ProductQnADTO getProductQnA(Criteria cri, String pno);

    public int addCart(List<String> pOpNo, List<String> pCount, List<String> pPrice, CartVO cartVO);
}
