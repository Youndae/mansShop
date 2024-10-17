package org.shop.domain.dto.cart.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.shop.domain.entity.Cart;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartMemberDTO {

    private String userId;

    private String cookieId;

    public Cart toEntity() {
        return Cart.builder()
                .userId(this.userId)
                .cookieId(this.cookieId)
                .build();
    }
}
