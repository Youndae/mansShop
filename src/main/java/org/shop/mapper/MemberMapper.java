package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.dto.admin.member.in.AdminMemberPointRequestDTO;
import org.shop.domain.dto.admin.member.out.AdminMemberResponseDTO;
import org.shop.domain.dto.member.business.MemberDTO;
import org.shop.domain.dto.paging.PageCriteria;
import org.shop.domain.entity.Member;

import java.util.List;

public interface MemberMapper {

    Member getInfo(@Param("userId") String userId);

    Member findById(@Param("userId") String userId);

    void patchMember(@Param("entity") Member member);

    Member findByNickname(@Param("nickname") String nickname);

    List<AdminMemberResponseDTO> findAllByAdminMemberList(@Param("cri") PageCriteria cri);

    int countByAdminMemberList(@Param("cri") PageCriteria cri);

    void patchPoint(AdminMemberPointRequestDTO dto);

    int countByUserId(@Param("id") String userId);

    int countByNickname(String nickname);

    void join(Member member);

    String searchId(@Param("entity") Member member);

    int checkUser(Member member);

    int modifyPw(Member member);

    MemberDTO getUserInfo(String userId);

}
