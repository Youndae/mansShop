package org.shop.domain.dto.order.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCartDetailDTO {

    private long cartId;

    private long detailId;

    private long optionId;

}
