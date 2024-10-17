package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.dto.cart.business.CartMemberDTO;
import org.shop.domain.dto.cart.out.CartDetailDTO;
import org.shop.domain.dto.order.business.OrderCartDetailDTO;
import org.shop.domain.dto.order.out.OrderProductDTO;
import org.shop.domain.entity.CartDetail;

import java.util.List;
import java.util.Map;

public interface CartDetailMapper {

    void saveCartDetailList(Map<String, Object> params);

    List<CartDetailDTO> findAllDetailById(long cartId);

    List<CartDetail> findAllById(long cartId);

    void deleteByIds(List<Long> deleteCartDetailIds);

    void patchCartDetailCount(@Param("cartDetailId") long cartDetailId, @Param("countValue") int countValue);

    Long findCartIdByCartIdAndDetailId(long detailId);

    List<OrderProductDTO> findOrderProductByOptionIds(List<Long> cartDetailIds);

    List<OrderCartDetailDTO> findAllDetailByUserIdOrCookieId(@Param("dto") CartMemberDTO cartMemberDTO);
}
