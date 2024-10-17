package org.shop.domain.dto.order.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.dto.order.business.UserOrderData;
import org.shop.domain.dto.paging.PageDTO;
import org.shop.domain.dto.paging.PagingResponseDTO;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class OrderListResponseDTO<T> extends PagingResponseDTO<T> {
    private UserOrderData userData;

    public OrderListResponseDTO(List<T> content, PageDTO<?> pageMaker, UserOrderData userData) {
        super(content, pageMaker);
        this.userData = userData;
    }
}
