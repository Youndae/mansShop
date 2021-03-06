package org.shop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.shop.domain.*;
import org.shop.mapper.ProductMapper;
import org.shop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;
import java.util.Random;

@RequestMapping("/product")
@Controller
@Log4j
@AllArgsConstructor
public class ProductController {

    private ProductMapper productMapper;

    private ProductService productService;


    @GetMapping("/{pno}")
    public String productDetail(@PathVariable("pno") String pno, Model model, Criteria cri, Principal principal){
        //상품 상세페이지
        log.info("detail pno : " + pno);

        cri.setKeyword(pno);

        model.addAttribute("pDetail", productMapper.productDetail(pno));

        model.addAttribute("pReviewCount", productMapper.getProductReviewTotal(pno));

        model.addAttribute("pQnACount", productMapper.getProductQnATotal(pno));

        if(principal == null){
            model.addAttribute("likeStat", 2);
        }else{
            model.addAttribute("likeStat", productMapper.getLikeProductStat(pno, principal.getName()));
        }

        return "/product/productDetail";

    }

    @GetMapping("/getProductThumb")
    @ResponseBody
    public ResponseEntity<List<ThumbnailVO>> getThumb(String pno){
        log.info("getProductThumb");

        return new ResponseEntity<>(productMapper.getProductThumb(pno), HttpStatus.OK);
    }

    @GetMapping("/getProductOp")
    @ResponseBody
    public ResponseEntity<List<ProductOpVO>> getProductOp(String pno){
        log.info("getProductOp");

        return new ResponseEntity<>(productMapper.getProductOp(pno), HttpStatus.OK);
    }

    @GetMapping("/getProductInfo")
    @ResponseBody
    public ResponseEntity<List<ProductImgVO>> getProductInfo(String pno){
        log.info("getProductInfo");

        return new ResponseEntity<>(productMapper.getProductInfo(pno), HttpStatus.OK);
    }

    @GetMapping(value = "/productQnAPages/{pno}/{page}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<ProductQnADTO> getProductQnA(@PathVariable("page") int page, @PathVariable("pno") String pno){
        log.info("getProductQnA");

        Criteria cri = new Criteria(page, 10);

        log.info("Review pno : " + pno);

        log.info("pno : " + pno + " cri : " + cri.getPageNum());

        return new ResponseEntity<>(productService.getProductQnA(cri, pno), HttpStatus.OK);
    }

    @GetMapping(value = "/reviewPages/{pno}/{page}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<ProductReviewDTO> getProductReview(@PathVariable("page") int page, @PathVariable("pno") String pno){
        log.info("getProductReview");

        Criteria cri = new Criteria(page, 10);

        log.info("Review pno : " + pno);

        log.info("pno : " + pno + " cri : " + cri.getPageNum());

        return new ResponseEntity<>(productService.getProductReview(cri, pno), HttpStatus.OK);
    }

    @PostMapping("/addCart")
    @ResponseBody
    public int addCart(@RequestParam("pOpNo") List<String> pOpNo,
                       @RequestParam("pCount") List<String> pCount,
                       @RequestParam("pPrice") List<String> pPrice,
                       CartVO cartVO, Principal principal, CartDetailVO cartDetailVO,
                       HttpServletRequest request, HttpServletResponse response){
        //장바구니 추가
        log.info("addCart");

        log.info("pPrice : " + pPrice);

        Cookie cookie = WebUtils.getCookie(request, "cartCookie");

        if(principal == null && cookie == null){ // 회원이 아니고 쿠키가 없는 경우
            //쿠키 생성
            String ckId = RandomStringUtils.random(8, true, true);
            Cookie cartCookie = new Cookie("cartCookie", ckId);
            cartCookie.setPath("/");
            cartCookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(cartCookie);

            //VO에 쿠키 값 set
            cartVO.setCkId(ckId);
            cartVO.setUserId("Anonymous");

            return productService.addCart(pOpNo, pCount, pPrice, cartVO, cartDetailVO);
        }else if(principal == null && cookie != null){ //회원이 아니고 쿠키가 존재하는 경우
            String ckId = cookie.getValue();
            //쿠키 유효기간을 다시 증가.
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(cookie);

            //VO에 쿠키값 set
            cartVO.setCkId(ckId);
            cartVO.setUserId("Anonymous");

            return productService.addCart(pOpNo, pCount, pPrice, cartVO, cartDetailVO);
        }else{ //회원인 경우
            //회원 장바구니 처리
            cartVO.setUserId(principal.getName());

            return productService.addCart(pOpNo, pCount, pPrice, cartVO, cartDetailVO);
        }
    }

    @PostMapping("/QnAInsert")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public void QnAInsert(ProductQnAVO productQnAVO, Principal principal){

        //상품 문의 작성

        log.info("pQnAContent: " + productQnAVO.getPQnAContent());
        log.info("pno : " + productQnAVO.getPno());


        productQnAVO.setUserId(principal.getName());

        log.info("vo : "  + productQnAVO);

        productMapper.insertPQnA(productQnAVO);

    }

    @PostMapping("/likeProduct")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public void likeProduct(LikeVO likeVO, Principal principal){
        //찜목록에 추가
        log.info("like product");

        likeVO.setUserId(principal.getName());

        String likeNo = likeVO.getUserId()+likeVO.getPno();

        likeVO.setLikeNo(likeNo);

        log.info("like data : " + likeVO);

        productMapper.likeProduct(likeVO);

    }

    @PostMapping("/deLikeProduct")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public void deLikeProduct(LikeVO likeVO, Principal principal){
        //찜목록에서 삭제
        log.info("deLikeProduct");

        String id = principal.getName();

        likeVO.setUserId(id);

        productMapper.deLikeProduct(likeVO);
    }


}
