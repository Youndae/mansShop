package org.shop.service;

import org.shop.domain.dto.admin.member.in.AdminMemberPointRequestDTO;
import org.shop.domain.dto.admin.member.out.AdminMemberDetailDTO;
import org.shop.domain.dto.admin.member.out.AdminMemberResponseDTO;
import org.shop.domain.dto.admin.order.out.AdminOrderDetailDTO;
import org.shop.domain.dto.admin.order.out.AdminOrderResponseDTO;
import org.shop.domain.dto.admin.product.in.AdminPatchDiscountDTO;
import org.shop.domain.dto.admin.product.in.AdminProductDeleteImageDTO;
import org.shop.domain.dto.admin.product.in.AdminProductImageDTO;
import org.shop.domain.dto.admin.product.in.AdminProductPatchDTO;
import org.shop.domain.dto.admin.product.out.*;
import org.shop.domain.dto.admin.qna.out.AdminMemberQnADetailDTO;
import org.shop.domain.dto.admin.qna.out.AdminProductQnADetailDTO;
import org.shop.domain.dto.admin.qna.out.AdminQnAClassificationResponseDTO;
import org.shop.domain.dto.admin.qna.out.AdminQnAListResponseDTO;
import org.shop.domain.dto.admin.review.business.AdminReviewDetailDTO;
import org.shop.domain.dto.admin.review.out.AdminReviewDetailResponseDTO;
import org.shop.domain.dto.admin.review.out.AdminReviewListDTO;
import org.shop.domain.dto.admin.sales.business.AdminPeriodClassificationDTO;
import org.shop.domain.dto.admin.sales.business.AdminPeriodSalesListDTO;
import org.shop.domain.dto.admin.sales.out.*;
import org.shop.domain.dto.myPage.qna.in.QnAReplyRequestDTO;
import org.shop.domain.dto.paging.PageCriteria;

import java.security.Principal;
import java.util.List;

public interface AdminService {

    List<AdminProductListDTO> getProductList(PageCriteria cri);

    int getProductListTotalElements(PageCriteria cri);

    AdminProductDetailDTO getProductDetail(String productId);

    AdminProductPatchDataDTO getProductPatchData(String productId);

    String insertProduct(AdminProductImageDTO imageDTO, AdminProductPatchDTO dto);

    String patchProduct(String productId
                        , List<Long> deleteOptionIds
                        , AdminProductImageDTO imageDTO
                        , AdminProductDeleteImageDTO deleteImageDTO
                        , AdminProductPatchDTO patchDTO);

    List<String> getProductClassificationList();

    List<AdminProductStockDTO> getProductStockList(PageCriteria cri);

    List<AdminDiscountResponseDTO> getProductDiscountList(PageCriteria cri);

    int getProductDiscountListTotalElements(PageCriteria cri);

    List<AdminDiscountProductDTO> getProductListOfClassification(String classificationId);

    AdminDiscountSelectProductDTO getDiscountProductData(String productId);

    String patchDiscountProduct(AdminPatchDiscountDTO discountDTO);

    List<AdminOrderResponseDTO> getNewOrderList(PageCriteria cri);

    int getNewOrderListTotalElements(PageCriteria cri);

    List<AdminOrderResponseDTO> getAllOrderList(PageCriteria cri);

    int getAllOrderListTotalElements(PageCriteria cri);

    AdminOrderDetailDTO getOrderDetail(long orderId);

    String patchOrderStatus(long orderId);

    List<AdminQnAListResponseDTO> getProductQnAList(PageCriteria cri, String type);

    int getProductQnAListTotalElements(PageCriteria cri, String type);

    AdminProductQnADetailDTO getProductQnADetail(long qnaId);

    List<AdminQnAListResponseDTO> getMemberQnAList(PageCriteria cri, String type);

    int getMemberQnAListTotalElements(PageCriteria cri, String type);

    AdminMemberQnADetailDTO getMemberQnADetail(long qnaId);

    List<AdminQnAClassificationResponseDTO> getQnAClassificationList();

    String postProductQnAReply(QnAReplyRequestDTO replyDTO, Principal principal);

    String patchProductQnAReply(QnAReplyRequestDTO replyDTO);

    String patchProductQnAStatusToComplete(long qnaId);

    String postMemberQnAReply(QnAReplyRequestDTO replyDTO, Principal principal);

    String patchMemberQnAReply(QnAReplyRequestDTO replyDTO);

    String patchMemberQnAStatusToComplete(long qnaId);

    String postQnAClassification(String content);

    String deleteQnAClassification(long classificationId);

    List<AdminMemberResponseDTO> getMemberList(PageCriteria cri);

    int getMemberListTotalElements(PageCriteria cri);

    AdminMemberDetailDTO getMemberDetail(String userId);

    String patchPoint(AdminMemberPointRequestDTO dto);

    AdminPeriodSalesResponseDTO<AdminPeriodSalesListDTO> getPeriodSalesListByYear(int term);

    AdminPeriodSalesMonthDetailDTO getPeriodDetail(String term);

    AdminPeriodSalesResponseDTO<AdminPeriodClassificationDTO> getPeriodSalesByDay(String term);

    AdminClassificationSalesResponseDTO getPeriodClassificationList(String term, String classification);

    List<AdminDailySalesOrderResponseDTO> getOrderListByDay(String term, PageCriteria cri);

    int getOrderListByDayTotalCount(String term);

    List<AdminProductSalesListDTO> getProductSalesList(PageCriteria cri);

    int getProductSalesListTotalElements(PageCriteria cri);

    AdminProductSalesDetailDTO getProductSalesDetail(String productId);

    List<AdminReviewListDTO> getReviewList(PageCriteria cri);

    int getReviewListTotalElements(PageCriteria cri);

    AdminReviewDetailResponseDTO getReviewDetail(long reviewId);

    String postReviewReply(long reviewId, String content);
}
