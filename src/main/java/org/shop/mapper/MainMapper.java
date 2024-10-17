package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.dto.main.out.MainListDTO;
import org.shop.domain.dto.paging.MainPageCriteria;

import java.util.List;

public interface MainMapper {

    List<MainListDTO> findMainBestAndNew(@Param("type") String type);

    List<MainListDTO> findClassificationList(@Param("cri") MainPageCriteria cri);

    int findClassificationElementsCount(@Param("cri") MainPageCriteria cri);

    List<MainListDTO> findSearchProduct(@Param("cri")MainPageCriteria cri);

    int findSearchProductCount(@Param("cri")MainPageCriteria cri);

}
