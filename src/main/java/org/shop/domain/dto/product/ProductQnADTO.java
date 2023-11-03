package org.shop.domain.dto.product;

import lombok.*;
import org.shop.domain.entity.ProductQnA;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductQnADTO {

    private long productQnACount;

    private List<ProductQnA> productQnAList;
}
