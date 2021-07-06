package org.shop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.MemberVO;
import org.shop.domain.MyQnAVO;
import org.shop.mapper.MyPageMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping("/myPage/")
@Controller
@Log4j
@AllArgsConstructor
@PreAuthorize("isAuthenticated()")
public class MyPageController {

    //private MyPageService myPageService;

    private MyPageMapper myPageMapper;

    @GetMapping("/ModifyInfo")
    /*@PreAuthorize("hasRole('ROLE_MEMBER')")*/
    public void getModifyInfo(Model model){
        //정보 수정 창
        String userId = "coco";

        log.info("MyPage Controller " + myPageMapper.getModifyInfo(userId));

        model.addAttribute("info", myPageMapper.getModifyInfo(userId));
    }

    @PostMapping("/ModifyInfo")
    public void modifyInfo(MemberVO memberVO){
        //정보 수정 처리
    }

    @GetMapping("/memberOrderList")
    public void memberOrderList(Model model){
        //회원 주문 내역
    }

    @GetMapping("/memberReviewList")
    public void memberReviewList(Model model){
        //회원 리뷰 내역
    }

    @GetMapping("/insertReview")
    public void getInsertReview(Model model){
        //리뷰 작성 페이지
    }

    @PostMapping("/insertReview")
    public void insertReview(){
        //리뷰작성 처리
    }

    @GetMapping("/memberQnAList")
    public void memberQnAList(Model model){
        //회원 QnA 목록
    }

    @GetMapping("/memberQnADetail")
    public void memberQnADetail(MyQnAVO myQnAVO){
        //회원 QnA Detail
    }

    @GetMapping("/insertMemberQnA")
    public void getInsertMemberQnA(){
        //회원 QnA 작성 페이지
    }

    @PostMapping("/insertMemberQnA")
    public void insertMemberQnA(){
        //회원 QnA 작성 처리
    }

    @GetMapping("/likeList")
    public void likeList(Model model){
        //찜목록
    }

    @PostMapping("/insertMemberReply")
    public void insertMemberReply(){
        //회원 QnA 댓글 작성 처리
    }

    @GetMapping("/cart")
    public void cart(Model model, Principal principal){
        //장바구니 페이지

        log.info("get Cart List");

        String id = principal.getName();

        model.addAttribute("cartList", myPageMapper.getCartList(id));
    }

}
