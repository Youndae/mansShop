package org.shop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.*;
import org.shop.mapper.MyPageMapper;
import org.shop.service.MyPageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RequestMapping("/myPage/")
@Controller
@Log4j
@AllArgsConstructor
/*@PreAuthorize("isAuthenticated()")*/
public class MyPageController {

    private MyPageService myPageService;

    private MyPageMapper myPageMapper;

    @GetMapping("/modifyCheck")
    public void modifyCheck(){

    }

    @PostMapping("/modifyCheck")
    @ResponseBody
    public String modifyCheckProc(MemberVO memberVO, Principal principal){

        /*memberVO.setUserId(principal.getName());*/

        memberVO.setUserId("coco");

        log.info("modifyCheck : " + memberVO);

        if(myPageService.modifyCheckProc(memberVO) == 0){
            log.info("modify check controller return false");
            return "0";
        }else{
            log.info("modify check controller return true");
            return "1";
        }

    }

    @GetMapping("/modifyInfo")
    /*@PreAuthorize("hasRole('ROLE_MEMBER')")*/
    public void getModifyInfo(Model model, Principal principal){
        //정보 수정 창
        String userId = principal.getName();

        log.info("MyPage Controller " + myPageMapper.getModifyInfo(userId));

        model.addAttribute("info", myPageMapper.getModifyInfo(userId));
    }

    @PostMapping("/modifyInfo")
    public String modifyInfo(MemberVO memberVO){
        //정보 수정 처리

        log.info("modify memberVO : " + memberVO);

        myPageMapper.modifyInfo(memberVO);

        return "redirect:/myPage/modifyInfo";
    }

    @GetMapping("/memberOrderList")
    public String memberOrderList(Principal principal){
        //회원 주문 내역

        if(principal == null){
            return "/main/orderInfoCheck";
        }else{
            return "/myPage/memberOrderList";
        }

    }

    @GetMapping("/selectOrderList")
    @ResponseBody
    public ResponseEntity<List<MemberOrderListDTO>> getOrderList(String regDate, Principal principal) throws ParseException {

        log.info("select OrderList");
        log.info("regDate : " + regDate);

        /*String userId = principal.getName();
        log.info("userId : " + userId);*/

        String userId = "coco";

        SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd");
        Date date = fm.parse(regDate);

        log.info("orderList controller date : " + date);

        log.info(myPageMapper.memberOrderList(userId, date));

        return new ResponseEntity<>(myPageMapper.memberOrderList(userId, date), HttpStatus.OK);
    }

    @GetMapping("/memberReviewList")
    public void memberReviewList(Model model, Principal principal, Criteria cri){
        //회원 리뷰 내역

        /*cri.setKeyword(principal.getName());*/
        cri.setKeyword("coco");

        log.info("member Review List : " + myPageMapper.memberReviewList(cri));

        model.addAttribute("rList", myPageMapper.memberReviewList(cri));

        int total = myPageMapper.getReviewTotal(cri);

        model.addAttribute("pageMaker", new PageDTO(cri, total));


    }

    @GetMapping("/memberReviewDetail/{rNum}")
    public String memberReviewDetail(Model model, @PathVariable long rNum){

        model.addAttribute("rDetail", myPageMapper.memberReviewDetail(rNum));

        return "/myPage/memberReviewDetail";
    }

    @GetMapping("/memberReviewModify/{rNum}")
    public String memberReviewModify(Model model, @PathVariable long rNum){

        model.addAttribute("rModify", myPageMapper.memberReviewDetail(rNum));

        return "/myPage/memberReviewModify";
    }

    @PostMapping("/memberReviewModify")
    public String memberReviewModifyProc(ProductReviewVO productReviewVO){

        log.info("modify rNum : " + productReviewVO.getRNum());

        myPageMapper.memberReviewModify(productReviewVO);

        return "redirect:/myPage/memberReviewDetail/"+productReviewVO.getRNum();
    }

