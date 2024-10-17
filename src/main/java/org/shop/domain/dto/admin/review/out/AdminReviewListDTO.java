package org.shop.domain.dto.admin.review.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
public class AdminReviewListDTO {

    private long reviewId;

    private String productName;

    private String userId;

    private LocalDate updatedAt;
}
