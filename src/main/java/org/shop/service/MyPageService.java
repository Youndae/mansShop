package org.shop.service;

import org.shop.domain.MemberVO;

import java.security.Principal;
import java.util.List;

public interface MyPageService {

    public void deleteCart(String id, List<String> pOpNoList);

    public int modifyCheckProc(MemberVO memberVO);

    public void cartCount(String pOpNo, String cPrice, String countType, Principal principal);
}
