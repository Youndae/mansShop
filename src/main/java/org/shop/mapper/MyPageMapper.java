package org.shop.mapper;

import org.shop.domain.LikeVO;
import org.shop.domain.MyQnAReplyVO;
import org.shop.domain.MyQnAVO;
import org.shop.domain.OrderVO;

import java.util.List;

public interface MyPageMapper {

    public void modifyInfo();

    public void getModifyInfo();

    public List<OrderVO> memberOrderList();

    public void insertReview();

    public List<MyQnAVO> memberQnAList();

    public void memberQnADetail();

    public void insertMemberQnA();

    public List<LikeVO> likeList();

    public List<MyQnAReplyVO> memberQnAReply();

    public void insertMemberQnAReply();

}
