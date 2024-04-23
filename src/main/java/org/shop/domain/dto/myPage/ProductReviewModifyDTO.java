package org.shop.domain.dto.myPage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReviewModifyDTO {

    private long rNum;

    private String reviewContent;
}