    @GetMapping("/memberReviewDelete/{rNum}")
    public String memberReviewDelete(@PathVariable long rNum){

        log.info("memberReviewDelete");

        myPageMapper.deleteReview(rNum);

        return "redirect:/myPage/memberReviewList";
    }

    @GetMapping("/orderReview/{pOpNo}/{pno}/{orderNo}")
    public String getInsertReview(Model model, @PathVariable String pOpNo,
                                  @PathVariable String pno, @PathVariable String orderNo){
        //리뷰 작성 페이지
        log.info("orderReview VO : " + pOpNo);

        model.addAttribute("pno", pno);
        model.addAttribute("orderNo", orderNo);

        model.addAttribute("pInfo", myPageMapper.reviewProductInfo(pOpNo));

        return "/myPage/orderReview";
    }

    @PostMapping("/orderReview")
    public String insertReview(ProductReviewVO productReviewVO, @RequestParam("orderNo") String orderNo, Principal principal){
        //리뷰작성 처리

        log.info("order Review");

        productReviewVO.setUserId(principal.getName());

        long n = 21;

        productReviewVO.setRNum(n);

        log.info("orderReview reviewVO : " + productReviewVO);
        log.info("orderReview pno : " + productReviewVO.getPno());
        log.info("orderReview orderNo : " + orderNo);



        myPageMapper.insertProductReview(productReviewVO);

        myPageMapper.reviewStatUp(productReviewVO.getPno(), orderNo);

        return "redirect:/myPage/memberOrderList";
    }

    @GetMapping("/memberQnAList")
    public void memberQnAList(Model model, Principal principal, Criteria cri){
        //회원 QnA 목록
        log.info("memberQnAList");

        /*cri.setKeyword(principal.getName());*/
        cri.setKeyword("coco");

        model.addAttribute("qList", myPageMapper.memberQnAList(cri));

        int total = myPageMapper.getQnATotal(cri);

        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }

    @GetMapping("/memberQnADetail/{qno}")
    public String memberQnADetail(@PathVariable long qno, Model model){
        //회원 QnA Detail
        log.info("myQnADetail qno : " + qno);

        model.addAttribute("qDetail", myPageMapper.memberQnADetail(qno));

        model.addAttribute("qReply", myPageMapper.memberQnAReply(qno));

        return "/myPage/memberQnADetail";
    }

    @GetMapping("/insertMemberQnA")
    public void getInsertMemberQnA(){
        //회원 QnA 작성 페이지
    }

    @PostMapping("/insertMemberQnA")
    public String insertMemberQnA(MyQnAVO myQnAVO, Principal principal){
        //회원 QnA 작성 처리

        long a = 21;//시퀀스 처리.

        myQnAVO.setQno(a);

        myQnAVO.setUserId(principal.getName());

        myPageMapper.insertMemberQnA(myQnAVO);

        return "redirect:/myPage/memberQnAList";
    }

    @GetMapping("/likeList")
    public void likeList(Model model, Principal principal, Criteria cri){
        //찜목록

        cri.setKeyword(principal.getName());

        /*cri.setKeyword("coco");*/

        cri.setAmount(8);

        model.addAttribute("lList", myPageMapper.likeList(cri));

        int total = myPageMapper.getLikeTotal(cri);

        model.addAttribute("pageMaker", new PageDTO(cri, total));
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

    @PostMapping("/deleteCart")
    @ResponseBody
    public void deleteCart(@RequestParam("pOpNo")List<String> pOpNoList, Principal principal){
        log.info("delete Cart : " + pOpNoList);

        myPageService.deleteCart(principal.getName(), pOpNoList);

    }

    @PostMapping("/cartCount")
    @ResponseBody
    public void cartUp(@RequestParam("pOpNo")String pOpNo, @RequestParam("cPrice")String cPrice, @RequestParam("countType") String countType, Principal principal){
        log.info("cartUp!");

        log.info("popno List : " + pOpNo);
        log.info("cPrice List : " + cPrice);
        log.info("type : " + countType);

        myPageService.cartCount(pOpNo, cPrice, countType, principal);
    }

}
