package org.shop.domain.dto.order.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderData {
    private String recipient;

    private String orderPhone;

}
