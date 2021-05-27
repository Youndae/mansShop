package org.shop.mapper;

import org.shop.domain.ProductOpVO;
import org.shop.domain.ProductQnAVO;
import org.shop.domain.ProductReviewVO;
import org.shop.domain.ThumbnailVO;

import java.util.List;

public interface ProductMapper {

    public List<ProductOpVO> productMenu();

    public void productDetail();

    public List<ThumbnailVO> productThumbnail();

    public List<ProductReviewVO> productReview();

    public List<ProductQnAVO> productQnAList();

    public void productQnA();

    //답변완료인지 미완료인지 체크.
    //답글이 있다면 답변완료 없다면 미완료로 출력될것.
    public void checkQnA();

    public void getProductReview();

    public void modifyProductReview();

    public void deleteProductReview();

    public void likeProduct();

    public void addCart();

    public void deleteCart();
}
