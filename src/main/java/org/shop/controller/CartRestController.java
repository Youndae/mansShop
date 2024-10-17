package org.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.dto.cart.business.CartMemberDTO;
import org.shop.service.CartService;
import org.shop.service.CookieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartRestController {

    private final CookieService cookieService;

    private final CartService cartService;

    @DeleteMapping("/")
    public ResponseEntity<String> deleteSelectCartDetail(@RequestBody List<Long> deleteCartDetailIds
                                                    , Principal principal
                                                    , HttpServletRequest request
                                                    , HttpServletResponse response) {

        CartMemberDTO cartMemberDTO = cookieService.createCartMemberDTOToCartDetail(principal, request, response);
        String result = cartService.deleteSelectCartDetail(deleteCartDetailIds, cartMemberDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllCart(Principal principal
                                                , HttpServletRequest request
                                                , HttpServletResponse response){
        CartMemberDTO cartMemberDTO = cookieService.createCartMemberDTOToCartDetail(principal, request, response);
        String result = cartService.deleteCart(cartMemberDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/up/{detailId}")
    public ResponseEntity<String> patchCartUp(@PathVariable("detailId") Long cartDetailId
                                            , Principal principal
                                            , HttpServletRequest request
                                            , HttpServletResponse response) {
        CartMemberDTO cartMemberDTO = cookieService.createCartMemberDTOToCartDetail(principal, request, response);
        String result = cartService.patchCartUp(cartDetailId, cartMemberDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/down/{detailId}")
    public ResponseEntity<String> patchCartDown(@PathVariable("detailId") Long cartDetailId
                                                , Principal principal
                                                , HttpServletRequest request
                                                , HttpServletResponse response) {
        CartMemberDTO cartMemberDTO = cookieService.createCartMemberDTOToCartDetail(principal, request, response);
        String result = cartService.patchCartDown(cartDetailId, cartMemberDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
