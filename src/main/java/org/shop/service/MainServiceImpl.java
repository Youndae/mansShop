package org.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.dto.main.out.ProductPageableDTO;
import org.shop.domain.dto.main.out.MainListDTO;
import org.shop.domain.dto.paging.MainPageCriteria;
import org.shop.mapper.MainMapper;
import org.shop.mapper.ProductOrderDetailMapper;
import org.shop.mapper.ProductOrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainServiceImpl implements MainService{

    private final MainMapper mainMapper;

    @Override
    public List<MainListDTO> getMainBestAndNewList(String type) {

        return mainMapper.findMainBestAndNew(type);
    }

    @Override
    public ProductPageableDTO<MainListDTO> getMainClassificationList(MainPageCriteria cri) {
        List<MainListDTO> dto = mainMapper.findClassificationList(cri);
        int totalElements = mainMapper.findClassificationElementsCount(cri);

        return new ProductPageableDTO<>(dto, totalElements);
    }

    @Override
    public ProductPageableDTO<MainListDTO> getMainSearchList(MainPageCriteria cri) {
        List<MainListDTO> dto = mainMapper.findSearchProduct(cri);
        int totalElements = mainMapper.findSearchProductCount(cri);

        return new ProductPageableDTO<>(dto, totalElements);
    }

}
