package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.*;

import java.util.List;

public interface MainMapper {

    public List<ProductOpVO> mainBest();

    public List<ProductOpVO> mainNew();

    public List<ProductOpVO> mainClassification(Criteria cri);

    public List<ProductOpVO> searchProduct(Criteria cri);

    public int getSearchProduct(Criteria cri);

    public int getProductTotal(Criteria cri);

    public List<MemberOrderListDTO> getNonOrderList(@Param("recipient") String recipient, @Param("orderPhone") String orderPhone);
}
