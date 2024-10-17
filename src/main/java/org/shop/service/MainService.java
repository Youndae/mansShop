package org.shop.service;

import org.shop.domain.dto.main.out.ProductPageableDTO;
import org.shop.domain.dto.main.out.MainListDTO;
import org.shop.domain.dto.paging.MainPageCriteria;

import java.util.List;

public interface MainService {

    List<MainListDTO> getMainBestAndNewList(String type);

    ProductPageableDTO<MainListDTO> getMainClassificationList(MainPageCriteria cri);

    ProductPageableDTO<MainListDTO> getMainSearchList(MainPageCriteria cri);
}
