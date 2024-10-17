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
public class AdminProductPatchDataDTO {

    private String productId;

    private String productName;

    private String classification;

    private String firstThumbnail;

    private int price;

    private boolean isOpen;

    private int discount;

    private List<ProductOptionDTO> optionList;

    private List<String> thumbnailList;

    private List<String> infoImageList;

    private List<String> classificationList;

    public AdminProductPatchDataDTO(Product entity
                                    , List<String> thumbnailList
                                    , List<String> infoImageList
                                    , List<String> classificationList
                                    , List<ProductOptionDTO> optionList) {
        this.productId = entity.getId();
        this.productName = entity.getProductName();
        this.classification = entity.getClassificationId();
        this.firstThumbnail = entity.getThumbnail();
        this.price = entity.getProductPrice();
        this.isOpen = entity.isOpen();
        this.discount = entity.getProductDiscount();
        this.optionList = optionList;
        this.thumbnailList = thumbnailList;
        this.infoImageList = infoImageList;
        this.classificationList = classificationList;
    }
}
