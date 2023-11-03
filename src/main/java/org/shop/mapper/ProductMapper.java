package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.dto.product.ProductDetailDTO;
import org.shop.domain.dto.product.ProductOpDTO;
import org.shop.domain.entity.*;

import java.util.List;

public interface ProductMapper {

    public ProductDetailDTO productDetail(String pno);

    public List<ProductOpDTO> getProductOp(String pno);

    public List<ProductReview> getProductReview(@Param("cri") Criteria cri, @Param("pno") String pno);

    public int getProductReviewTotal(String pno);

    public List<ProductQnA> getProductQnA(@Param("cri") Criteria cri, @Param("pno")String pno);

    public int getProductQnATotal(String pno);

    public int getLikeProductStat(@Param("pno") String pno, @Param("userId") String userId);

    public void addCart(Cart cart);

    public void updateCart(Cart cart);

    public String checkCartNo(Cart cart);

    public void updateCartDetail(List<CartDetail> updateCartDetailList);

    public void addCartDetail(List<CartDetail> addCartDetailList);

    public List<String> checkDetailOption(String cartNo);

    public void insertPQnA(ProductQnA productQna);

    public void likeProduct(LikeProduct likeProduct);

    public void deLikeProduct(LikeProduct likeProduct);

}
