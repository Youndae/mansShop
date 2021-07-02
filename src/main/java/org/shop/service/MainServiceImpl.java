package org.shop.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.Criteria;
import org.shop.domain.ProductQnADTO;
import org.shop.domain.ProductReviewDTO;
import org.shop.mapper.MainMapper;
import org.springframework.stereotype.Service;

@Service
@Log4j
@AllArgsConstructor
public class MainServiceImpl implements MainService{

    private MainMapper mainMapper;

    @Override
    public ProductReviewDTO getProductReview(Criteria cri, String pno) {

        log.info("ProductReview Service pno : " + pno + " cri" + cri.getPageNum());

        log.info("get ProductReviewTotal : " + mainMapper.getProductReviewTotal(pno));

        log.info("getProductReview : " + mainMapper.getProductReview(cri, pno));

        return new ProductReviewDTO(
                mainMapper.getProductReviewTotal(pno),
                mainMapper.getProductReview(cri, pno));
    }

    @Override
    public ProductQnADTO getProductQnA(Criteria cri, String pno) {


        return new ProductQnADTO(
                mainMapper.getProductQnATotal(pno),
                mainMapper.getProductQnA(cri, pno)
        );
    }
}
