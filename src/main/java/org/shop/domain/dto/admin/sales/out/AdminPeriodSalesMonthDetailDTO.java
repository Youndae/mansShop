package org.shop.domain.dto.admin.sales.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.dto.admin.sales.business.AdminBestSalesProductDTO;
import org.shop.domain.dto.admin.sales.business.AdminPeriodClassificationDTO;
import org.shop.domain.dto.admin.sales.business.AdminPeriodSalesListDTO;
import org.shop.domain.dto.admin.sales.business.AdminPeriodSalesStatisticsDTO;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class AdminPeriodSalesMonthDetailDTO {

    private String term;

    private long sales;

    private long salesQuantity;

    private long orderQuantity;

    private long lastYearComparison;

    private long lastYearSales;

    private long lastYearSalesQuantity;

    private long lastYearOrderQuantity;

    private List<AdminBestSalesProductDTO> bestProduct;

    private List<AdminPeriodClassificationDTO> classificationSales;

    private List<AdminPeriodSalesListDTO> dailySales;

    public AdminPeriodSalesMonthDetailDTO(String term
                                        , AdminPeriodSalesStatisticsDTO monthStatistics
                                        , AdminPeriodSalesStatisticsDTO lastYearStatistics
                                        , List<AdminBestSalesProductDTO> bestProduct
                                        , List<AdminPeriodClassificationDTO> classificationSales
                                        , List<AdminPeriodSalesListDTO> dailySales) {
        this.term = term;
        this.sales = monthStatistics.getSales();
        this.salesQuantity = monthStatistics.getSalesQuantity();
        this.orderQuantity = monthStatistics.getOrderQuantity();
        this.lastYearComparison = monthStatistics.getSales() - lastYearStatistics.getSales();
        this.lastYearSales = lastYearStatistics.getSales();
        this.lastYearSalesQuantity = lastYearStatistics.getSalesQuantity();
        this.lastYearOrderQuantity = lastYearStatistics.getOrderQuantity();
        this.bestProduct = bestProduct;
        this.classificationSales = classificationSales;
        this.dailySales = dailySales;
    }
}
