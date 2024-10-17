package org.shop.domain.dto.order.out;

import lombok.*;
import org.shop.domain.dto.order.business.OrderDetailDTO;
import org.shop.domain.entity.ProductOrder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderListDTO {
    private long orderId;

    private int orderTotalPrice;

    private LocalDateTime orderDate;

    private String orderStat;

    List<OrderDetailDTO> detail;

    public OrderListDTO(ProductOrder entity, List<OrderDetailDTO> detail) {
        this.orderId = entity.getId();
        this.orderTotalPrice = entity.getOrderTotalPrice();
        this.orderDate = entity.getCreatedAt();
        this.orderStat = entity.getStatus();
        this.detail = detail;
    }
}
