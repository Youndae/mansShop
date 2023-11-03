package org.shop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.ResultProperties;
import org.shop.domain.dto.admin.ProductInfoImageDTO;
import org.shop.domain.dto.admin.ProductThumbnailDTO;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.dto.product.*;
import org.shop.domain.entity.*;
import org.shop.mapper.AdminMapper;
import org.shop.mapper.ProductMapper;
import org.shop.service.CookieService;
import org.shop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RequestMapping("/product")
@Controller
@Log4j
@AllArgsConstructor
public class ProductController {

    private ProductMapper productMapper;

    private ProductService productService;

    private AdminMapper adminMapper;

    //상품 상세 페이지
    @GetMapping("/{pno}")
    public String productDetail(@PathVariable("pno") String pno, Model model, Criteria cri, Principal principal){
        log.info("detail pno : " + pno);

        cri.setKeyword(pno);

        model.addAttribute("pDetail", productMapper.productDetail(pno));

        model.addAttribute("pReviewCount", productMapper.getProductReviewTotal(pno));

        model.addAttribute("pQnACount", productMapper.getProductQnATotal(pno));

        model.addAttribute("likeStat", principal == null ? 2 : productMapper.getLikeProductStat(pno, principal.getName()));

        return "/product/productDetail";

    }

    //상품 썸네일(대표 썸네일이 아닌) 리스트
    @GetMapping("/getProductThumb")
    @ResponseBody
    public ResponseEntity<List<ProductThumbnailDTO>> getThumb(String pno){
        log.info("getProductThumb");

        return new ResponseEntity<>(adminMapper.getThumbnail(pno), HttpStatus.OK);
    }

    //상품 옵션 리스트(selectBox 데이터)
    @GetMapping("/getProductOp")
    @ResponseBody
    public ResponseEntity<List<ProductOpDTO>> getProductOp(String pno){
        log.info("getProductOp");

        return new ResponseEntity<>(productMapper.getProductOp(pno), HttpStatus.OK);
    }

    //상품 정보 이미지 리스트
    @GetMapping("/getProductInfo")
    @ResponseBody
    public ResponseEntity<List<ProductInfoImageDTO>> getProductInfo(String pno){
        log.info("getProductInfo : " + pno);

        return new ResponseEntity<>(adminMapper.getInfoImg(pno), HttpStatus.OK);
    }

    //상품문의 데이터 리스트
    @GetMapping(value = "/productQnAPages/{pno}/{page}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<ProductQnADTO> getProductQnA(@PathVariable("page") int page, @PathVariable("pno") String pno){
        log.info("getProductQnA");

        Criteria cri = new Criteria(page);

        return new ResponseEntity<>(productService.getProductQnA(cri, pno), HttpStatus.OK);
    }

    //상품 리뷰 데이터 리스트
    @GetMapping(value = "/reviewPages/{pno}/{page}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<ProductReviewDTO> getProductReview(@PathVariable("page") int page, @PathVariable("pno") String pno){
        log.info("getProductReview");

        Criteria cri = new Criteria(page);

        return new ResponseEntity<>(productService.getProductReview(cri, pno), HttpStatus.OK);
    }

    //장바구니 추가 처리
    @PostMapping("/addCart")
    @ResponseBody
    public String addCart(@RequestParam("pOpNo") List<String> pOpNo
            , @RequestParam("pCount") List<String> pCount
            , @RequestParam("pPrice") List<String> pPrice
            , Principal principal
            , HttpServletRequest request
            , HttpServletResponse response){
        log.info("addCart");

        int result;

        try{
            result = productService.addCart(pOpNo, pCount, pPrice, principal, request, response);
        }catch (Exception e){
            result = ResultProperties.ERROR;
        }

        return String.valueOf(result);
    }

    //상품 문의 작성 처리
    @PostMapping("/QnAInsert")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String qnAInsert(ProductQnAInsertDTO dto, Principal principal){

        int result;

        try{
            result = productService.qnAInsertProc(dto, principal);
        }catch (Exception e){
            result = ResultProperties.ERROR;
        }

        return String.valueOf(result);

    }

    //상품 찜 목록에 추가
    @PostMapping("/likeProduct")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String likeProduct(@RequestParam("pno") String pno, Principal principal){
        //찜목록에 추가
        log.info("like product pno : "  + pno);

        int result;

        try{
            result = productService.likeProduct(pno, principal);
        }catch (Exception e){
            result = ResultProperties.ERROR;
        }

        return String.valueOf(result);
    }

    //상품 찜 목록에서 삭제
    @DeleteMapping("/deLikeProduct")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String deLikeProduct(@RequestBody Map<String, String> pno, Principal principal){
        log.info("deLikeProduct : " + pno);

        int result;

        try{
            result = productService.deLikeProduct(pno.get("pno"), principal);
        }catch (Exception e){
            result = ResultProperties.ERROR;
        }

        return String.valueOf(result);
    }


}
