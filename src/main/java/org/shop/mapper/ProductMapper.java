package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.*;

import java.util.List;

public interface ProductMapper {

    public ProductVO productDetail(String pno);

    public List<ThumbnailVO> getProductThumb(String pno);

    public List<ProductOpVO> getProductOp(String pno);

    public List<ProductImgVO> getProductInfo(String pno);

    public List<ProductReviewVO> getProductReview(@Param("cri") Criteria cri, @Param("pno") String pno);

    public int getProductReviewTotal(String pno);

    public int getProductQnATotal(String pno);

    public int getLikeProductStat(@Param("pno") String pno, @Param("userId") String userId);

    public List<ProductQnAVO> getProductQnA(@Param("cri") Criteria cri, @Param("pno")String pno);

    public int checkCart(CartVO cartVO);

    public void addCart(CartVO cartVO);

    //updatedAt 수정
    public void updateCart(CartVO cartVO);

    public String checkCartNo(CartVO cartVO);

    public void updateCartDetail(CartDetailVO cartDetailVO);

    public void addCartDetail(CartDetailVO cartDetailVO);

    public int checkDetailOption(CartDetailVO cartDetailVO);

    public void insertPQnA(ProductQnAVO productQnAVO);

    public void likeProduct(LikeVO likeVO);

    public void deLikeProduct(LikeVO likeVO);



}
