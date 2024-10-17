package org.shop.domain.dto.order.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class OrderProductResponseDTO {

    private List<OrderProductDTO> products;

    private int totalPrice;

    private int deliveryFee;

    private int finalTotalPrice;

    public OrderProductResponseDTO(List<OrderProductDTO> products) {
        this.products = products;
        this.totalPrice = products.stream().mapToInt(OrderProductDTO::getProductTotalPrice).sum();
        this.deliveryFee = totalPrice < 100000 ? 3500 : 0;
        this.finalTotalPrice = totalPrice + deliveryFee;
    }
}
