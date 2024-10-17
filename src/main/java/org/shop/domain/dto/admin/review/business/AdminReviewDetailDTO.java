package org.shop.domain.dto.admin.review.business;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
public class AdminReviewDetailDTO {
    private long reviewId;

    private String productName;

    private String userId;

    private String productSize;

    private String productColor;

    private String content;

    private LocalDate createdAt;

    private LocalDate updatedAt;
}
