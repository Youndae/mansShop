package org.shop.domain.dto.admin.sales.business;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;

@Getter
@ToString
@NoArgsConstructor
public class AdminSalesTermDTO {
    private int year;

    private int month;

    private Integer day;

    public AdminSalesTermDTO(String term) {
        int[] termArr = Arrays.stream(term.split("-"))
                                .mapToInt(Integer::parseInt)
                                .toArray();
        this.year = termArr[0];
        this.month = termArr[1];
        this.day = termArr.length == 2 ? null : termArr[2];
    }

    public void setMinusYear() {
        this.year = this.year - 1;
    }
}
