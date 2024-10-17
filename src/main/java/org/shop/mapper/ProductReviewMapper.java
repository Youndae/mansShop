package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.dto.admin.review.business.AdminReviewDetailDTO;
import org.shop.domain.dto.admin.review.out.AdminReviewListDTO;
import org.shop.domain.dto.myPage.qna.in.QnAReplyRequestDTO;
import org.shop.domain.dto.myPage.out.MyPageReviewPatchDTO;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.dto.paging.PageCriteria;
import org.shop.domain.dto.product.business.ProductReviewListDTO;
import org.shop.domain.entity.ProductReview;

import java.util.List;

public interface ProductReviewMapper {
    List<ProductReviewListDTO> findAllDetailByProductId(@Param("productId") String productId, @Param("cri") Criteria cri);

    int countByProductId(@Param("productId") String productId);

    MyPageReviewPatchDTO findPatchDataByReviewIdAndUserId(@Param("id") long reviewId, @Param("userId") String userId);

    ProductReview findById(long id);

    void patchReview(@Param("dto") QnAReplyRequestDTO dto);

    void deleteById(long id);

    List<AdminReviewListDTO> findAllByAdminList(@Param("cri") PageCriteria cri);

    int countAdminReviewListTotalElements(@Param("cri") PageCriteria cri);

    AdminReviewDetailDTO findDetailByReviewId(@Param("id") long reviewId);

    void saveReview(ProductReview entity);
}
