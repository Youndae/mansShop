package org.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.dto.main.out.ProductPageableDTO;
import org.shop.domain.dto.main.out.MainListDTO;
import org.shop.domain.dto.order.OrderListRequestDTO;
import org.shop.domain.dto.order.out.OrderListDTO;
import org.shop.domain.dto.order.out.OrderListResponseDTO;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.dto.order.in.ProductOrderListDTO;
import org.shop.domain.dto.paging.MainPageCriteria;
import org.shop.domain.dto.paging.PageDTO;
import org.shop.domain.mapper.PageableRequestMapper;
import org.shop.service.MainService;
import org.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@Controller
@Slf4j
@PreAuthorize("permitAll()")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    private final OrderService orderService;

    @Value("#{filePath['file.path']}")
    private String filePath;

    //메인페이지. BEST 상품 출력
    @GetMapping("/")
    public String main(Model model){
        model.addAttribute("productClassification", "BEST");
        model.addAttribute("pList", mainService.getMainBestAndNewList("BEST"));
        model.addAttribute("pageMaker", new PageDTO<>(new Criteria(), 0));

        return"main/main";
    }

    //new 상품 리스트
    @GetMapping("/new")
    public String newProduct(Model model){
        model.addAttribute("productClassification", "NEW");
        model.addAttribute("pList", mainService.getMainBestAndNewList("NEW"));
        model.addAttribute("pageMaker", new PageDTO<>(new Criteria(), 0));

        return "main/main";
    }

    //분류별 상품 리스트
    @GetMapping("/category/{classification}")
    public String productClassification(@PathVariable("classification") String classification
                                        , Model model
                                        , @RequestParam(name = "page", required = false, defaultValue = "1") int page){
        MainPageCriteria cri = PageableRequestMapper.createMainPageCriteria(page, null, classification);
        ProductPageableDTO<MainListDTO> dto = mainService.getMainClassificationList(cri);

        model.addAttribute("productClassification", classification);
        model.addAttribute("pList", dto.getContent());
        model.addAttribute("pageMaker", new PageDTO<>(cri, dto.getTotalElements()));

        return "main/main";
    }

    //검색 상품 리스트
    @GetMapping("/search")
    public String searchProduct(Model model
                                , @RequestParam("page") int page
                                , @RequestParam("keyword") String keyword){
        MainPageCriteria cri = PageableRequestMapper.createMainPageCriteria(page, keyword, null);
        ProductPageableDTO<MainListDTO> dto = mainService.getMainSearchList(cri);
        //상품 검색
        model.addAttribute("pList", dto.getContent());
        model.addAttribute("pageMaker", new PageDTO<>(cri, dto.getTotalElements()));

        return "main/main";
    }

    //이미지 데이터
    @GetMapping("/display")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(String image){
        File file = new File(filePath + image);
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

    @GetMapping("/non/check")
    public String anonymousOrderCheck() {

        return "/main/orderInfoCheck";
    }

    //비회원 주문 목록 페이지
    @GetMapping("/non/order")
    public String nonOrderList(ProductOrderListDTO dto
                                , @RequestParam(name = "term", required = false, defaultValue = "3") String term
                                , @RequestParam(name = "page", required = false, defaultValue = "1") int page
                                , Model model){

        OrderListRequestDTO orderDTO = new OrderListRequestDTO(term, page);
        OrderListResponseDTO<OrderListDTO> result = orderService.getAnonymousOrderList(dto, orderDTO);

        model.addAttribute("list", result);

        return "/main/nonOrderList";
    }

    //오류 페이지
    @GetMapping("/error")
    public String accessDenied(Model model){
        model.addAttribute("msg", "Access Denied");

        return "/accessError";
    }

}
