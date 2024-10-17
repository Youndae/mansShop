package org.shop.service;

import org.shop.domain.dto.member.in.MemberJoinDTO;
import org.shop.domain.dto.member.in.MemberResetPwDTO;
import org.shop.domain.dto.member.in.MemberSearchIdDTO;
import org.shop.domain.dto.member.in.MemberSearchPwDTO;

public interface MemberService {

    String join(MemberJoinDTO dto);

    String searchId(MemberSearchIdDTO searchIdDTO);

    String searchPw(MemberSearchPwDTO searchPwDTO);

    String resetPw(MemberResetPwDTO resetPwDTO);

    String checkCno(MemberResetPwDTO resetPwDTO);

    String checkJoinUserId(String userId);

    String checkJoinNickname(String nickname);
}
