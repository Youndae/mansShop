package org.shop.domain.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductLike {

    private long id;

    private String userId;

    private String productId;

    private LocalDate createdAt;
}
