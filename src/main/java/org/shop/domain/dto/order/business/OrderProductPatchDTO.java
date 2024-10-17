package org.shop.domain.dto.order.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductPatchDTO <T> {

    private T id;
    private int count;
}
