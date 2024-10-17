package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.dto.admin.qna.out.AdminQnAListResponseDTO;
import org.shop.domain.dto.myPage.qna.business.MyPageProductQnADetailDTO;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.dto.paging.PageCriteria;
import org.shop.domain.dto.product.in.ProductQnARequestDTO;
import org.shop.domain.entity.ProductQnA;

import java.util.List;

public interface ProductQnAMapper {
    List<ProductQnA> findAllDetailByProductId(@Param("productId") String productId, @Param("cri") Criteria cri);

    int countByProductId(@Param("productId") String productId);

    void saveQnA(ProductQnARequestDTO dto);

    MyPageProductQnADetailDTO findDetailByQnAIdAndUserId(@Param("qnaId") long qnaId, @Param("userId") String userId);

    ProductQnA findById(long id);

    void deleteById(@Param("id") long id);

    List<AdminQnAListResponseDTO> findAllByAdminQnAList(@Param("cri") PageCriteria cri, @Param("type") String type);

    int countByAdminQnAList(@Param("cri") PageCriteria cri, @Param("type") String type);

    MyPageProductQnADetailDTO findDetailById(@Param("qnaId") long qnaId);

    void patchStatusToTrue(@Param("id") long qnaId);
}
