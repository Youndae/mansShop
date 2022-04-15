package org.shop.service;

import org.shop.domain.CartVO;
import org.shop.domain.MemberVO;

import java.security.Principal;
import java.util.List;

public interface MyPageService {

    public void deleteCart(CartVO cartVO, List<String> cdNoList);

    public int modifyCheckProc(MemberVO memberVO);

    public void cartCount(String cdNo, String cPrice, String countType);
}
