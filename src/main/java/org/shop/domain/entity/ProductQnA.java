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
public class ProductQnA {

    private long id;

    private String productId;

    private String userId;

    private String qnaContent;

    private LocalDate createdAt;

    private boolean status;
}
