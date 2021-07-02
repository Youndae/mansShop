package org.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewDTO {
    private long reviewCount;
    private List<ProductReviewVO> reviewList;


}
