package org.shop.mapper;

public interface OrderMapper {

    //결제 할 상품 목록
    public void orderPayment();

    //주문 목록에 추가
    public void addOrderPayment();

    //상품 매출에 추가
    public void productSales();

    //총 매출에 추가(sales 테이블)
    public void addTotalSales();

}
