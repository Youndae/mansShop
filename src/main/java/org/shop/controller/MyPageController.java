package org.shop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.ResultProperties;
import org.shop.domain.dto.myPage.*;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.dto.paging.PageDTO;
import org.shop.domain.entity.Cart;
import org.shop.domain.entity.ProductOrder;
import org.shop.mapper.MyPageMapper;
import org.shop.service.CookieService;
import org.shop.service.MyPageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/myPage/")
@Controller
@Log4j
@AllArgsConstructor
public class MyPageController {

    private MyPageService myPageService;

    private MyPageMapper myPageMapper;

    private CookieService cookieService;

    //회원 정보 수정 페이지 접근 전 비밀번호 체크 페이지
    @GetMapping("/modifyCheck")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public void modifyCheck(){

    }

    //회원 정보 수정 페이지 접근 전 비밀번호 체크 처리.
    @PostMapping("/modifyCheck")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String modifyCheckProc(@RequestParam("userPw") String userPw, Principal principal){

        return String.valueOf(myPageService.modifyCheckProc(userPw, principal));
    }

    //회원 정보 수정 페이지
    @GetMapping("/modifyInfo")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public void getModifyInfo(Model model, Principal principal){
        String userId = principal.getName();

        model.addAttribute("info", myPageMapper.getModifyInfo(userId));
    }

    //회원 정보 수정 처리
    @PatchMapping("/modifyInfo")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String modifyInfo(MemberModifyDTO dto, Principal principal){

        if(myPageService.modifyInfo(dto, principal) == 0)
            return "/accessError";

        return "redirect:/myPage/modifyInfo";
    }

    //회원 주문 내역 페이지
    @GetMapping("/memberOrderList")
    public String memberOrderList(Principal principal){

        if(principal == null){
            return "/main/orderInfoCheck";
        }else{
            return "/myPage/memberOrderList";
        }

    }

    //회원 주문 내역 리스트 데이터
    @GetMapping("/selectOrderList")
    @ResponseBody
    public ResponseEntity<List<MemberOrderListDTO>> getOrderList(String regDate, Principal principal) {

        log.info("select OrderList");

        return new ResponseEntity<>(myPageService.getOrderList(regDate, principal), HttpStatus.OK);
    }

    //회원 리뷰 내역 페이지
    @GetMapping("/memberReviewList")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public void memberReviewList(Model model, Principal principal, Criteria cri){
        cri.setKeyword(principal.getName());

        model.addAttribute("rList", myPageMapper.memberReviewList(cri));

        int total = myPageMapper.getReviewTotal(cri);

        model.addAttribute("pageMaker", new PageDTO(cri, total));

    }

    //회원 리뷰 상세 페이지
    @GetMapping("/memberReviewDetail/{rNum}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String memberReviewDetail(Model model, @PathVariable long rNum){

        model.addAttribute("rDetail", myPageMapper.memberReviewDetail(rNum));

        return "/myPage/memberReviewDetail";
    }

    //회원 리뷰 수정 페이지
    @GetMapping("/memberReviewModify/{rNum}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String memberReviewModify(Model model, @PathVariable long rNum){

        model.addAttribute("rModify", myPageMapper.memberReviewDetail(rNum));

        return "/myPage/memberReviewModify";
    }

    //회원 리뷰 수정 처리
    @PatchMapping("/memberReviewModify")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String memberReviewModifyProc(ProductReviewModifyDTO dto){

        log.info("modify rNum : " + dto.getRNum());

        int result;

        try{
            result = myPageService.memberReviewModify(dto);
        }catch (Exception e){
            result = ResultProperties.ERROR;
        }

        if(result == ResultProperties.ERROR)
            return "/accessError";

        return "redirect:/myPage/memberReviewDetail/"+ dto.getRNum();
    }

    //회원 리뷰 삭제 처리
    @DeleteMapping("/memberReviewDelete/{rNum}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String memberReviewDelete(@PathVariable long rNum){

        log.info("memberReviewDelete rNum : " + rNum);

        int result;

        try{
            result = myPageService.deleteReview(rNum);
        }catch (Exception e){
            result = ResultProperties.ERROR;
        }

        return String.valueOf(result);
    }

    //리뷰 작성 페이지
    @GetMapping("/orderReview/{pno}/{orderNo}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String getInsertReview(Model model
                                , @PathVariable String pno
                                , @PathVariable String orderNo){

        ProductOrderInfoDTO dto = ProductOrderInfoDTO.builder()
                        .pno(pno)
                        .orderNo(orderNo)
                        .pName(myPageMapper.reviewProductInfo(pno))
                        .build();

        model.addAttribute("pInfo", dto);

        return "/myPage/orderReview";
    }

