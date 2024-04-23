package org.shop.service;

import org.shop.domain.dto.myPage.*;
import org.shop.domain.entity.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

public interface MyPageService {

    public String modifyCheckProc(String userPw, Principal principal);

    public void modifyInfo(MemberModifyDTO dto, Principal principal);

    public List<MemberOrderListDTO> getOrderList(String regDate, Principal principal);

    public String insertReviewProc(ProductReviewInsertDTO dto, Principal principal);

    public String insertMyQnAProc(MyQnAInsertDTO dto, Principal principal);

    String memberReviewModify(ProductReviewModifyDTO dto);

    String deleteReview(long rNum);

    public String createChatRoom(Principal principal);

    public String findByUserRoomId(String chatRoomId, Principal principal);
}
