package org.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.FileProperties;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.dto.myPage.MemberOrderListDTO;
import org.shop.domain.dto.main.NonProductOrderDTO;
import org.shop.domain.dto.paging.PageDTO;
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
@RequiredArgsConstructor
public class MainController {


    private final MainMapper mainMapper;

    //메인페이지. BEST 상품 출력
    @GetMapping("/")
    public String main(Model model){
        model.addAttribute("productClassification", "BEST");
        model.addAttribute("pList", mainMapper.mainBest());

        return"main/main";
    }

    //new 상품 리스트
    @GetMapping("/new")
    public String newProduct(Model model){
        model.addAttribute("productClassification", "NEW");
        model.addAttribute("pList", mainMapper.mainNew());

        return "main/main";
    }

    //분류별 상품 리스트
    @GetMapping("/{classification}")
    public String productClassification(@PathVariable("classification") String classification, Model model, Criteria cri){
        cri.setKeyword(classification);
        cri.setAmount(12);

        model.addAttribute("productClassification", classification);
        model.addAttribute("pList", mainMapper.mainClassification(cri));
        int total = mainMapper.getProductTotal(cri);
        model.addAttribute("pageMaker", new PageDTO(cri, total));


        return "main/main";
    }

    //검색 상품 리스트
    @GetMapping("/search")
    public String searchProduct(Model model, Criteria cri){
        //상품 검색
        model.addAttribute("pList", mainMapper.searchProduct(cri));
        int total = mainMapper.getSearchProduct(cri);
        model.addAttribute("pageMaker", new PageDTO(cri, total));

        return "main/main";
    }

    //이미지 데이터
    @GetMapping("/display")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(String image){
        File file = new File(FileProperties.FILE_PATH + image);
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
    @GetMapping("/non/order")
    public String nonOrderList(NonProductOrderDTO dto, Model model){

        model.addAttribute("oList", dto);

        return "/main/nonOrderList";
    }

    //비회원 주문 목록 데이터 리스트
    @GetMapping("/non/order/data")
    @ResponseBody
    public ResponseEntity<List<MemberOrderListDTO>> getNonOrderList(String recipient, String orderPhone){

        return new ResponseEntity<>(mainMapper.getNonOrderList(recipient, orderPhone),HttpStatus.OK);
    }

    //오류 페이지
    @GetMapping("/error")
    public String accessDenied(Model model){
        model.addAttribute("msg", "Access Denied");

        return "/accessError";
    }

}
