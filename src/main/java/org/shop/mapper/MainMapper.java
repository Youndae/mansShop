package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.*;

import java.util.List;

public interface MainMapper {

    public List<ProductOpVO> mainBest();

    public ProductVO productDetail(String pno);

    public List<ThumbnailVO> getProductThumb(String pno);

    public List<ProductOpVO> getProductOp(String pno);

    public List<ProductImgVO> getProductInfo(String pno);

    public List<ProductReviewVO> getProductReview(@Param("cri") Criteria cri, @Param("pno") String pno);

    public List<ProductQnAVO> getProductQnA(@Param("cri") Criteria cri, @Param("pno")String pno);

    public int getProductReviewTotal(String pno);

    public int getProductQnATotal(String pno);

    public void mainNew();

    public void insertPQnA(ProductQnAVO productQnAVO);

    public void likeProduct(LikeVO likeVO);
}
