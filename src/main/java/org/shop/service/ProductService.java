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

    public int addCart(List<String> pOpNo, List<String> pCount, List<String> pPrice, Principal principal, HttpServletRequest request, HttpServletResponse response) throws Exception;


    public int likeProduct(String pno, Principal principal) throws Exception;

    public int deLikeProduct(String pno, Principal principal) throws Exception;

    public int qnAInsertProc(ProductQnAInsertDTO dto, Principal principal) throws Exception;
}
