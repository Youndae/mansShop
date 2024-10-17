package org.shop.domain.entity;

import lombok.*;
import org.shop.domain.dto.order.out.OrderProductDTO;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOrderDetail {

    private long id;

    private long productOptionId;

    private String productId;

    private long orderId;

    private int orderDetailCount;

    private int orderDetailPrice;

    private boolean orderReviewStatus;

    public ProductOrderDetail (OrderProductDTO dto, long orderId) {
        this.productOptionId = dto.getProductOptionId();
        this.productId = dto.getProductId();
        this.orderId = orderId;
        this.orderDetailCount = dto.getCount();
        this.orderDetailPrice = dto.getProductTotalPrice();
    }
}
