package org.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.dto.cart.business.OptionAndCountListDTO;
import org.shop.domain.dto.cart.business.CartMemberDTO;
import org.shop.domain.dto.cart.out.CartDetailDTO;
import org.shop.domain.dto.cart.out.CartDetailResponseDTO;
import org.shop.domain.entity.Cart;
import org.shop.domain.entity.CartDetail;
import org.shop.domain.enumeration.Result;
import org.shop.exception.customException.CustomAccessDeniedException;
import org.shop.exception.enumeration.ErrorCode;
import org.shop.mapper.CartDetailMapper;
import org.shop.mapper.CartMapper;
import org.shop.mapper.MyPageMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService{

    private final MyPageMapper myPageMapper;

    private final CartMapper cartMapper;

    private final CartDetailMapper cartDetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addCart(OptionAndCountListDTO optionAndCountListDTO, CartMemberDTO cartMemberDTO) {
        Long cartId = cartMapper.findCartIdByUserIdOrCookieId(cartMemberDTO);

        if(cartId == null) {
            Cart cart = cartMemberDTO.toEntity();
            cartMapper.saveCart(cart);
            cartId = cart.getId();
        }else
            cartMapper.updateCartUpdatedAt(cartId);

        Map<String, Object> params = new HashMap<>();

        params.put("cartId", cartId);
        params.put("optionIds", optionAndCountListDTO.getOptionNoList());
        params.put("cartCounts", optionAndCountListDTO.getCountList());

        cartDetailMapper.saveCartDetailList(params);

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public CartDetailResponseDTO getCartDetail(CartMemberDTO cartMemberDTO) {
        Long cartId = cartMapper.findCartIdByUserIdOrCookieId(cartMemberDTO);

        if(cartId == null)
            return null;

        List<CartDetailDTO> contents = cartDetailMapper.findAllDetailById(cartId);
        int totalPrice = contents.stream().mapToInt(CartDetailDTO::getTotalPrice).sum();

        return new CartDetailResponseDTO(contents, totalPrice);
    }

    @Override
    public String deleteSelectCartDetail(List<Long> deleteCartDetailIds, CartMemberDTO cartMemberDTO) {
        Long cartId = checkCartUser(cartMemberDTO);

        List<CartDetail> detailList = cartDetailMapper.findAllById(cartId);

        if(detailList.size() == deleteCartDetailIds.size())
            cartMapper.deleteById(cartId);
        else
            cartDetailMapper.deleteByIds(deleteCartDetailIds);

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public String deleteCart(CartMemberDTO cartMemberDTO) {
        Long cartId = checkCartUser(cartMemberDTO);
        cartMapper.deleteById(cartId);

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public String patchCartUp(Long cartDetailId, CartMemberDTO cartMemberDTO) {
        checkCartUser(cartMemberDTO);

        cartDetailMapper.patchCartDetailCount(cartDetailId, 1);

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public String patchCartDown(Long cartDetailId, CartMemberDTO cartMemberDTO) {
        checkCartUser(cartMemberDTO);

        cartDetailMapper.patchCartDetailCount(cartDetailId, -1);

        return Result.SUCCESS.getResultKey();
    }

    private Long checkCartUser(CartMemberDTO cartMemberDTO) {
        Long cartId = cartMapper.findCartIdByUserIdOrCookieId(cartMemberDTO);

        if(cartId == null)
            throw new CustomAccessDeniedException(ErrorCode.ACCESS_DENIED, ErrorCode.ACCESS_DENIED.getMessage());

        return cartId;
    }

}
