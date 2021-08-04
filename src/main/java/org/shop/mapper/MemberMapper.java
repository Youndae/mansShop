package org.shop.mapper;

import org.shop.domain.MemberVO;

public interface MemberMapper {

    public void join(MemberVO memberVO);

    public void joinAuth(String userId);

    int idCheck(String userId) throws Exception;
}
