package org.shop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.FileProperties;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.dto.myPage.MemberOrderListDTO;
import org.shop.domain.dto.main.NonProductOrderDTO;
import org.shop.domain.dto.paging.PageDTO;
import org.shop.domain.entity.Product;
import org.shop.mapper.MainMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


@Controller
@Log4j
@PreAuthorize("permitAll()")
@AllArgsConstructor
public class MainController {


    private MainMapper mainMapper;

    //메인페이지. BEST 상품 출력
    @GetMapping("/")
    public String main(Model model, Criteria cri){

        log.info("main page");

        log.info("keyword : " + cri.getKeyword());

        model.addAttribute("productClassification", "BEST");

        model.addAttribute("pList", mainMapper.mainBest());

        return"main/main";
    }

    //new 상품 리스트
    @GetMapping("/new")
    public String newProduct(Model model){
        //새로운 상품
        log.info("new");

        model.addAttribute("productClassification", "NEW");

        model.addAttribute("pList", mainMapper.mainNew());

        return "main/main";
    }

    //분류별 상품 리스트
    @GetMapping("/{classification}")
    public String productClassification(@PathVariable("classification") String classification, Model model, Criteria cri){
        log.info("classification : " + classification);

        cri.setKeyword(classification);
        cri.setAmount(12);

        model.addAttribute("productClassification", classification);

        model.addAttribute("pList", mainMapper.mainClassification(cri));

        int total = mainMapper.getProductTotal(cri);

        model.addAttribute("pageMaker", new PageDTO(cri, total));


        return "main/main";
    }

    //검색 상품 리스트
    @GetMapping("/searchProduct")
    public String searchProduct(Model model, Criteria cri){
        //상품 검색
        log.info("searchProduct");

        model.addAttribute("pList", mainMapper.searchProduct(cri));

        int total = mainMapper.getSearchProduct(cri);

        model.addAttribute("pageMaker", new PageDTO(cri, total));

        return "main/main";
    }

    //이미지 데이터
    @GetMapping("/display")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(String image){
        log.info("main thumbnail : " + image);

        File file = new File(FileProperties.FILE_PATH + image);

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

    //비회원 주문 목록 페이지
    @GetMapping("/nonOrderList")
    public String nonOrderList(NonProductOrderDTO dto, Model model){

        model.addAttribute("oList", dto);

        return "/main/nonOrderList";
    }

    //비회원 주문 목록 데이터 리스트
    @GetMapping("/getNonOrderList")
    @ResponseBody
    public ResponseEntity<List<MemberOrderListDTO>> getNonOrderList(String recipient, String orderPhone){

        log.info("getNonOrderList recipient : " + recipient + ", orderPhone : " + orderPhone);

        return new ResponseEntity<>(mainMapper.getNonOrderList(recipient, orderPhone),HttpStatus.OK);
    }

    //오류 페이지
    @GetMapping("/accessError")
    public String accessDenied(Authentication auth, Model model){
        log.info("access Denied : " + auth);

        model.addAttribute("msg", "Access Denied");

        return "/accessError";
    }

}
