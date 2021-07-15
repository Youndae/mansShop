package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.*;

import java.util.Date;
import java.util.List;

public interface MyPageMapper {

    public void modifyInfo();

    public MemberVO getModifyInfo(String userId);

    public List<MemberOrderListDTO> memberOrderList(@Param("userId") String userId, @Param("regDate") Date regDate);

    public void insertReview();

    public List<MyQnAVO> memberQnAList();

    public void memberQnADetail();

    public void insertMemberQnA();

    public List<LikeVO> likeList();

    public List<MyQnAReplyVO> memberQnAReply();

    public void insertMemberQnAReply();

    public List<CartVO> getCartList(String userId);

    public void deleteCart(@Param("userId") String userId, @Param("pOpNo") String pOpNo);

}
