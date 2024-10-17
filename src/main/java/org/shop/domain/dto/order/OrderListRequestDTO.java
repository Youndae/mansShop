package org.shop.domain.dto.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderListRequestDTO {

    private String term;

    private int page;

    private String termValue;

    private int offset;

    private final int amount = 20;

    public OrderListRequestDTO(String term, int page) {
        if(term.equals("all"))
            term = "1200";
        this.term = LocalDate.now().minusMonths(Long.parseLong(term)).toString();
        this.page = page;
        this.offset = (page - 1) * amount;
        this.termValue = term;
    }
}
