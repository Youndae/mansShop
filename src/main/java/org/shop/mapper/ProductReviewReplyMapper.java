package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.dto.admin.review.business.AdminReviewReplyDTO;

public interface ProductReviewReplyMapper {
    AdminReviewReplyDTO findDetailByReviewId(@Param("id") long reviewId);

    void saveReply(@Param("id") long reviewId, @Param("content") String content, @Param("userId") String userId);
}
