package org.shop.domain.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewReply {

    private long id;

    private String userId;

    private long reviewId;

    private String replyContent;

    private LocalDate createdAt;

    private LocalDate updatedAt;
}
