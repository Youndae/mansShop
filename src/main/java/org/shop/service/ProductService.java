package org.shop.service;

import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.dto.product.ProductQnADTO;
import org.shop.domain.dto.product.ProductQnAInsertDTO;
import org.shop.domain.dto.product.ProductReviewDTO;
import org.shop.domain.entity.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

public interface ProductService {

    public ProductReviewDTO getProductReview(Criteria cri, String pno);

    public ProductQnADTO getProductQnA(Criteria cri, String pno);

    public String addCart(List<String> pOpNo
                        , List<String> pCount
                        , List<String> pPrice
                        , Principal principal
                        , HttpServletRequest request
                        , HttpServletResponse response);


    public String likeProduct(String pno, Principal principal);

    public String deLikeProduct(String pno, Principal principal);

    public String qnAInsertProc(ProductQnAInsertDTO dto, Principal principal);
}
