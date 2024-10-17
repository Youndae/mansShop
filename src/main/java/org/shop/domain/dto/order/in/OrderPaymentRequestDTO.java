package org.shop.domain.dto.order.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaymentRequestDTO {

    private String recipient;

    private String phone;

    private String address;

    private String memo;

    private String paymentType;
}
