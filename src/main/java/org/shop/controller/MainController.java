package org.shop.controller;

import lombok.extern.log4j.Log4j;
import org.shop.domain.Criteria;
import org.shop.domain.ProductOpVO;
import org.shop.domain.ThumbnailVO;
import org.shop.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


@Controller
@Log4j
@PreAuthorize("permitAll()")
public class MainController {

    @Autowired(required = false)
    private MainMapper mainMapper;

    @GetMapping("/")
    public String main(Model model){
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
    public String productDetail(@PathVariable("pno") String pno, Model model){
        log.info("detail pno : " + pno);

        model.addAttribute("pDetail", mainMapper.productDetail(pno));

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


}
