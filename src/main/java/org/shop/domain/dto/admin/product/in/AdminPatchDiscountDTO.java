package org.shop.domain.dto.admin.product.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminPatchDiscountDTO {
    private int discount;

    private List<String> productIds;
}
