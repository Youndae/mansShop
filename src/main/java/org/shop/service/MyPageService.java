package org.shop.service;

import org.shop.domain.dto.myPage.*;
import org.shop.domain.entity.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

public interface MyPageService {

    public int deleteCart(List<String> cdNoList, Principal principal, HttpServletRequest request, HttpServletResponse response) throws Exception;

    public int modifyCheckProc(String userPw, Principal principal);

    public int cartCount(String cdNo, String cPrice, String countType) throws Exception;

    public int modifyInfo(MemberModifyDTO dto, Principal principal);

    public List<MemberOrderListDTO> getOrderList(String regDate, Principal principal);

    public int insertReviewProc(ProductReviewInsertDTO dto, String orderNo, Principal principal) throws Exception;

    public int insertMyQnAProc(MyQnAInsertDTO dto, Principal principal) throws Exception;

    int memberReviewModify(ProductReviewModifyDTO dto) throws Exception;

    int deleteReview(long rNum) throws Exception;

    public String createChatRoom(Principal principal);

    public String findByUserRoomId(String chatRoomId, Principal principal);
}
