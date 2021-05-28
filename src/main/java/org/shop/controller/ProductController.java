package org.shop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.mapper.ProductMapper;
import org.shop.service.ProductService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/product")
@RestController
@Log4j
@AllArgsConstructor
public class ProductController {

    //private ProductMapper productMapper;

    //private ProductService productService;

    @GetMapping("/menu")
    public void productMenu(Model model, String pClassification){
        //상품 메뉴
        //페이징들어가야하고
        //검색 들어가야함
        //pClassification으로 받은 상품 분류를 이용해
        //같은 페이지에서 결과값만 다르게 출력하도록 작성.
    }

    @GetMapping("/productDetail/{pno}")
    public void productDetail(String pno, Model model){
        //상품 상세 페이지
        //썸네일리스트와 상품정보 이미지 출력필요.
    }

    @GetMapping("/productReview/{pno}")
    public void productReviewList(Model model, String pno){
        //상품 리뷰리스트.
    }

    @GetMapping("/productQnAList/{pno}")
    public void productQnAList(Model model, String pno){
        //상품 QnA리스트.
    }

    @GetMapping("/productQnADetail/{pQnANo}")
    public void productQnA(Model model, Long pQnANo){
        //상품 QnA 내용 출력.
    }

    @GetMapping("/insertProductQnA")
    public void ProductQnA(){
        //productQnA작성 페이지
    }

    @PostMapping("/insertProductQnA")
    public void insertProductQnA(){
        //productQnA insert처리.
    }

    @GetMapping("/productOrderInfo")
    public void productOrderInfo(){
        //상품 주문정보
        //판매자 정보나 고객센터 번호, 배송정보등 있는 페이지.
    }

    @GetMapping("/modifyProductReview")
    public void productReview(){
        //리뷰 수정 페이지
    }

    @PostMapping("/modifyProductReview")
    public void modifyProductReview(){
        //리뷰 수정 처리.
    }

    @DeleteMapping("/deleteProductReview")
    public void deleteProductReview(){
        //리뷰 삭제
    }

    @PostMapping("/like")
    public void likeProduct(){
        //상품 찜 처리.
    }

    @PostMapping("/addCart")
    public void addCart(){
        //장바구니처리
    }

    @DeleteMapping("/deleteCart")
    public void deleteCart(){
        //장바구니에서 삭제
    }


}
