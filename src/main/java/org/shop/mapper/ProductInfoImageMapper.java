package org.shop.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductInfoImageMapper {
    List<String> findAllByProductId(@Param("productId") String productId);

    void saveInfoImages(Map<String, Object> params);

    void deleteByImageNames(List<String> deleteInfoImages);

}
