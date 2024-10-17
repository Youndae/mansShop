package org.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.dto.cart.business.OptionAndCountListDTO;
import org.shop.domain.dto.cart.business.CartMemberDTO;
import org.shop.domain.dto.paging.PagingResponseDTO;
import org.shop.domain.dto.product.business.ProductQnAListDTO;
import org.shop.domain.dto.product.business.ProductReviewListDTO;
import org.shop.domain.dto.product.in.ProductQnARequestDTO;
import org.shop.service.CartService;
import org.shop.service.CookieService;
import org.shop.service.PrincipalService;
import org.shop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class ProductRestController {

    private final CookieService cookieService;

    private final CartService cartService;

    private final ProductService productService;

    private final PrincipalService principalService;

    @PostMapping("/cart")
    public ResponseEntity<String> addCart(@RequestBody OptionAndCountListDTO optionAndCountListDTO
                                    , Principal principal
                                    , HttpServletRequest request
                                    , HttpServletResponse response){
        CartMemberDTO cartMemberDTO = cookieService.createCartMemberDTOToAddCart(request, principal, response);

        String result = cartService.addCart(optionAndCountListDTO, cartMemberDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/qna")
    public ResponseEntity<String> insertProductQnA(@RequestBody ProductQnARequestDTO dto
                                                    , Principal principal) {
        dto.setUserId(principalService.getUserIdToPrincipal(principal));
        String result = productService.insertQnA(dto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //상품 찜 목록에 추가
    @PostMapping("/like/{pno}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String likeProduct(@PathVariable String pno, Principal principal){

        return productService.likeProduct(pno, principal);
    }

    //상품 찜 목록에서 삭제
    @DeleteMapping("/like/{pno}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String deLikeProduct(@PathVariable String pno, Principal principal){

        return productService.deLikeProduct(pno, principal);
    }

    //상품문의 데이터 리스트
    @GetMapping(value = "/qna/{productId}/{page}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<PagingResponseDTO<ProductQnAListDTO>> getProductQnA(@PathVariable("page") int page, @PathVariable("productId") String productId){

        return new ResponseEntity<>(productService.getDetailProductQnA(productId, page), HttpStatus.OK);
    }

    //상품 리뷰 데이터 리스트
    @GetMapping(value = "/review/{productId}/{page}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<PagingResponseDTO<ProductReviewListDTO>> getProductReview(@PathVariable("page") int page, @PathVariable("productId") String productId){

        return new ResponseEntity<>(productService.getDetailReview(productId, page), HttpStatus.OK);
    }
}
