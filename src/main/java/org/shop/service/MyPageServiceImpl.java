package org.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.dto.myPage.qna.business.MyPageMemberQnADetailDTO;
import org.shop.domain.dto.myPage.qna.business.MyPageProductQnADetailDTO;
import org.shop.domain.dto.myPage.qna.business.QnAReplyListDTO;
import org.shop.domain.dto.myPage.in.MemberCheckRequestDTO;
import org.shop.domain.dto.myPage.in.MemberInfoRequestDTO;
import org.shop.domain.dto.myPage.qna.in.MemberQnARequestDTO;
import org.shop.domain.dto.myPage.qna.in.QnAReplyRequestDTO;
import org.shop.domain.dto.myPage.out.*;
import org.shop.domain.dto.myPage.qna.out.*;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.entity.*;
import org.shop.domain.enumeration.Result;
import org.shop.exception.customException.CustomAccessDeniedException;
import org.shop.exception.customException.CustomNotFoundException;
import org.shop.exception.enumeration.ErrorCode;
import org.shop.mapper.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{

    private final MyPageMapper myPageMapper;

    private final MemberMapper memberMapper;

    private final ProductQnAMapper productQnAMapper;

    private final ProductQnAReplyMapper productQnAReplyMapper;

    private final MemberQnAMapper memberQnAMapper;

    private final MemberQnAReplyMapper memberQnAReplyMapper;

    private final QnAClassificationMapper qnAClassificationMapper;

    private final ProductReviewMapper productReviewMapper;

    private final BCryptPasswordEncoder encoder;

    private final ProductLikeMapper productLikeMapper;

    private final ProductOrderDetailMapper productOrderDetailMapper;


    @Override
    public List<LikeListDTO> getLikeList(Criteria cri, String userId) {

        return myPageMapper.getLikeList(userId, cri);
    }

    @Override
    public int getLikeTotalElements(String userId) {

        return myPageMapper.getLikeTotalElements(userId);
    }

    @Override
    public List<MyPageProductQnAListDTO> getProductQnAList(String userId, Criteria cri) {
        return myPageMapper.getProductQnAList(userId, cri);
    }

    @Override
    public int getProductQnATotalElements(String userId) {
        return myPageMapper.getProductQnATotalElements(userId);
    }

    @Override
    public List<MyPageMemberQnAListDTO> getMemberQnAList(String userId, Criteria cri) {
        return myPageMapper.getMemberQnAList(userId, cri);
    }

    @Override
    public int getMemberQnATotalElements(String userId) {
        return myPageMapper.getMemberQnATotalElements(userId);
    }

    @Override
    public List<MyPageReviewListDTO> getReviewList(String userId, Criteria cri) {
        return myPageMapper.getReviewList(userId, cri);
    }

    @Override
    public int getReviewTotalElements(String userId) {
        return myPageMapper.getReviewTotalElements(userId);
    }

    @Override
    public MyPageMemberInfoDTO getMemberInfo(String userId) {
        Member member = memberMapper.getInfo(userId);
        String[] splitMail = member.getUserEmail().split("@");

        return new MyPageMemberInfoDTO(member, splitMail);
    }

    @Override
    public MyPageReviewDetailDTO getReviewDetail(long reviewId, String userId) {
        return myPageMapper.getReviewDetail(reviewId, userId);
    }

    @Override
    public MyPageProductQnADetailResponseDTO getProductQnADetail(long qnaId, String userId) {
        MyPageProductQnADetailDTO dto = productQnAMapper.findDetailByQnAIdAndUserId(qnaId, userId);

        if(dto == null)
            throw new CustomNotFoundException(ErrorCode.NOT_FOUND, ErrorCode.NOT_FOUND.getMessage());

        List<QnAReplyListDTO> replyList = productQnAReplyMapper.findAllByQnAId(qnaId);

        return new MyPageProductQnADetailResponseDTO(dto, replyList);
    }

    @Override
    public MyPageMemberQnADetailResponseDTO getMemberQnADetail(long qnaId, String userId) {

        MyPageMemberQnADetailDTO dto = memberQnAMapper.findDetailByIdAndUserId(qnaId, userId);

        if(dto == null)
            throw new CustomNotFoundException(ErrorCode.NOT_FOUND, ErrorCode.NOT_FOUND.getMessage());

        List<QnAReplyListDTO> replyList = memberQnAReplyMapper.findAllByQnAId(qnaId);

        return new MyPageMemberQnADetailResponseDTO(dto, replyList);
    }

    @Override
    public String patchMemberQnAReply(QnAReplyRequestDTO dto, String userId) {

        MemberQnAReply entity = memberQnAReplyMapper.findById(dto.getId());

        if(!entity.getUserId().equals(userId))
            throw new CustomAccessDeniedException(ErrorCode.ACCESS_DENIED, ErrorCode.ACCESS_DENIED.getMessage());

        entity.setReplyContent(dto.getContent());

        memberQnAReplyMapper.patchContent(entity);

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public String postMemberQnAReply(QnAReplyRequestDTO dto, String userId) {
        MemberQnA entity = memberQnAMapper.findById(dto.getId());

        if(!entity.getUserId().equals(userId))
            throw new CustomAccessDeniedException(ErrorCode.ACCESS_DENIED, ErrorCode.ACCESS_DENIED.getMessage());

        memberQnAReplyMapper.insertReply(dto.toMemberQnAReplyEntity(userId));
        memberQnAMapper.patchStatus(dto.getId());

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public List<MyPageQnAClassificationDTO> getQnAClassification() {
        List<QnAClassification> entity = qnAClassificationMapper.findAll();

        return entity.stream()
                    .map(QnAClassification::fromDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public MemberQnAPatchResponseDTO getMemberQnAPatchData(long qnaId, String userId) {
        MemberQnA entity = memberQnAMapper.findById(qnaId);

        if(!entity.getUserId().equals(userId))
            throw new CustomAccessDeniedException(ErrorCode.ACCESS_DENIED, ErrorCode.ACCESS_DENIED.getMessage());

        return new MemberQnAPatchResponseDTO(entity);
    }

    @Override
    public Long postMemberQnA(MemberQnARequestDTO dto, String userId) {
        MemberQnA entity = dto.toEntity(userId);
        memberQnAMapper.saveEntity(entity);

        return entity.getId();
    }

    @Override
    public Long patchMemberQnA(long qnaId, MemberQnARequestDTO dto, String userId) {
        MemberQnA entity = memberQnAMapper.findById(qnaId);

        if(!entity.getUserId().equals(userId))
            throw new CustomAccessDeniedException(ErrorCode.ACCESS_DENIED, ErrorCode.ACCESS_DENIED.getMessage());

        memberQnAMapper.patchQnA(qnaId, dto);

        return qnaId;
    }

    @Override
    public String deleteMemberQnA(long qnaId, String userId) {
        MemberQnA entity = memberQnAMapper.findById(qnaId);

        if(!entity.getUserId().equals(userId))
            throw new CustomAccessDeniedException(ErrorCode.ACCESS_DENIED, ErrorCode.ACCESS_DENIED.getMessage());

        memberQnAMapper.deleteById(qnaId);

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public String deleteProductQnA(long qnaId, String userId) {
        ProductQnA entity = productQnAMapper.findById(qnaId);

        if(!entity.getUserId().equals(userId))
            throw new CustomAccessDeniedException(ErrorCode.ACCESS_DENIED, ErrorCode.ACCESS_DENIED.getMessage());

        productQnAMapper.deleteById(qnaId);

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public MyPageReviewPatchDTO getPatchReviewData(long reviewId, String userId) {
        MyPageReviewPatchDTO dto = productReviewMapper.findPatchDataByReviewIdAndUserId(reviewId, userId);

        if(dto == null)
            throw new CustomNotFoundException(ErrorCode.NOT_FOUND, ErrorCode.NOT_FOUND.getMessage());

        return dto;
    }

    @Override
    public MyPageReviewPostDTO getPostReviewData(long orderDetailId, String userId) {
        MyPageReviewPostDTO dto = productOrderDetailMapper.findPostReviewDetailData(orderDetailId, userId);

        if(dto == null)
            throw new CustomNotFoundException(ErrorCode.NOT_FOUND, ErrorCode.NOT_FOUND.getMessage());

        return dto;
    }

    @Override
    public String postReview(long orderDetailId, String content, String userId) {
        ProductOrderDetail orderDetail = productOrderDetailMapper.findById(orderDetailId);
        ProductReview entity = ProductReview.builder()
                                            .productId(orderDetail.getProductId())
                                            .productOptionId(orderDetail.getProductOptionId())
                                            .userId(userId)
                                            .reviewContent(content)
                                            .build();
        productReviewMapper.saveReview(entity);
        productOrderDetailMapper.patchReviewStatus(orderDetailId);

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public String patchReview(QnAReplyRequestDTO dto, String userId) {
        ProductReview entity = productReviewMapper.findById(dto.getId());

        if(!entity.getUserId().equals(userId))
            throw new CustomAccessDeniedException(ErrorCode.ACCESS_DENIED, ErrorCode.ACCESS_DENIED.getMessage());

        productReviewMapper.patchReview(dto);

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public String deleteReview(long reviewId, String userId) {
        ProductReview entity = productReviewMapper.findById(reviewId);

        if(!entity.getUserId().equals(userId))
            throw new CustomAccessDeniedException(ErrorCode.ACCESS_DENIED, ErrorCode.ACCESS_DENIED.getMessage());

        productReviewMapper.deleteById(reviewId);

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public String checkMember(MemberCheckRequestDTO dto, String userId) {
        if(!dto.getUserId().equals(userId))
            throw new CustomAccessDeniedException(ErrorCode.ACCESS_DENIED, ErrorCode.ACCESS_DENIED.getMessage());

        Member entity = memberMapper.findById(dto.getUserId());

        if(encoder.matches(dto.getUserPw(), entity.getUserPw()))
            return Result.SUCCESS.getResultKey();
        else
            return Result.FAIL.getResultKey();
    }

    @Override
    public String checkNickname(String nickname, String userId) {
        Member member = memberMapper.findByNickname(nickname);

        if(member == null || member.getUserId().equals(userId))
            return Result.OK.getResultKey();
        else
            return Result.DUPLICATE.getResultKey();
    }

    @Override
    public String patchMemberInfo(MemberInfoRequestDTO dto, String userId) {
        try {
            Member entity = dto.toEntity(userId);
            memberMapper.patchMember(entity);
        }catch (Exception e) {
            log.error("MyPageServiceImpl.patchMemberInfo error");
            e.printStackTrace();
            return Result.FAIL.getResultKey();
        }

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public String deleteLike(long likeId, String userId) {
        ProductLike entity = productLikeMapper.findById(likeId);

        if(!entity.getUserId().equals(userId))
            throw new CustomAccessDeniedException(ErrorCode.ACCESS_DENIED, ErrorCode.ACCESS_DENIED.getMessage());

        productLikeMapper.deleteById(likeId);

        return Result.SUCCESS.getResultKey();
    }
}
