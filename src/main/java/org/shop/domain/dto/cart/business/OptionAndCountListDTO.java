package org.shop.domain.dto.cart.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OptionAndCountListDTO {

    private List<Long> optionNoList;

    private List<Integer> countList;
}
