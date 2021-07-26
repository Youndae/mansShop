package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.*;

import java.util.Date;
import java.util.List;

public interface MyPageMapper {

    public String modifyCheck(MemberVO memberVO);

    public MemberVO getModifyInfo(String userId);

    public List<MemberOrderListDTO> memberOrderList(@Param("userId") String userId, @Param("regDate") Date regDate);

    public ProductOpVO reviewProductInfo(String pOpNo);

    public void insertProductReview(ProductReviewVO productReviewVO);

    public void reviewStatUp(@Param("pno") String pno, @Param("orderNo") String orderNo);

    public List<ProductReviewVO> memberReviewList(Criteria cri);

    public ProductReviewVO memberReviewDetail(long rNum);

    public void memberReviewModify(ProductReviewVO productReviewVO);

    public int getReviewTotal(Criteria cri);

    public void deleteReview(long rNum);

    public List<MyQnAVO> memberQnAList(Criteria cri);

    public int getQnATotal(Criteria cri);

    public MyQnAVO memberQnADetail(long qno);

    public List<MyQnAReplyVO> memberQnAReply(long qno);

    public void insertMemberQnA(MyQnAVO myQnAVO);

    public List<LikeVO> likeList(Criteria cri);

    public int getLikeTotal(Criteria cri);

    public void insertMemberQnAReply();

    public List<CartVO> getCartList(String userId);

    public void deleteCart(@Param("userId") String userId, @Param("pOpNo") String pOpNo);

    public void cartUp(CartVO cartVO);

    public void cartDown(CartVO cartVO);

}
