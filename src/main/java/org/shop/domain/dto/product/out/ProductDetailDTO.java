package org.shop.domain.dto.product.out;

import lombok.*;
import org.shop.domain.dto.paging.PagingResponseDTO;
import org.shop.domain.dto.product.business.ProductOptionDTO;
import org.shop.domain.dto.product.business.ProductQnAListDTO;
import org.shop.domain.dto.product.business.ProductReviewListDTO;
import org.shop.domain.entity.Product;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDTO {

    private String productId;

    private String productName;

    private int productPrice;

    private String productImageName;

    private boolean likeStat;

    private int discount;

    private int discountPrice;

    private List<ProductOptionDTO> options;

    private List<String> thumbnails;

    private List<String> infoImages;

    private PagingResponseDTO<ProductReviewListDTO> reviews;

    private PagingResponseDTO<ProductQnAListDTO> qnAs;

    @Builder
    public ProductDetailDTO(Product product
                            , boolean likeStat
                            , List<ProductOptionDTO> options
                            , List<String> thumbnails
                            , List<String> infoImages
                            , PagingResponseDTO<ProductReviewListDTO> reviews
                            , PagingResponseDTO<ProductQnAListDTO> qnAs) {
        this.productId = product.getId();
        this.productName = product.getProductName();
        this.productPrice = product.getProductPrice();
        this.productImageName = product.getThumbnail();
        this.likeStat = likeStat;
        this.discount = product.getProductDiscount();
        this.discountPrice = product.getProductPrice() - (product.getProductPrice() * product.getProductDiscount() / 100);
        this.options = options;
        this.thumbnails = thumbnails;
        this.infoImages = infoImages;
        this.reviews = reviews;
        this.qnAs = qnAs;
    }
}
