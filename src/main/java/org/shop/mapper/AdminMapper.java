package org.shop.mapper;

import org.shop.domain.*;

import java.util.List;

public interface AdminMapper {

    public List<ProductOpVO> productList();

    public void addProduct(ProductOpVO productOpVO);

    public void addProductOp(ProductOpVO productOpVO);

    public void productInfo();

    public void modifyProductInfo();

    public List<OrderVO> orderList();

    public void shippingProcessing();

    public void orderPacking();

    public void orderDetail();

    public List<ProductQnAVO> adminQnAList();

    public void adminQnADetail();

    public void adminQnAComplete();

    public void adminQnAReply();

    public void userInfo();

    public void userList();

    public List<SalesVO> salesProductList();

    public List<SalesVO> salesTermList();

    public void replyProductQnA();

    public List<ProductVO> pList() throws Exception;




    public List<ProductVO> getAttachList();
}
