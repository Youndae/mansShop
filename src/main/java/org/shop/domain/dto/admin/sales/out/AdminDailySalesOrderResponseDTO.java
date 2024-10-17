package org.shop.domain.dto.admin.sales.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.dto.admin.sales.business.AdminSalesOrderListDTO;
import org.shop.domain.entity.ProductOrder;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminDailySalesOrderResponseDTO {

    private long totalPrice;

    private long deliveryFee;

    private String paymentType;

    private List<AdminSalesOrderListDTO> detailList;

    public AdminDailySalesOrderResponseDTO(ProductOrder entity, List<AdminSalesOrderListDTO> detailList) {
        this.totalPrice = entity.getOrderTotalPrice();
        this.deliveryFee = entity.getDeliveryFee();
        this.paymentType = entity.getPaymentType();
        this.detailList = detailList;
    }
}
