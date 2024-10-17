package org.shop.domain.entity;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOption {

    private Long id;

    private String productId;

    private String productSize;

    private String productColor;

    private int stock;

    private boolean isOpen;
}
