package org.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductQnADTO {
    private long productQnACount;
    private List<ProductQnAVO> productQnAList;
}
