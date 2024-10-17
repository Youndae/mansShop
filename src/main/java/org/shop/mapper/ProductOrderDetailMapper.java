package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.dto.admin.order.business.AdminOrderDetailListDTO;
import org.shop.domain.dto.admin.sales.business.*;
import org.shop.domain.dto.myPage.out.MyPageReviewPostDTO;
import org.shop.domain.dto.order.business.OrderDetailDTO;
import org.shop.domain.entity.ProductOrderDetail;

import java.util.List;
import java.util.Map;

public interface ProductOrderDetailMapper {
    List<OrderDetailDTO> findByDetailList(List<Long> orderIdList);

    void saveOrderDetails(Map<String, Object> orderDetails);

    List<AdminOrderDetailListDTO> findAllByOrderId(@Param("id") long orderId);

    List<AdminPeriodClassificationDTO> findPeriodClassification(@Param("dto") AdminSalesTermDTO dto);

    AdminClassificationSalesDTO findPeriodClassificationSales(@Param("term") AdminSalesTermDTO termDTO, @Param("classification") String classification);

    List<AdminClassificationSalesListDTO> findPeriodClassificationProductSales(@Param("term") AdminSalesTermDTO termDTO, @Param("classification") String classification);

    List<AdminSalesOrderDetailDTO> findAllByOrderIds(List<Long> orderIds);

    AdminSalesDTO getProductPeriodSales(@Param("year") int thisYear, @Param("id") String productId);

    List<AdminProductSalesOptionDTO> getProductOptionSales(@Param("thisYear") Integer year, @Param("id") String productId);

    MyPageReviewPostDTO findPostReviewDetailData(@Param("id") long orderDetailId, @Param("userId") String userId);

    ProductOrderDetail findById(@Param("id") long orderDetailId);

    void patchReviewStatus(@Param("id") long orderDetailId);

}
