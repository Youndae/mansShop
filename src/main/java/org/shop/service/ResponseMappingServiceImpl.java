package org.shop.service;

import lombok.extern.slf4j.Slf4j;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.dto.paging.PageDTO;
import org.shop.domain.dto.paging.PagingResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ResponseMappingServiceImpl implements ResponseMappingService{

    @Override
    public <T> PagingResponseDTO<T> pagingResponseMapping(List<T> content, Criteria cri, int totalElements) {

        return new PagingResponseDTO<>(content, new PageDTO<>(cri, totalElements));
    }
}
