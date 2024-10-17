package org.shop.domain.entity;

import lombok.*;
import org.shop.domain.dto.order.in.OrderPaymentRequestDTO;
import org.shop.domain.dto.order.out.OrderProductResponseDTO;
import org.shop.domain.enumeration.OrderStatus;

import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOrder {

    private long id;

    private String userId;

    private String recipient;

    private String orderPhone;

    private String orderAddress;

    private String orderMemo;

    private int orderTotalPrice;

    private int deliveryFee;

    private LocalDateTime createdAt;

    private String paymentType;

    private String status;

    private int productCount;

    public ProductOrder (OrderPaymentRequestDTO paymentRequestDTO, OrderProductResponseDTO productDTO, String userId) {
        this.userId = userId;
        this.recipient = paymentRequestDTO.getRecipient();
        this.orderPhone = paymentRequestDTO.getPhone();
        this.orderAddress = paymentRequestDTO.getAddress();
        this.orderMemo = paymentRequestDTO.getMemo();
        this.orderTotalPrice = productDTO.getFinalTotalPrice();
        this.deliveryFee = productDTO.getDeliveryFee();
        this.paymentType = paymentRequestDTO.getPaymentType();
        this.status = OrderStatus.ORDER.getStatusStr();
        this.productCount = productDTO.getProducts().size();
    }
}
