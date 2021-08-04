package org.shop.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.CartVO;
import org.shop.domain.Criteria;
import org.shop.domain.ProductQnADTO;
import org.shop.domain.ProductReviewDTO;
import org.shop.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public int addCart(List<String> pOpNo, List<String> pCount, List<String> pPrice, CartVO cartVO) {
        log.info("addCart impl");

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
        return 1;
    }

}
