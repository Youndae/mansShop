package org.shop.mapper;

import org.shop.domain.entity.Member;

public interface MemberMapper {

    int idCheck(String userId);

    public void join(Member member);

    public void joinAuth(String userId);

    public String searchId(Member member);

    public int checkUser(Member member);

    public int modifyPw(Member member);

}
