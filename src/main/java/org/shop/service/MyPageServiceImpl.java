package org.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.ResultProperties;
import org.shop.domain.dto.myPage.*;
import org.shop.domain.entity.*;
import org.shop.mapper.ChatMapper;
import org.shop.mapper.MyPageMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Log4j
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{

    private final MyPageMapper myPageMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    private final CookieService cookieService;

    private final ChatMapper chatMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int deleteCart(List<String> cdNoList
            , Principal principal
            , HttpServletRequest request
            , HttpServletResponse response) throws Exception{

        Cart cart;

        try{
            cart = cookieService.checkCookie(request, principal, response, false);
        }catch (Exception e){
            e.printStackTrace();
            return ResultProperties.ERROR;
        }


        if(myPageMapper.cartCount(cart) == cdNoList.size()){ //전체 삭제라면
            //cart테이블에서 해당 아이디의 데이터 삭제
            log.info("all delete");
            myPageMapper.deleteCart(cart);

            //비회원이 전체 삭제를 한 경우에는 쿠키를 삭제한다.
            if(cart.getUserId() == null)
                cookieService.deleteCookie(request, response);

        }else{
            log.info("delete detail");
            myPageMapper.deleteCartDetail(cdNoList);
        }
        return ResultProperties.SUCCESS;

    }

    @Override
    public int modifyCheckProc(String userPw, Principal principal) {
        log.info("modifyCheckProc");

        String checkPw = myPageMapper.modifyCheck(principal.getName());

        if(passwordEncoder.matches(userPw, checkPw)){
            log.info("true");
            return ResultProperties.SUCCESS;
        }else{
            log.info("false");
            return ResultProperties.ERROR;
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int cartCount(String cdNo, String cPrice, String countType) throws Exception{

        CartDetail cartDetail = CartDetail.builder()
                .cdNo(cdNo)
                .cPrice(Long.parseLong(cPrice))
                .build();

        log.info("count impl type : " + countType);

        if(countType.equals("up")){
            log.info("up");
            myPageMapper.cartUp(cartDetail);
        }else if(countType.equals("down")){
            log.info("down");
            myPageMapper.cartDown(cartDetail);
        }

        log.info("impl complete");
        return ResultProperties.SUCCESS;

    }


    @Override
    public int modifyInfo(MemberModifyDTO dto, Principal principal) {

        if(!dto.getUserId().equals(principal.getName()))
            return ResultProperties.ERROR;

        myPageMapper.modifyInfo(Member.builder()
                .userId(dto.getUserId())
                .userName(dto.getUserName())
                .userPhone(dto.getUserPhone())
                .userEmail(dto.getUserEmail())
                .build());

        return ResultProperties.SUCCESS;
    }

    @Override
    public List<MemberOrderListDTO> getOrderList(String regDate, Principal principal) {

        try{
            SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd");
            Date date = fm.parse(regDate);
            return myPageMapper.memberOrderList(principal.getName(), date);
        }catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int insertReviewProc(ProductReviewInsertDTO dto, String orderNo, Principal principal) throws Exception{


        ProductReview productReview = ProductReview.builder()
                .pno(dto.getPno())
                .userId(principal.getName())
                .reviewContent(dto.getReviewContent())
                .build();

        myPageMapper.insertProductReview(productReview);

        myPageMapper.reviewStatUp(productReview.getPno(), orderNo);

        return ResultProperties.SUCCESS;

    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int insertMyQnAProc(MyQnAInsertDTO dto, Principal principal) throws Exception{

        MyQnA myQna = MyQnA.builder()
                .userId(principal.getName())
                .qTitle(dto.getQTitle())
                .qContent(dto.getQContent())
                .build();

        myPageMapper.insertMemberQnA(myQna);
        return ResultProperties.SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int memberReviewModify(ProductReviewModifyDTO dto) throws Exception{

        myPageMapper.memberReviewModify(ProductReview.builder()
                                        .rNum(dto.getRNum())
                                        .reviewContent(dto.getReviewContent())
                                        .build());
        return ResultProperties.SUCCESS;

    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int deleteReview(long rNum) throws Exception{

        myPageMapper.deleteReview(rNum);
        return ResultProperties.SUCCESS;
    }

    @Override
    public String createChatRoom(Principal principal) {

        /** 방번호 생성. 기능 완료 후 수정 */
        StringBuffer sb = new StringBuffer();

        String uid = principal.getName();
        //상담이 마무리되지 않은 방이 있다면(chatStatus == 0)
        String duplicationRoomId = chatMapper.duplicationCheck(uid);
        if(duplicationRoomId != null)
            return duplicationRoomId;

        String chatRoomId = sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()))
                            .append(uid)
                            .toString();

        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomId(chatRoomId)
                .userId(uid)
                .build();

        chatMapper.createChatRoom(chatRoom);

        return chatRoomId;
    }

    @Override
    public String findByUserRoomId(String chatRoomId, Principal principal) {
        /**
         * 현재는 해당 방을 생성한 사용자가 맞는지만 체크.
         *
         * 추후 이전 채팅 내역까지 출력하도록 할 때 기능 추가.
         */

        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomId(chatRoomId)
                .userId(principal.getName())
                .build();

        if(chatMapper.checkUser(chatRoom) == 0)
            throw new AccessDeniedException("Access Denied");

        return chatRoomId;
    }
}
