package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.dto.myPage.out.*;
import org.shop.domain.dto.myPage.qna.out.MyPageMemberQnAListDTO;
import org.shop.domain.dto.myPage.qna.out.MyPageProductQnAListDTO;
import org.shop.domain.dto.paging.Criteria;

import java.util.List;

public interface MyPageMapper {

    List<LikeListDTO> getLikeList(@Param("userId") String userId, @Param("cri") Criteria cri);

    int getLikeTotalElements(@Param("userId") String userId);

    List<MyPageProductQnAListDTO> getProductQnAList(@Param("userId") String userId, @Param("cri") Criteria cri);

    int getProductQnATotalElements(@Param("userId") String userId);

    List<MyPageMemberQnAListDTO> getMemberQnAList(@Param("userId") String userId, @Param("cri") Criteria cri);

    int getMemberQnATotalElements(@Param("userId") String userId);

    List<MyPageReviewListDTO> getReviewList(@Param("userId") String userId, @Param("cri") Criteria cri);

    int getReviewTotalElements(@Param("userId") String userId);

    MyPageReviewDetailDTO getReviewDetail(@Param("reviewId") long reviewId, @Param("userId") String userId);
}
