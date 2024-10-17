package org.shop.domain.dto.admin.sales.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.dto.admin.sales.business.AdminPeriodSalesListDTO;
import org.shop.domain.dto.admin.sales.business.AdminProductSalesDTO;
import org.shop.domain.dto.admin.sales.business.AdminProductSalesOptionDTO;
import org.shop.domain.dto.admin.sales.business.AdminSalesDTO;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class AdminProductSalesDetailDTO {

    private int thisYear;

    private String productName;

    private long totalSales;

    private long totalSalesQuantity;

    private long yearSales;

    private long yearSalesQuantity;

    private long lastYearComparison;

    private long lastYearSales;

    private long lastYearSalesQuantity;

    private List<AdminPeriodSalesListDTO> monthSales;

    private List<AdminProductSalesOptionDTO> optionTotalSales;

    private List<AdminProductSalesOptionDTO> optionYearSales;

    private List<AdminProductSalesOptionDTO> optionLastYearSales;

    public AdminProductSalesDetailDTO(int thisYear
                                      , AdminProductSalesDTO totalSalesDTO
                                        , AdminSalesDTO yearSalesDTO
                                        , AdminSalesDTO lastYearSalesDTO
                                        , List<AdminPeriodSalesListDTO> monthSales
                                        , List<AdminProductSalesOptionDTO> optionTotalSales
                                        , List<AdminProductSalesOptionDTO> optionYearSales
                                        , List<AdminProductSalesOptionDTO> optionLastYearSales){
        this.thisYear = thisYear;
        this.productName = totalSalesDTO.getProductName();
        this.totalSales = totalSalesDTO.getTotalSales();
        this.totalSalesQuantity = totalSalesDTO.getTotalSalesQuantity();
        this.yearSales = yearSalesDTO.getSales();
        this.yearSalesQuantity = yearSalesDTO.getSalesQuantity();
        this.lastYearComparison = yearSalesDTO.getSales() - lastYearSalesDTO.getSales();
        this.lastYearSales = lastYearSalesDTO.getSales();
        this.lastYearSalesQuantity = lastYearSalesDTO.getSalesQuantity();
        this.monthSales = monthSales;
        this.optionTotalSales = optionTotalSales;
        this.optionYearSales = optionYearSales;
        this.optionLastYearSales = optionLastYearSales;
    }
}
