package org.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.dto.product.out.ProductDetailDTO;
import org.shop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/product")
@Controller
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //상품 상세 페이지
    @GetMapping("/{productId}")
    public String productDetail(@PathVariable("productId") String productId, Model model, Principal principal){

        ProductDetailDTO dto = productService.productDetail(productId, principal);

        model.addAttribute("product", dto);

        return "/product/productDetail";
    }

}
