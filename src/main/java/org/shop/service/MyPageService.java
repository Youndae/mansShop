package org.shop.service;

import org.shop.domain.MemberVO;

import java.util.List;

public interface MyPageService {

    public void deleteCart(String id, List<String> pOpNoList);

    public int modifyCheckProc(MemberVO memberVO);
}
