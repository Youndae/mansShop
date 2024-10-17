package org.shop.service;

import org.shop.domain.dto.paging.PagingResponseDTO;
import org.shop.domain.dto.product.business.ProductQnAListDTO;
import org.shop.domain.dto.product.business.ProductReviewListDTO;
import org.shop.domain.dto.product.in.ProductQnARequestDTO;
import org.shop.domain.dto.product.out.ProductDetailDTO;
import java.security.Principal;

public interface ProductService {

    ProductDetailDTO productDetail(String productId, Principal principal);

    PagingResponseDTO<ProductReviewListDTO> getDetailReview(String productId, int page);

    PagingResponseDTO<ProductQnAListDTO> getDetailProductQnA(String productId, int page);

    String insertQnA(ProductQnARequestDTO dto);

    String likeProduct(String pno, Principal principal);

    String deLikeProduct(String pno, Principal principal);

}
