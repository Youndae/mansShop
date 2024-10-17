package org.shop.domain.dto.admin.order.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.dto.admin.order.business.AdminOrderDetailListDTO;
import org.shop.domain.entity.ProductOrder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class AdminOrderDetailDTO {
    private long id;

    private String recipient;

    private String userId;

    private LocalDateTime createdAt;

    private String phone;

    private String address;

    private String status;

    List<AdminOrderDetailListDTO> detailList;

    public AdminOrderDetailDTO(ProductOrder entity, List<AdminOrderDetailListDTO> detailList) {
        this.id = entity.getId();
        this.recipient = entity.getRecipient();
        this.userId = entity.getUserId().equals("Anonymous") ? "미가입자" : entity.getUserId();
        this.createdAt = entity.getCreatedAt();
        this.phone = entity.getOrderPhone();
        this.address = entity.getOrderAddress();
        this.status = entity.getStatus();
        this.detailList = detailList;
    }
}
