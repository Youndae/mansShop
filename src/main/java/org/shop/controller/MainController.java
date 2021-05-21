package org.shop.controller;

import lombok.extern.log4j.Log4j;
import org.shop.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/main")
@RestController
@Log4j
public class MainController {

    @Autowired(required = false)
    private MainMapper mainMapper;

    @GetMapping("/")
    public void main(Model model){
        //메인페이지
        //BEST상품 12개, NEW 상품 12개 출력.

    }


}
