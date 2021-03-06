package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.*;

import java.util.Date;
import java.util.List;

public interface MyPageMapper {

    public String modifyCheck(MemberVO memberVO);

    public void modifyInfo(MemberVO memberVO);

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

    public List<CartDetailDTO> getCartList(CartVO cartVO);

    public void deleteCart(CartVO cartVO);

    public void deleteCartDetail(@Param("cdNo") String cdNo);

    public int cartCount(CartVO cartVO);

    public void cartUp(CartDetailVO cartDetailVO);

    public void cartDown(CartDetailVO cartDetailVO);

}
