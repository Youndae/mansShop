package org.shop.domain.dto.admin.review.business;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
public class AdminReviewReplyDTO {

    private long detailId;

    private String content;

    private LocalDate updatedAt;
}
