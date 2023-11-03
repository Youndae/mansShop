package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.dto.myPage.MemberOrderListDTO;
import org.shop.domain.entity.Product;

import java.util.List;

public interface MainMapper {

    public List<Product> mainBest();

    public List<Product> mainNew();

    public List<Product> mainClassification(Criteria cri);

    public int getProductTotal(Criteria cri);

    public int getSearchProduct(Criteria cri);

    public List<Product> searchProduct(Criteria cri);

    public List<MemberOrderListDTO> getNonOrderList(@Param("recipient") String recipient
                                                , @Param("orderPhone") String orderPhone);
}
