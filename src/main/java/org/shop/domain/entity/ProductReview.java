package org.shop.domain.entity;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReview {

    private long id;

    private String productId;

    private long productOptionId;

    private String userId;

    private String reviewContent;

    private LocalDate createdAt;

    private LocalDate updatedAt;
}
