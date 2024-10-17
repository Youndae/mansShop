package org.shop.domain.entity;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDetail {

    private long id;

    private long productOptionId;

    private long cartId;

    private int cartCount;

}
