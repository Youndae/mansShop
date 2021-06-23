package org.shop.mapper;

import org.shop.domain.*;

import java.util.List;

public interface AdminMapper {

    public List<ProductOpVO> productList(Criteria cri);

    public void addProduct(ProductOpVO productOpVO);

    public void addProductOp(ProductOpVO productOpVO);

    public void addProductThumbnail(ThumbnailVO thumbnailVO);

    public void addProductInfo(ProductImgVO productImgVO);

    public ProductOpVO productInfo(String pOpNo);

    public void modifyProductThumb(ProductOpVO productOpVO);

    public void modifyProductInfo(ProductOpVO productOpVO);

    public void modifyProductOp(ProductOpVO productOpVO);

    public void deleteThumb(String pThumbnail);

    public void deleteInfoImg(String pImg);

    public void deleteProduct(String pno);

    public List<OrderVO> orderList(Criteria cri);

    public void shippingProcessing(String orderNo);

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




    public List<ProductVO> getFirstThumb(String pno);

    public List<ThumbnailVO> getThumbnail(String pno);

    public List<ProductImgVO> getInfoImg(String pno);

    int maxStep(String pno);

    public void closedProductOp(String pOpNo);

    public void openProductOp(String pOpNo);

    public void closedProduct(String pno);

    public void openProduct(String pno);


    public int getProductTotal(Criteria cri);

    public int getOrderTotal(Criteria cri);


    public OrderVO orderInfo(String orderNo);

    public List<OrderDetailVO> orderInfoTable(String orderNo);

    public int checkOrderStat(String orderNo);
}
