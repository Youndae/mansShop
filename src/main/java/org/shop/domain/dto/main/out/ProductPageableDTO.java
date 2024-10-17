package org.shop.domain.dto.main.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPageableDTO<T> {

    private List<T> content;

    private int totalElements;
}
