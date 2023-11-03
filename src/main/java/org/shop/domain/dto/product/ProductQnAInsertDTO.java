package org.shop.domain.dto.product;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductQnAInsertDTO {

    private String pno;

    private String pQnAContent;
}
