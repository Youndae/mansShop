package org.shop.service;

import org.shop.domain.dto.myPage.out.LikeListDTO;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.dto.paging.PagingResponseDTO;

import java.util.List;

public interface ResponseMappingService {
    <T> PagingResponseDTO<T> pagingResponseMapping(List<T> content, Criteria cri, int totalElements);
}
