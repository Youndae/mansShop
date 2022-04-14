package org.shop.service;

import org.shop.domain.*;

import java.util.List;

public interface ProductService {

    public ProductReviewDTO getProductReview(Criteria cri, String pno);

    public ProductQnADTO getProductQnA(Criteria cri, String pno);

    public int addCart(List<String> pOpNo, List<String> pCount, List<String> pPrice, CartVO cartVO, CartDetailVO cartDetailVO);
}
