package org.shop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.mapper.AdminMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/")
@Controller
@Log4j
@AllArgsConstructor

public class AdminController {

    private AdminMapper adminMapper;

    //private AdminService adminService;

    @GetMapping("/productList")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void productList(Model model) throws Exception{
        //상품 목록
        log.info("pList");

        adminMapper.pList().forEach(product -> log.info(product));

        model.addAttribute("pList", adminMapper.pList());
    }

    @GetMapping("/addProduct")
    public void addProduct(){
        //상품 추가
    }

    @GetMapping("/productInfo")
    public void productInfo(){
        //상품 정보
    }

    @PostMapping("/modifyProductInfo")
    public void modifyProductInfo(){
        //상품 정보 수정
    }

    @GetMapping("/orderList")
    public void orderList(){
        //주문 목록
        //모든 주문 목록 출력
    }

    @PostMapping("/shippingProcess")
    public void shippingProcess(){
        //배송처리
    }

    @PostMapping("/orderPacking")
    public void orderPacking(){
        //상품 포장 처리
        //이건 shippingProcess랑 합쳐서 할지 따로 할지 좀 더 고민.
    }

    @GetMapping("/orderDetail")
    public void orderDetail(){
        //주문 상세 정보
    }

    @GetMapping("/adminQnAList")
    public void adminQnAList(){
        //QnA 리스트.
        //상품 QnA가 아닌 회원 문의 리스트트
    }

    @GetMapping("/adminQnAList/{qno}")
    public void adminQnADetail(){
        //QnA Detail
    }

    @PostMapping("/adminQnAComplete")
    public void adminQnAComplete(){
        //QnA 답변 완료 처리
    }

    @PostMapping("/adminQnAReply")
    public void adminQnAReply(){
        // QnA 댓글 처리
        // MyPageController에도 동일하게 있는데
        // 거기서 한번에 처리하도록 할지 admin과 user를 나눠서 처리할지 고민.
    }

    @GetMapping("/userInfo")
    public void userInfo(){
        //회원 정보
    }

    @GetMapping("/userList")
    public void userList(){
        //회원 목록
    }

    @GetMapping("/salesProductList")
    public void salesProductList(){
        //상품별 매출 목록
    }

    @GetMapping("/salesTermList")
    public void salesTermList(){
        //기간별 매출 목록
    }

    @GetMapping("/replyProductQnA")
    public void getReplyProductQnA(){
        //상품 문의 답글 작성 페이지
        //admin한테만 답글 작성 권한이 있으므로 여기서 처리
    }

    @PostMapping("/replyProductQnA")
    public void replyProductQnA(){
        //상품 문의 답글 작성 처리
    }


}
