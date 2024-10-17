package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.dto.cart.business.CartMemberDTO;
import org.shop.domain.entity.Cart;

public interface CartMapper {

    Long findCartIdByUserIdOrCookieId(@Param("dto") CartMemberDTO dto);

    Long saveCart(Cart cart);

    void updateCartUpdatedAt(@Param("cartId") long cartId);

    void deleteById(long cartId);
}
