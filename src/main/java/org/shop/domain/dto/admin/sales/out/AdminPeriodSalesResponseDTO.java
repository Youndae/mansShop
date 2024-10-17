package org.shop.domain.dto.admin.sales.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminPeriodSalesResponseDTO <T> {
    private List<T> content;

    private String term;

    private long sales;

    private long salesQuantity;

    private long orderQuantity;
}
