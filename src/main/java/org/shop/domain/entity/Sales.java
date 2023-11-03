package org.shop.domain.entity;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sales {

    private String salesTerm;

    private Long salesSum;

    private Long salesOrders;
}
