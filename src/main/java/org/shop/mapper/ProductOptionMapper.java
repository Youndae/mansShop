package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.dto.order.business.OrderProductPatchDTO;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.dto.product.business.ProductOptionDTO;
import org.shop.domain.entity.ProductOption;

import java.util.List;
import java.util.Map;

public interface ProductOptionMapper {
    List<ProductOptionDTO> findAllDetailByProductId(@Param("productId") String productId);

    void patchProductOptionToOrder(List<OrderProductPatchDTO<Long>> optionPatchList);

    void saveOptions(Map<String, Object> options);

    void patchOptions(List<ProductOption> oldOptions);

    void deleteByIds(List<Long> deleteOptionIds);

    List<ProductOption> findAllByProductId(List<String> productIds);

    List<ProductOption> findAllOptionByProductId(@Param("id") String productId);
}
