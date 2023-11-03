package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.dto.member.MemberDTO;
import org.shop.domain.dto.myPage.*;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.entity.*;

import java.util.Date;
import java.util.List;

public interface MyPageMapper {

    public MemberModifyDTO getModifyInfo(String userId);

    public MemberDTO getUserInfo(String userId);

    public void modifyInfo(Member member);

    public String modifyCheck(String userId);

    public List<CartDetailDTO> getCartList(Cart cart);

    public int cartCount(Cart cart);

    public void deleteCart(Cart cart);

    public void deleteCartDetail(List<String> cdNo);

    public void cartUp(CartDetail cartDetail);

    public void cartDown(CartDetail cartDetail);

    public List<MemberOrderListDTO> memberOrderList(@Param("userId") String userId, @Param("regDate") Date regDate);

    public String reviewProductInfo(String pno);

    public void insertProductReview(ProductReview productReview);

    public void reviewStatUp(@Param("pno") String pno, @Param("orderNo") String orderNo);

    public void deleteReview(long rNum);

    public List<MemberQnAListDTO> memberQnAList(Criteria cri);

    public int getQnATotal(Criteria cri);

    public MemberQnADetailDTO memberQnADetail(long qno);

    public List<MemberQnAReplyListDTO> memberQnAReply(long qno);

    public void insertMemberQnA(MyQnA myQna);

    public List<MemberReviewListDTO> memberReviewList(Criteria cri);

    public int getReviewTotal(Criteria cri);

    public MemberReviewDetailDTO memberReviewDetail(long rNum);

    public void memberReviewModify(ProductReview productReview);

    public List<LikeListDTO> likeList(Criteria cri);

    public int getLikeTotal(Criteria cri);











































}
