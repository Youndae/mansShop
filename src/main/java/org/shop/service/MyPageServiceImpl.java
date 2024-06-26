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
    public String modifyCheckProc(String userPw, Principal principal) {
        String checkPw = myPageMapper.modifyCheck(principal.getName());

        if(passwordEncoder.matches(userPw, checkPw))
            return ResultProperties.SUCCESS;

        return ResultProperties.ERROR;
    }

    @Override
    public void modifyInfo(MemberModifyDTO dto, Principal principal) {

        if(!dto.getUserId().equals(principal.getName()))
            throw new AccessDeniedException("userId not equals principal");

        myPageMapper.modifyInfo(Member.builder()
                                    .userId(dto.getUserId())
                                    .userName(dto.getUserName())
                                    .userPhone(dto.getUserPhone())
                                    .userEmail(dto.getUserEmail())
                                    .build()
                            );
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
    @Transactional(rollbackFor = Exception.class)
    public String insertReviewProc(ProductReviewInsertDTO dto, Principal principal) {
        ProductReview productReview = ProductReview.builder()
                                                    .pno(dto.getPno())
                                                    .userId(principal.getName())
                                                    .reviewContent(dto.getReviewContent())
                                                    .build();

        myPageMapper.insertProductReview(productReview);
        myPageMapper.reviewStatUp(productReview.getPno(), dto.getOrderNo());

        return ResultProperties.SUCCESS;
    }

    @Override
    public String insertMyQnAProc(MyQnAInsertDTO dto, Principal principal) {

        myPageMapper.insertMemberQnA(MyQnA.builder()
                                        .userId(principal.getName())
                                        .qTitle(dto.getQTitle())
                                        .qContent(dto.getQContent())
                                        .build()
                                );

        return ResultProperties.SUCCESS;
    }

    @Override
    public String memberReviewModify(ProductReviewModifyDTO dto) {

        myPageMapper.memberReviewModify(ProductReview.builder()
                                        .rNum(dto.getRNum())
                                        .reviewContent(dto.getReviewContent())
                                        .build());

        return ResultProperties.SUCCESS;
    }


    @Override
    public String deleteReview(long rNum) {
        try {
            myPageMapper.deleteReview(rNum);
            return ResultProperties.SUCCESS;
        }catch (Exception e) {
            log.error("deleteReview Exception : " + e.getMessage());
            return ResultProperties.ERROR;
        }
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



        chatMapper.createChatRoom(ChatRoom.builder()
                                        .chatRoomId(chatRoomId)
                                        .userId(uid)
                                        .build()
                                );

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
