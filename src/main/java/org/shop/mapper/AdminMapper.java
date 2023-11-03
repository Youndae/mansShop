package org.shop.mapper;

import org.shop.domain.dto.admin.*;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.entity.*;

import java.util.List;

public interface AdminMapper {

    public List<ProductListDTO> productList(Criteria cri);

    public int getProductTotal(Criteria cri);

    public void addProduct(Product product);

    public void addProductOp(ProductOp productOp);

    public void addProductThumbnail(List<ProductThumbnail> productThumbnailList);

    public void addProductInfo(List<ProductImg> productImgList);

    public ProductInfoDTO productInfo(String pOpNo);

    public List<ProductFirstThumbnailDTO> getFirstThumb(String pno);

    public List<ProductThumbnailDTO> getThumbnail(String pno);

    public List<ProductInfoImageDTO> getInfoImg(String pno);

    public void modifyProductThumb(Product product);

    public void modifyProductInfo(Product product);

    public void modifyProductOp(ProductOp productOp);

    public void deleteThumb(List<String> pThumbnail);

    public void deleteInfoImg(List<String> pImg);

    int maxStep(String pno);

    public void closedProductOp(String pOpNo);

    public void openProductOp(String pOpNo);

    public void closedProduct(String pno);

    public void openProduct(String pno);

    public List<ProductOrder> orderList(Criteria cri);

    public int getOrderTotal(Criteria cri);

    public AdminProductOrderDTO orderInfo(String orderNo);

    public List<AdminOrderInfoTableDTO> orderInfoTable(String orderNo);

    public void shippingProcessing(String orderNo);

    public int checkOrderStat(String orderNo);

    public int getAdminQnATotal(Criteria cri);

    public List<ProductQnA> adminQnAList(Criteria cri);

    public MyQnA adminQnADetail(int qno);

    public List<MyQnAReplyGetDTO> adminQnAReplyList(int qno);

    public void adminQnAComplete(int qno);

    public int adminQnACheck(int qno);

    public void adminQnAReply(MyQnAReply myQnareply);

    public void qnAReplyDelete(int replyNo);

    public List<MemberListDTO> userList(Criteria cri);

    public int getUserTotal(Criteria cri);

    public MemberInfoDTO userInfo(String userId);

    public int getSalesProductTotal(Criteria cri);

    public List<SalesProductListDTO> salesProductList(Criteria cri);

    public int getSalesTermTotal(Criteria cri);

    public List<Sales> salesTermSelect();

    public List<Sales> salesTermList(Criteria cri);

    public List<ProductQnAListDTO> productQnAList(Criteria cri);

    public int getProductQnATotal(Criteria cri);

    public ProductQnADetailDTO productQnADetail(long pQnANo);

    public void productQnAReply(ProductQnA productQna);

    void productQnAComplete(long pQnANo);

}
