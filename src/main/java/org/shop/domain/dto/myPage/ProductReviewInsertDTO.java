package org.shop.domain.dto.myPage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewInsertDTO {

    private String pno;

    private String reviewContent;
}
