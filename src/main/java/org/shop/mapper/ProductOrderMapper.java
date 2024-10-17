package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.dto.admin.order.out.AdminOrderResponseDTO;
import org.shop.domain.dto.admin.sales.business.*;
import org.shop.domain.dto.order.in.ProductOrderListDTO;
import org.shop.domain.dto.order.OrderListRequestDTO;
import org.shop.domain.dto.paging.PageCriteria;
import org.shop.domain.entity.ProductOrder;

import java.util.List;

public interface ProductOrderMapper {

    List<ProductOrder> findOrderList(@Param("dto") ProductOrderListDTO dto, @Param("orderDTO") OrderListRequestDTO orderDTO);

    int findOrderListCount(@Param("dto") ProductOrderListDTO dto, @Param("orderDTO") OrderListRequestDTO orderDTO);

    Long saveOrder(ProductOrder orderEntity);

    List<AdminOrderResponseDTO> findAllNewOrderList(@Param("cri") PageCriteria cri);

    int countByNewOrderList(@Param("cri") PageCriteria cri);

    List<AdminOrderResponseDTO> findAll(@Param("cri") PageCriteria cri);

    int countByAllList(@Param("cri") PageCriteria cri);

    ProductOrder findById(@Param("id") long orderId);

    void patchOrderStatus(@Param("id") long orderId, @Param("status") String statusStr);

    List<AdminPeriodSalesListDTO> findPeriodList(int term);

    AdminPeriodSalesStatisticsDTO findPeriodStatistics(AdminSalesTermDTO termDTO);

    List<AdminBestSalesProductDTO> findPeriodBestProduct(AdminSalesTermDTO termDTO);

    List<AdminPeriodSalesListDTO> findPeriodDailyList(AdminSalesTermDTO termDTO);

    AdminClassificationSalesDTO findDailySales(AdminSalesTermDTO termDTO);

    List<ProductOrder> findAllByDayToPagination(@Param("term") AdminSalesTermDTO termDTO, @Param("cri") PageCriteria cri);

    int countBySalesOrderList(@Param("term") AdminSalesTermDTO termDTO);

    AdminProductSalesDTO getProductSales(@Param("id") String productId);

    List<AdminPeriodSalesListDTO> getProductMonthPeriodSales(@Param("year") int thisYear, @Param("id") String productId);

}
