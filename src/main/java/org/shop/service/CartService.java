package org.shop.service;

import org.shop.domain.dto.cart.business.OptionAndCountListDTO;
import org.shop.domain.dto.cart.business.CartMemberDTO;
import org.shop.domain.dto.cart.out.CartDetailResponseDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

public interface CartService {

    String addCart(OptionAndCountListDTO optionAndCountListDTO, CartMemberDTO cartMemberDTO);

    CartDetailResponseDTO getCartDetail(CartMemberDTO cartMemberDTO);

    String deleteSelectCartDetail(List<Long> deleteCartDetailIds, CartMemberDTO cartMemberDTO);

    String deleteCart(CartMemberDTO cartMemberDTO);

    String patchCartUp(Long cartDetailId, CartMemberDTO cartMemberDTO);

    String patchCartDown(Long cartDetailId, CartMemberDTO cartMemberDTO);


}
