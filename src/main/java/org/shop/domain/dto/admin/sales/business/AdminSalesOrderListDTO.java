package org.shop.domain.dto.admin.sales.business;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AdminSalesOrderListDTO {

    private String productName;

    private String productSize;

    private String productColor;

    private int count;

    private int price;

    public AdminSalesOrderListDTO(AdminSalesOrderDetailDTO dto) {
        this.productName = dto.getProductName();
        this.productSize = dto.getProductSize();
        this.productColor = dto.getProductColor();
        this.count = dto.getCount();
        this.price = dto.getPrice();
    }
}
