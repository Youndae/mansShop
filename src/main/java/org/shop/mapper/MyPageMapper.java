package org.shop.mapper;

import org.shop.domain.*;

import java.util.List;

public interface MyPageMapper {

    public void modifyInfo();

    public MemberVO getModifyInfo(String userId);

    public List<OrderVO> memberOrderList();

    public void insertReview();

    public List<MyQnAVO> memberQnAList();

    public void memberQnADetail();

    public void insertMemberQnA();

    public List<LikeVO> likeList();

    public List<MyQnAReplyVO> memberQnAReply();

    public void insertMemberQnAReply();

}