    //리뷰 작성 처리
    @PostMapping("/orderReview")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String insertReview(ProductReviewInsertDTO dto
                                , @RequestParam("orderNo") String orderNo
                                , Principal principal){

        log.info("order Review");

        int result;

        try{
            result = myPageService.insertReviewProc(dto, orderNo, principal);
        }catch (Exception e){
            result = ResultProperties.ERROR;
        }

        if(result == ResultProperties.ERROR)
            return "accessError";

        return "redirect:/myPage/memberOrderList";
    }

    //회원 문의사항 페이지
    @GetMapping("/memberQnAList")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public void memberQnAList(Model model, Principal principal, Criteria cri){
        //회원 QnA 목록
        log.info("memberQnAList");

        cri.setKeyword(principal.getName());

        model.addAttribute("qList", myPageMapper.memberQnAList(cri));

        int total = myPageMapper.getQnATotal(cri);

        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }

    //회원 문의사항 상세 페이지
    @GetMapping("/memberQnADetail/{qno}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String memberQnADetail(@PathVariable long qno, Model model){
        //회원 QnA Detail
        log.info("myQnADetail qno : " + qno);

        model.addAttribute("qDetail", myPageMapper.memberQnADetail(qno));

        model.addAttribute("qReply", myPageMapper.memberQnAReply(qno));

        return "/myPage/memberQnADetail";
    }

    //회원 문의사항 작성 페이지
    @GetMapping("/insertMemberQnA")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public void getInsertMemberQnA(){

    }

    //회원 문의사항 작성 처리
    @PostMapping("/insertMemberQnA")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String insertMemberQnA(MyQnAInsertDTO dto, Principal principal){

        int result;
        try{
            result = myPageService.insertMyQnAProc(dto, principal);
        }catch (Exception e){
            result = ResultProperties.ERROR;
        }

        if(result == ResultProperties.ERROR)
            return "accessError";

        return "redirect:/myPage/memberQnAList";
    }

    //회원 찜 목록 페이지
    @GetMapping("/likeList")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public void likeList(Model model, Principal principal, Criteria cri){
        cri.setKeyword(principal.getName());

        cri.setAmount(8);

        model.addAttribute("lList", myPageMapper.likeList(cri));

        int total = myPageMapper.getLikeTotal(cri);

        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }

    //회원 장바구니 페이지
    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpServletRequest request, HttpServletResponse response) {
        log.info("get Cart List");

        Cart cart = cookieService.checkCookie(request, principal, response, false);

        model.addAttribute("cartList", myPageMapper.getCartList(cart));

        return "myPage/cart";

    }

    //회원 장바구니 선택목록 삭제 처리
    @DeleteMapping("/deleteCart")
    @ResponseBody
    public String deleteCart(@RequestBody List<String> cdNoList, Principal principal, HttpServletRequest request, HttpServletResponse response) {
        log.info("delete Cart : " + cdNoList);

        int result;

        try{
            result = myPageService.deleteCart(cdNoList, principal, request, response);
        }catch (Exception e){
            result = ResultProperties.ERROR;
        }

        return String.valueOf(result);

    }

    //회원 장바구니 수량 증감
    @PostMapping("/cartCount")
    @ResponseBody
    public String cartUp(@RequestParam("cdNo")String cdNo, @RequestParam("cPrice")String cPrice, @RequestParam("countType") String countType){

        log.info("cdNo : " + cdNo + ", cPrice : " + cPrice + ", countType : " + countType);

        int result;
        try{
            result = myPageService.cartCount(cdNo, cPrice, countType);
        }catch (Exception e){
            result = ResultProperties.ERROR;
        }

        return String.valueOf(result);
    }

    //채팅 문의 리스트
    @GetMapping("/chatQnA")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String chatQnA(Principal principal, Model model){

        //채팅 저장 처리 및 내역 출력 기능 구현 후 서비스 호출해서 qList로 전달
        model.addAttribute("qList", null);

        return "myPage/memberChat";
    }

    //채팅 방 생성
    @PostMapping("/chatRoom")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @ResponseBody
    public String createChatRoom(Principal principal){
        log.info("Create ChatRoom user : " + principal.getName());

        return myPageService.createChatRoom(principal);
    }

    //채팅방 입장
    @GetMapping("/chatRoom/{chatRoomId}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String chatRoom(@PathVariable("chatRoomId") String chatRoomId, Model model, Principal principal){
        log.info(principal.getName() + "'s chatRoom connect");

        //채팅방 정보(내역) 전달
        model.addAttribute("room", myPageService.findByUserRoomId(chatRoomId, principal));

        return "chat/room";
    }

}
