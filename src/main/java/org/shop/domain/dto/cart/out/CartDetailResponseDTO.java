package org.shop.domain.dto.cart.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailResponseDTO {
    private List<CartDetailDTO> content;

    private int totalPrice;
}
