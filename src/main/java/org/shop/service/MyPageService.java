package org.shop.service;

import org.shop.domain.dto.myPage.in.MemberCheckRequestDTO;
import org.shop.domain.dto.myPage.in.MemberInfoRequestDTO;
import org.shop.domain.dto.myPage.qna.in.MemberQnARequestDTO;
import org.shop.domain.dto.myPage.qna.in.QnAReplyRequestDTO;
import org.shop.domain.dto.myPage.out.*;
import org.shop.domain.dto.myPage.qna.out.*;
import org.shop.domain.dto.paging.Criteria;

import java.util.List;

public interface MyPageService {

    List<LikeListDTO> getLikeList(Criteria cri, String userId);

    int getLikeTotalElements(String userId);

    List<MyPageProductQnAListDTO> getProductQnAList(String userId, Criteria cri);

    int getProductQnATotalElements(String userId);

    List<MyPageMemberQnAListDTO> getMemberQnAList(String userId, Criteria cri);

    int getMemberQnATotalElements(String userId);

    List<MyPageReviewListDTO> getReviewList(String userId, Criteria cri);

    int getReviewTotalElements(String userId);

    MyPageMemberInfoDTO getMemberInfo(String userId);

    MyPageReviewDetailDTO getReviewDetail(long reviewId, String userId);

    String patchMemberInfo(MemberInfoRequestDTO dto, String userId);

    MyPageProductQnADetailResponseDTO getProductQnADetail(long qnaId, String userId);

    MyPageMemberQnADetailResponseDTO getMemberQnADetail(long qnaId, String userId);

    String patchMemberQnAReply(QnAReplyRequestDTO dto, String userId);

    String postMemberQnAReply(QnAReplyRequestDTO dto, String userId);

    List<MyPageQnAClassificationDTO> getQnAClassification();

    MemberQnAPatchResponseDTO getMemberQnAPatchData(long qnaId, String userId);

    Long postMemberQnA(MemberQnARequestDTO dto, String userId);

    Long patchMemberQnA(long qnaId, MemberQnARequestDTO dto, String userId);

    String deleteMemberQnA(long qnaId, String userId);

    String deleteProductQnA(long qnaId, String userId);

    MyPageReviewPatchDTO getPatchReviewData(long reviewId, String userId);

    String patchReview(QnAReplyRequestDTO dto, String userId);

    String deleteReview(long reviewId, String userId);

    String checkMember(MemberCheckRequestDTO dto, String userId);

    String checkNickname(String nickname, String userId);

    String deleteLike(long likeId, String userId);

    MyPageReviewPostDTO getPostReviewData(long orderDetailId, String userId);

    String postReview(long orderDetailId, String content, String userId);
}
