package org.shop.domain.dto.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.dto.order.business.UserOrderData;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PagingResponseDTO<T> {
    private List<T> content;

    private PageDTO<?> pageMaker;
}
