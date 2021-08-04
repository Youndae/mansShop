package org.shop.mapper;

import org.shop.domain.*;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    public List<OrderVO> orderList(Criteria cri);

    public void shippingProcessing(String orderNo);

    public List<ProductQnAVO> adminQnAList(Criteria cri);

    public MyQnAVO adminQnADetail(int qno);

    public void adminQnAComplete(int qno);

    public int adminQnACheck(int qno);

    public List<MyQnAReplyVO> adminQnAReplyList(int qno);

    public void adminQnAReply(MyQnAReplyVO myQnAReplyVO);

    public void QnAReplyDelete(int replyNo);

    public MemberVO userInfo(String userId);

    public List<MemberVO> userList(Criteria cri);

    public List<ProductOpVO> salesProductList(Criteria cri);

    public List<SalesVO> salesTermList(Criteria cri);

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

    public int getUserTotal(Criteria cri);


    public OrderVO orderInfo(String orderNo);

    public List<OrderDetailVO> orderInfoTable(String orderNo);

    public int checkOrderStat(String orderNo);

    public int getAdminQnATotal(Criteria cri);

    public int getSalesProductTotal(Criteria cri);

    public int getSalesTermTotal(Criteria cri);

    public List<SalesVO> salesTermSelect();

    public List<ProductQnAVO> productQnAList(Criteria cri);

    public int getProductQnATotal(Criteria cri);

    public ProductQnAVO productQnADetail(long pQnANo);

    public void productQnAReply(ProductQnAVO productQnAVO);
}
