package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.OrderDetailVO;
import org.shop.domain.OrderVO;
import org.shop.domain.SalesVO;

public interface OrderMapper {

    //결제 할 상품 목록
    public void orderPayment(OrderVO orderVO);

    //주문 목록에 추가
    public void orderPaymentOp(OrderDetailVO orderDetailVO);

    //상품 매출에 추가
    public void productSales(OrderDetailVO orderDetailVO);

    //상품 옵션 매출에 추가
    public void productOpSales(OrderDetailVO orderDetailVO);

    //총 매출에 추가(sales 테이블). 해당 기간 매출 데이터가 없는경우.
    public void addTotalSales(SalesVO salesVO);

    //총 매출에 추가(sales 테이블). 해당 기간 매출 데이터가 존재하는 경우.
    public void updateTotalSales(SalesVO salesVO);

    //총 매출 테이블에서 가장 늦은 기간값
    public int maxSalesTerm(@Param("term") String term);

    //결제된 상품 카트에서 삭제.
    public void deleteOrderCart(@Param("userId") String userId, @Param("pOpNo") String pOpNo);

}
