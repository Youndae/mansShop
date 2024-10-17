package org.shop.domain.dto.admin.product.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.dto.product.business.ProductOptionDTO;
import org.shop.domain.entity.Product;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminProductDetailDTO {

    private String productId;

    private String classification;

    private String productName;

    private String firstThumbnail;

    private List<String> thumbnailList;

    private List<String> infoImageList;

    private List<ProductOptionDTO> optionList;

    private int price;

    private boolean isOpen;

    private long sales;

    private int discount;

    private int discountPrice;

    public AdminProductDetailDTO(Product entity
                                , List<String> thumbnailList
                                , List<String> infoImageList
                                , List<ProductOptionDTO> optionList) {
        this.productId = entity.getId();
        this.classification = entity.getClassificationId();
        this.productName = entity.getProductName();
        this.firstThumbnail = entity.getThumbnail();
        this.thumbnailList = thumbnailList;
        this.infoImageList = infoImageList;
        this.optionList = optionList;
        this.price = entity.getProductPrice();
        this.isOpen = entity.isOpen();
        this.sales = entity.getProductSales();
        this.discount = entity.getProductDiscount();
        this.discountPrice = this.price - (this.price * this.discount / 100);
    }
}
