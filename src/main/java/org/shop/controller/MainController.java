package org.shop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.Criteria;
import org.shop.domain.MemberOrderListDTO;
import org.shop.domain.OrderVO;
import org.shop.domain.PageDTO;
import org.shop.mapper.MainMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/")
    public String main(Model model, Criteria cri){
        //메인페이지
        //BEST상품 12개, NEW 상품 12개 출력.

        log.info("main page");

        log.info("keyword : " + cri.getKeyword());

        model.addAttribute("productClassification", "BEST");

        model.addAttribute("pList", mainMapper.mainBest());

        return"main/main";
    }

    @GetMapping("/new")
    public String newProduct(Model model){
        //새로운 상품
        log.info("new");

        model.addAttribute("productClassification", "NEW");

        model.addAttribute("pList", mainMapper.mainNew());

        return "main/main";
    }

    @GetMapping("/{classification}")
    public String productClassification(@PathVariable("classification") String classification, Model model, Criteria cri){
        //상품 분류별
        log.info("classification : " + classification);

        cri.setKeyword(classification);
        cri.setAmount(12);

        model.addAttribute("productClassification", cri.getKeyword());

        model.addAttribute("pList", mainMapper.mainClassification(cri));

        log.info(mainMapper.mainClassification(cri));

        int total = mainMapper.getProductTotal(cri);

        model.addAttribute("pageMaker", new PageDTO(cri, total));


        return "main/main";
    }

    @GetMapping("/searchProduct")
    public String searchProduct(Model model, Criteria cri){
        //상품 검색
        log.info("searchProduct");

        model.addAttribute("pList", mainMapper.searchProduct(cri));

        int total = mainMapper.getSearchProduct(cri);

        model.addAttribute("pageMaker", new PageDTO(cri, total));

        return "main/main";
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

    @GetMapping("/nonOrderList")
    public String nonOrderList(OrderVO orderVO, Model model){
        //비회원 주문목록
        log.info("nonOrderList : " + orderVO);

        orderVO.setUserId("Anonymous");

        model.addAttribute("oList", orderVO);

        return "/main/nonOrderList";
    }

    @GetMapping("/getNonOrderList")
    @ResponseBody
    public ResponseEntity<List<MemberOrderListDTO>> getNonOrderList(String recipient, String orderPhone){

        log.info("getNonOrderList recipient : " + recipient + ", orderPhone : " + orderPhone);

        return new ResponseEntity<>(mainMapper.getNonOrderList(recipient, orderPhone),HttpStatus.OK);
    }

}
