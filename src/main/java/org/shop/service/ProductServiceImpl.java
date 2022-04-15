package org.shop.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.shop.domain.*;
import org.shop.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Log4j
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

    private ProductMapper productMapper;

    @Override
    public ProductQnADTO getProductQnA(Criteria cri, String pno) {

        return new ProductQnADTO(
                productMapper.getProductQnATotal(pno),
                productMapper.getProductQnA(cri, pno)
        );
    }

    @Override
    public ProductReviewDTO getProductReview(Criteria cri, String pno) {

        log.info("ProductReview Service pno : " + pno + " cri" + cri.getPageNum());

        log.info("get ProductReviewTotal : " + productMapper.getProductReviewTotal(pno));

        log.info("getProductReview : " + productMapper.getProductReview(cri, pno));

        return new ProductReviewDTO(
                productMapper.getProductReviewTotal(pno),
                productMapper.getProductReview(cri, pno));
    }

    @Override
    public int addCart(List<String> pOpNo, List<String> pCount, List<String> pPrice, CartVO cartVO, CartDetailVO cartDetailVO) {
        log.info("addCart impl");

        /*
            이전 처리 코드.
            비회원은 처리할 수 없도록 되어있었던 코드.
        for(int i = 0; i < pOpNo.size(); i++){
            log.info("add cart data insert");
            cartVO.setCartNo(pOpNo.get(i) + cartVO.getUserId());
            cartVO.setPOpNo(pOpNo.get(i));
            cartVO.setCCount(Long.parseLong(pCount.get(i)));
            cartVO.setCPrice(Long.parseLong(pPrice.get(i)));

            if(productMapper.checkCart(cartVO.getCartNo()) == 0){
                log.info("add cart data insert VO");
                productMapper.addCart(cartVO);
                log.info("add cart data mapper");
            }else{
                productMapper.updateCart(cartVO);
            }
        }
        return 1;*/

        //장바구니에 회원 혹은 쿠키에 해당하는 데이터가 있다면
        if(productMapper.checkCart(cartVO) != 0){
            //setCartNo
            cartDetailVO.setCartNo(productMapper.checkCartNo(cartVO));

            for(int i = 0; i < pOpNo.size(); i++) {
                cartDetailVO = setDetail(i, cartDetailVO, pOpNo, pCount, pPrice);

                //detail에 같은 옵션 상품이 존재한다면
                if(productMapper.checkDetailOption(cartDetailVO) != 0){

                    productMapper.updateCartDetail(cartDetailVO); //해당 데이터의 Count와 Price 증가

                }else{ //detail에 같은 옵션 상품이 존재하지 않는다면 detail 테이블에 데이터 추가.

                    productMapper.addCartDetail(cartDetailVO);

                }
            }
            //cart 테이블 데이터의 updatedAt(수정일자) 수정.
            productMapper.updateCart(cartVO);
            return 1;
        }else{ //장바구니 테이블에 회원 혹은 쿠키에 해당하는 데이터가 없다면
            cartVO.setCartNo(LocalDateTime.now() + RandomStringUtils.random(4, true, true));
            cartDetailVO.setCartNo(cartVO.getCartNo());

            //cart insert
            productMapper.addCart(cartVO);

            for(int i = 0; i < pOpNo.size(); i++){
                cartDetailVO = setDetail(i, cartDetailVO, pOpNo, pCount, pPrice);

                //detail insert
                productMapper.addCartDetail(cartDetailVO);
            }
            return 1;
        }

    }

    public CartDetailVO setDetail(int i, CartDetailVO cartDetailVO, List<String> pOpNo, List<String> pCount, List<String> pPrice){

            //cartDetailVO에 set 해주는 코드.
            cartDetailVO.setCdNo(LocalDateTime.now() + pOpNo.get(i) + pCount.get(i));
            cartDetailVO.setPOpNo(pOpNo.get(i));
            cartDetailVO.setCCount(Integer.parseInt(pCount.get(i)));
            cartDetailVO.setCPrice(Long.parseLong(pPrice.get(i)));

        return cartDetailVO;

    }

}
