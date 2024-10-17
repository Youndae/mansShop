package org.shop.domain.dto.order.in;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderListDTO {

    private String recipient;

    private String orderPhone;

    private String userId;

    public ProductOrderListDTO(String recipient, String orderPhone) {
        this.recipient = recipient;
        this.orderPhone = orderPhone;
        this.userId = null;
    }

    public ProductOrderListDTO(String userId) {
        this.recipient = null;
        this.orderPhone = null;
        this.userId = userId;
    }
}
