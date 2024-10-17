package org.shop.domain.dto.admin.order.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
public class AdminOrderResponseDTO {
    private long id;

    private String recipient;

    private String userId;

    private String phone;

    private LocalDate createdAt;

    private String status;
}
