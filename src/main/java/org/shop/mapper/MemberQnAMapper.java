package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.dto.admin.qna.out.AdminQnAListResponseDTO;
import org.shop.domain.dto.myPage.qna.business.MyPageMemberQnADetailDTO;
import org.shop.domain.dto.myPage.qna.in.MemberQnARequestDTO;
import org.shop.domain.dto.paging.PageCriteria;
import org.shop.domain.entity.MemberQnA;

import java.util.List;

public interface MemberQnAMapper {
    MyPageMemberQnADetailDTO findDetailByIdAndUserId(@Param("qnaId") long qnaId, @Param("userId") String userId);

    MemberQnA findById(long id);

    void patchStatus(long id);

    Long saveEntity(MemberQnA entity);

    void patchQnA(@Param("id") long qnaId, @Param("dto") MemberQnARequestDTO dto);

    void deleteById(@Param("id") long qnaId);

    List<AdminQnAListResponseDTO> findAllByAdminQnAList(@Param("cri") PageCriteria cri, @Param("type") String type);

    int countByAdminQnAList(@Param("cri") PageCriteria cri, @Param("type") String type);

    MyPageMemberQnADetailDTO findDetailById(@Param("qnaId") long qnaId);

    void patchStatusToTrue(@Param("id") long qnaId);
}
