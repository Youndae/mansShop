package org.shop.domain.dto.admin.review.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.dto.admin.review.business.AdminReviewDetailDTO;
import org.shop.domain.dto.admin.review.business.AdminReviewReplyDTO;


@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminReviewDetailResponseDTO {

    private AdminReviewDetailDTO review;

    private AdminReviewReplyDTO reply;
}
