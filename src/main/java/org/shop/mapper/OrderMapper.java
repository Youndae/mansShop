package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.entity.Cart;
import org.shop.domain.entity.ProductOrderDetail;
import org.shop.domain.entity.ProductOrder;
import org.shop.domain.entity.Sales;

import java.util.List;

public interface OrderMapper {

    public void orderPayment(ProductOrder productorder);

    public void orderPaymentOp(List<ProductOrderDetail> productOrderDetailList);

    public void productSales(List<ProductOrderDetail> productOrderDetailList);

    public void productOpSales(List<ProductOrderDetail> productOrderDetailList);

    public void addTotalSales(Sales sales);

    public void updateTotalSales(Sales sales);

    public int maxSalesTerm(@Param("term") String term);

    public int deleteCartCheck(Cart cart);

    public void deleteOrderCart(Cart cart);

    public void deleteOrderCartDetail(List<String> cdNoList);

}
