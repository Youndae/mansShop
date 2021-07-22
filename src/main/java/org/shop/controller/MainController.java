package org.shop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.*;
import org.shop.mapper.MainMapper;
import org.shop.service.MainService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Controller
@Log4j
@PreAuthorize("permitAll()")
@AllArgsConstructor
public class MainController {


    private MainMapper mainMapper;

    private MainService mainService;

    @GetMapping("/")
    public String main(Model model, Principal principal){
        //메인페이지
        //BEST상품 12개, NEW 상품 12개 출력.

        log.info("main page");

        model.addAttribute("pList", mainMapper.mainBest());

        return"main/main";
    }

    @GetMapping("/display")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(String image){
        log.info("main thumbnail : " + image);

        File file = new File("E:\\upload\\Product\\" + image);

        log.info("file : " + file);

        ResponseEntity<byte[]> result = null;

        try{
            HttpHeaders header = new HttpHeaders();

            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/{pno}")
    public String productDetail(@PathVariable("pno") String pno, Model model, Criteria cri, Principal principal){
        log.info("detail pno : " + pno);

        cri.setKeyword(pno);

        model.addAttribute("pDetail", mainMapper.productDetail(pno));

        model.addAttribute("pReviewCount", mainMapper.getProductReviewTotal(pno));

        model.addAttribute("pQnACount", mainMapper.getProductQnATotal(pno));

        if(principal == null){
            model.addAttribute("likeStat", 2);
        }else{
            model.addAttribute("likeStat", mainMapper.getLikeProductStat(pno, principal.getName()));
        }

        /*model.addAttribute("pReviewList", mainMapper.getProductReview(cri));*/

        /*model.addAttribute("pQnA", mainMapper.getProductQnA(pno));*/

        /*int total = mainMapper.getProductReviewTotal(cri);*/
        /*log.info("Product Total : " + total);

        model.addAttribute("pageMaker", new PageDTO(cri, total));*/

        return "main/productDetail";


        //출력해야할 정보
        /*
            대표썸네일 + 썸네일 리스트
            옵션(컬러, 사이즈) select box
            가격
            select box를 선택햇을 때 해당 정보.(주문정보)

            상세정보, 리뷰

            product테이블 모든 컬럼만 먼저 갖고 오고
            getJSON을 사용해서 썸네일리스트를 대표썸네일 아래에 깔아주고
            가격은 detail로 받으면되고
            select box도 getJSON으로.
            상세정보도 어차피 다 이미지니까 다 그대로 갖고 오고
            리뷰는 눌렀을때 json으로 처리해줘야 하니까 그렇게 처리하면 되고.

        */
    }

    @GetMapping("/getProductThumb")
    @ResponseBody
    public ResponseEntity<List<ThumbnailVO>> getThumb(String pno){
        log.info("getProductThumb");

        return new ResponseEntity<>(mainMapper.getProductThumb(pno), HttpStatus.OK);
    }

    @GetMapping("/getProductOp")
    @ResponseBody
    public ResponseEntity<List<ProductOpVO>> getProductOp(String pno){
        log.info("getProductOp");

        return new ResponseEntity<>(mainMapper.getProductOp(pno), HttpStatus.OK);
    }

    @GetMapping("/getProductInfo")
    @ResponseBody
    public ResponseEntity<List<ProductImgVO>> getProductInfo(String pno){
        log.info("getProductInfo");

        return new ResponseEntity<>(mainMapper.getProductInfo(pno), HttpStatus.OK);
    }

    @GetMapping(value = "/productQnAPages/{pno}/{page}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<ProductQnADTO> getProductQnA(@PathVariable("page") int page, @PathVariable("pno") String pno){
        log.info("getProductQnA");

        Criteria cri = new Criteria(page, 10);

        log.info("Review pno : " + pno);

        log.info("pno : " + pno + " cri : " + cri.getPageNum());

        return new ResponseEntity<>(mainService.getProductQnA(cri, pno), HttpStatus.OK);
    }

    @GetMapping(value = "/reviewPages/{pno}/{page}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<ProductReviewDTO> getProductReview(@PathVariable("page") int page, @PathVariable("pno") String pno){
        log.info("getProductReview");

        Criteria cri = new Criteria(page, 10);

        log.info("Review pno : " + pno);

        log.info("pno : " + pno + " cri : " + cri.getPageNum());

        return new ResponseEntity<>(mainService.getProductReview(cri, pno), HttpStatus.OK);
    }

    @PostMapping("/QnAInsert")
    @ResponseBody
    public void QnAInsert(ProductQnAVO productQnAVO, Principal principal){

        //pQnANo 시퀀스 만들어야함.

        log.info("pQnAContent: " + productQnAVO.getPQnAContent());
        log.info("pno : " + productQnAVO.getPno());


        productQnAVO.setUserId(principal.getName());

        log.info("vo : "  + productQnAVO);



        mainMapper.insertPQnA(productQnAVO);

    }

   /* @PostMapping("/test")
    public void test(@RequestParam HashMap<String, Object> commandMap) throws Exception{




        String[] no_array = null;

        String pOpNo = commandMap.get("pOpNo").toString();
        no_array = pOpNo.split(",");

        log.info("pOpNo : " + pOpNo);
        log.info("no_array0 : " + no_array[0]);
        log.info("no_array1 : " + no_array[1]);
        log.info("no_array2 : " + no_array[2]);



        String[] name_array = null;

        String pName = commandMap.get("pName").toString();
        name_array = pName.split(",");



        String[] size_array = null;

        String pSize = commandMap.get("pSize").toString();
        size_array = pSize.split(",");



        String[] color_array = null;

        String pColor = commandMap.get("pColor").toString();
        color_array = pColor.split(",");



        String[] count_array = null;

        String cCount = commandMap.get("cCount").toString();
        count_array = cCount.split(",");



        String[] price_array = null;

        String cPrice = commandMap.get("cPrice").toString();
        price_array = cPrice.split(",");

        List<CartVO> vo = new ArrayList<>();

        for(int i = 0; i < no_array.length; i++){

            CartVO cartVO = new CartVO();

            log.info("for i : " + i);

            cartVO.setPOpNo(no_array[i]);
            log.info("arr_pOpNo : " + no_array[i]);
            log.info("vo pOpNo : " + cartVO.getPOpNo());

            cartVO.setPName(name_array[i]);
            log.info("arr_pName : " + name_array[i]);
            log.info("vo pName : " + cartVO.getPName());

            cartVO.setPSize(size_array[i]);
            log.info("arr_pSize : " + size_array[i]);
            log.info("vo pSize : " + cartVO.getPSize());

            cartVO.setPColor(color_array[i]);
            log.info("arr_pColor : " + color_array[i]);
            log.info("vo pColor : " + cartVO.getPColor());

            cartVO.setCCount(Long.parseLong(count_array[i]));
            log.info("arr_cCount : " + count_array[i]);
            log.info("vo cCount : " + cartVO.getCCount());

            cartVO.setCPrice(Long.parseLong(price_array[i]));
            log.info("arr_cPrice : " + price_array[i]);
            log.info("vo cPrice : " + cartVO.getCPrice());

            vo.add(cartVO);
            log.info("for cartVo : " + vo);
        }

        log.info("Last VO : " + vo);

        *//*HashMap<String, Object> resendMap = new HashMap<String, Object>();

        for(int i = 0; i < no_array.length; i++){
            resendMap.put("pOpNo", no_array[i]);
            resendMap.put("pName", name_array[i]);
            resendMap.put("pSize", size_array[i]);
            resendMap.put("pColor", color_array[i]);
            resendMap.put("cCount", count_array[i]);
            resendMap.put("cPrice", price_array[i]);
        }

        log.info("resendMap pOpNo : " + resendMap.get("pOpNo").toString());
        log.info("resendMap pName : " + resendMap.get("pName").toString());
        log.info("resendMap pSize : " + resendMap.get("pSize").toString());
        log.info("resendMap pColor : " + resendMap.get("pColor").toString());
        log.info("resendMap cCount : " + resendMap.get("cCount").toString());
        log.info("resendMap cPrice : " + resendMap.get("cPrice").toString());*//*

    }*/

    @PostMapping("/likeProduct")
    @ResponseBody
    public void likeProduct(LikeVO likeVO, Principal principal){
        log.info("like product");

        String id = principal.getName();

        likeVO.setUserId(id);

        String likeNo = likeVO.getUserId()+likeVO.getPno();

        likeVO.setLikeNo(likeNo);

        log.info("like data : " + likeVO);

        mainMapper.likeProduct(likeVO);

    }

    @PostMapping("/deLikeProduct")
    @ResponseBody
    public void deLikeProduct(LikeVO likeVO, Principal principal){
        log.info("deLikeProduct");

        String id = principal.getName();

        likeVO.setUserId(id);

        mainMapper.deLikeProduct(likeVO);
    }

    @PostMapping("/addCart")
    @ResponseBody
    public int addCart(@RequestParam("pOpNo") List<String> pOpNo,
                       @RequestParam("pCount") List<String> pCount,
                       @RequestParam("pPrice") List<String> pPrice,
                       CartVO cartVO, Principal principal){
        log.info("addCart");

        log.info("pPrice : " + pPrice);

        cartVO.setUserId(principal.getName());

        return mainService.addCart(pOpNo, pCount, pPrice, cartVO);
    }

}
