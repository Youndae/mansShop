package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.entity.ProductLike;

public interface ProductLikeMapper {
    boolean countByUserIdAndProductId(@Param("userId") String userId, @Param("productId") String productId);

    ProductLike findById(@Param("id") long likeId);

    void deleteById(@Param("id") long likeId);

    void likeProduct(ProductLike productLike);

    void deLikeProduct(ProductLike productLike);
}
