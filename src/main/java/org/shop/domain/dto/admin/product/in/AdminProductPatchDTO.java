package org.shop.domain.dto.admin.product.in;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.shop.domain.entity.Product;

import java.text.SimpleDateFormat;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AdminProductPatchDTO {
    private String productName;

    private String classification;

    private int price;

    private String isOpen;

    private int discount;

    private List<AdminPatchOptionDTO> newOptions;

    private List<AdminPatchOptionDTO> oldOptions;

    public Product toPostEntity() {
        StringBuffer sb = new StringBuffer();
        return Product.builder()
                .id(sb.append(this.classification)
                        .append(
                                new SimpleDateFormat("yyyyMMddHHmmss")
                                        .format(System.currentTimeMillis())
                        ).toString()
                )
                .classificationId(this.classification)
                .productName(this.productName)
                .productPrice(this.price)
                .thumbnail(null)
                .productDiscount(this.discount)
                .isOpen(this.isOpen.equals("true"))
                .build();
    }

    public Product toPatchEntity(String productId) {
        return Product.builder()
                        .id(productId)
                        .classificationId(this.classification)
                        .productName(this.productName)
                        .productPrice(this.price)
                        .thumbnail(null)
                        .productDiscount(this.discount)
                        .isOpen(this.isOpen.equals("true"))
                        .build();
    }
}
