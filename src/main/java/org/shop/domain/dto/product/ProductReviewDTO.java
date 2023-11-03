package org.shop.domain.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shop.domain.entity.ProductReview;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewDTO {

    private long reviewCount;

    private List<ProductReview> reviewList;

}
