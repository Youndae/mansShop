package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.dto.admin.product.business.AdminProductStockInfoDTO;
import org.shop.domain.dto.admin.product.in.AdminPatchDiscountDTO;
import org.shop.domain.dto.admin.product.out.AdminDiscountProductDTO;
import org.shop.domain.dto.admin.product.out.AdminDiscountResponseDTO;
import org.shop.domain.dto.admin.product.out.AdminProductListDTO;
import org.shop.domain.dto.admin.sales.out.AdminProductSalesListDTO;
import org.shop.domain.dto.cart.business.OptionAndCountListDTO;
import org.shop.domain.dto.order.business.OrderProductPatchDTO;
import org.shop.domain.dto.order.out.OrderProductDTO;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.dto.paging.PageCriteria;
import org.shop.domain.entity.*;

import java.util.List;


public interface ProductMapper {

    Product findById(@Param("productId") String productId);

    List<OrderProductDTO> findOrderProductByOptionIds(@Param("dto")OptionAndCountListDTO dto);

    void patchProductToOrder(List<OrderProductPatchDTO<String>> productPatchList);

    List<AdminProductListDTO> findAllByAdminProduct(@Param("cri") Criteria cri);

    int countByAdminProductTotalElements(@Param("cri") Criteria cri);

    void patchProduct(@Param("entity") Product product);

    void saveProduct(Product productEntity);

    List<AdminProductStockInfoDTO> findAllOrderByStock(@Param("cri") PageCriteria cri);

    List<AdminDiscountResponseDTO> findAllByDiscountList(@Param("cri") PageCriteria cri);

    int countByDiscountList(@Param("cri") PageCriteria cri);

    List<AdminDiscountProductDTO> findAllByClassificationId(@Param("category") String classificationId);

    void patchDiscountProduct(AdminPatchDiscountDTO discountDTO);

    List<AdminProductSalesListDTO> getProductSalesList(@Param("cri") PageCriteria cri);

    int getProductSalesListTotalElements(@Param("cri") PageCriteria cri);


}
