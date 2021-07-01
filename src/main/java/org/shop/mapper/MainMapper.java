package org.shop.mapper;

import org.shop.domain.*;

import java.util.List;

public interface MainMapper {

    public List<ProductOpVO> mainBest();

    public ProductVO productDetail(String pno);

    public List<ThumbnailVO> getProductThumb(String pno);

    public List<ProductOpVO> getProductOp(String pno);

    public List<ProductImgVO> getProductInfo(String pno);

    public List<ProductReviewVO> getProductReview(Criteria cri);

    public List<ProductQnAVO> getProductQnA(String pno);

    public int getProductReviewTotal(Criteria cri);

    public int pReviewCount(String pno);

    public int pQnACount(String pno);

    public void mainNew();
}
