package org.shop.domain.dto.admin.sales.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.dto.paging.PageCriteria;
import org.shop.domain.dto.paging.PageDTO;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminDailySalesOrderListResponseDTO {
    private List<AdminDailySalesOrderResponseDTO> content;

    private PageDTO<PageCriteria> pageMaker;

    private String term;


}
