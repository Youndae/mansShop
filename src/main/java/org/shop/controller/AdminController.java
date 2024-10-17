package org.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.dto.admin.member.out.AdminMemberResponseDTO;
import org.shop.domain.dto.admin.order.out.AdminOrderResponseDTO;
import org.shop.domain.dto.admin.product.out.*;
import org.shop.domain.dto.admin.qna.out.AdminMemberQnADetailDTO;
import org.shop.domain.dto.admin.qna.out.AdminProductQnADetailDTO;
import org.shop.domain.dto.admin.qna.out.AdminQnAClassificationResponseDTO;
import org.shop.domain.dto.admin.qna.out.AdminQnAListResponseDTO;
import org.shop.domain.dto.admin.review.business.AdminReviewDetailDTO;
import org.shop.domain.dto.admin.review.out.AdminReviewDetailResponseDTO;
import org.shop.domain.dto.admin.review.out.AdminReviewListDTO;
import org.shop.domain.dto.admin.sales.business.AdminPeriodSalesListDTO;
import org.shop.domain.dto.admin.sales.out.*;
import org.shop.domain.dto.paging.PageCriteria;
import org.shop.domain.dto.paging.PageDTO;
import org.shop.domain.dto.paging.PagingResponseDTO;
import org.shop.domain.mapper.PageableRequestMapper;
import org.shop.service.AdminService;
import org.shop.service.ResponseMappingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/")
@Controller
@Slf4j
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final AdminService adminService;

    private final ResponseMappingService responseMappingService;

    //상품 목록
    @GetMapping("/product")
    public String productList(@RequestParam(name = "page", required = false, defaultValue = "1") int page
                            , @RequestParam(name = "keyword", required = false) String keyword
                            , Model model) {
        PageCriteria cri = PageableRequestMapper.createDefaultAmountPageCriteria(page, keyword, null);
        List<AdminProductListDTO> dto = adminService.getProductList(cri);
        int totalElements = adminService.getProductListTotalElements(cri);
        PagingResponseDTO<AdminProductListDTO> result = responseMappingService.pagingResponseMapping(dto, cri, totalElements);

        model.addAttribute("list", result);

        return "admin/productList";
    }

    @GetMapping("/product/{productId}")
    public String productDetail(@PathVariable("productId") String productId
                                , Model model) {
        AdminProductDetailDTO result = adminService.getProductDetail(productId);

        model.addAttribute("detail", result);

        return "admin/productDetail";
    }

    @GetMapping("/product/add")
    public String addProduct(Model model) {

        List<String> classificationList = adminService.getProductClassificationList();

        model.addAttribute("classification", classificationList);

        return "admin/addProduct";
    }

    @GetMapping("/product/patch/{productId}")
    public String getProductPatchData(@PathVariable("productId") String productId
                                    , Model model) {
        AdminProductPatchDataDTO result = adminService.getProductPatchData(productId);

        model.addAttribute("detail", result);

        return "admin/productModify";
    }

    @GetMapping("/product/stock")
    public String getProductStockList(@RequestParam(name = "page", required = false, defaultValue = "1") int page
                                    , @RequestParam(name = "keyword", required = false) String keyword
                                    , Model model) {
        PageCriteria cri = PageableRequestMapper.createDefaultAmountPageCriteria(page, keyword, null);
        List<AdminProductStockDTO> content = adminService.getProductStockList(cri);
        int totalElements = adminService.getProductListTotalElements(cri);
        PagingResponseDTO<AdminProductStockDTO> result = responseMappingService.pagingResponseMapping(content, cri, totalElements);

        model.addAttribute("list", result);

        return "admin/productStockList";
    }

    @GetMapping("/product/discount")
    public String getProductDiscountList(@RequestParam(name = "page", required = false, defaultValue = "1") int page
                                        , @RequestParam(name = "keyword", required = false) String keyword
                                        , Model model){

        PageCriteria cri = PageableRequestMapper.createDefaultAmountPageCriteria(page, keyword, null);
        List<AdminDiscountResponseDTO> content = adminService.getProductDiscountList(cri);
        int totalElements = adminService.getProductDiscountListTotalElements(cri);
        PagingResponseDTO<AdminDiscountResponseDTO> dto = responseMappingService.pagingResponseMapping(content, cri, totalElements);
        model.addAttribute("list", dto);

        return "admin/productDiscountList";
    }

    @GetMapping("/product/discount/add")
    public String productDiscount(Model model) {
        List<String> classificationList = adminService.getProductClassificationList();
        model.addAttribute("classification", classificationList);

        return "admin/productDiscount";
    }

    @GetMapping("/order/new")
    public String getNewOrderList(@RequestParam(name = "page", required = false, defaultValue = "1") int page
                                , @RequestParam(name = "searchType", required = false) String searchType
                                , @RequestParam(value = "keyword", required = false) String keyword
                                , Model model) {
        PageCriteria cri = PageableRequestMapper.createDefaultAmountPageCriteria(page, keyword, searchType);
        List<AdminOrderResponseDTO> content = adminService.getNewOrderList(cri);
        int totalElements = adminService.getNewOrderListTotalElements(cri);
        PagingResponseDTO<AdminOrderResponseDTO> dto = responseMappingService.pagingResponseMapping(content, cri, totalElements);

        model.addAttribute("list", dto);

        return "admin/orderList";
    }

    @GetMapping("/order/all")
    public String getAllOrderList(@RequestParam(name = "page", required = false, defaultValue = "1") int page
                                , @RequestParam(name = "searchType", required = false) String searchType
                                , @RequestParam(value = "keyword", required = false) String keyword
                                , Model model) {
        PageCriteria cri = PageableRequestMapper.createDefaultAmountPageCriteria(page, keyword, searchType);
        List<AdminOrderResponseDTO> content = adminService.getAllOrderList(cri);
        int totalElements = adminService.getAllOrderListTotalElements(cri);
        PagingResponseDTO<AdminOrderResponseDTO> dto = responseMappingService.pagingResponseMapping(content, cri, totalElements);

        model.addAttribute("list", dto);

        return "admin/orderList";
    }

    @GetMapping("/qna/product/{type}")
    public String getProductQnAList(@PathVariable("type") String type
                                    , @RequestParam(name = "page", required = false, defaultValue = "1") int page
                                    , @RequestParam(value = "keyword", required = false) String keyword
                                    , Model model) {
        PageCriteria cri = PageableRequestMapper.createDefaultAmountPageCriteria(page, keyword, null);
        List<AdminQnAListResponseDTO> content = adminService.getProductQnAList(cri, type);
        int totalElements = adminService.getProductQnAListTotalElements(cri, type);
        PagingResponseDTO<AdminQnAListResponseDTO> dto = responseMappingService.pagingResponseMapping(content, cri, totalElements);

        model.addAttribute("list", dto);

        return "admin/productQnAList";
    }

    @GetMapping("/qna/product/detail/{qnaId}")
    public String getProductQnADetail(@PathVariable("qnaId") long qnaId
                                    , Model model) {
        AdminProductQnADetailDTO dto = adminService.getProductQnADetail(qnaId);

        model.addAttribute("detail", dto);

        return "admin/productQnADetail";
    }

    @GetMapping("/qna/member/{type}")
    public String getMemberQnAList(@PathVariable("type") String type
                                    , @RequestParam(name = "page", required = false, defaultValue = "1") int page
                                    , @RequestParam(value = "keyword", required = false) String keyword
                                    , Model model) {
        PageCriteria cri = PageableRequestMapper.createDefaultAmountPageCriteria(page, keyword, null);
        List<AdminQnAListResponseDTO> content = adminService.getMemberQnAList(cri, type);
        int totalElements = adminService.getMemberQnAListTotalElements(cri, type);
        PagingResponseDTO<AdminQnAListResponseDTO> dto = responseMappingService.pagingResponseMapping(content, cri, totalElements);

        model.addAttribute("list", dto);

        return "admin/memberQnAList";
    }

    @GetMapping("/qna/member/detail/{qnaId}")
    public String getMemberQnADetail(@PathVariable("qnaId") long qnaId
                                    , Model model) {

        AdminMemberQnADetailDTO dto = adminService.getMemberQnADetail(qnaId);

        model.addAttribute("detail", dto);

        return "admin/memberQnADetail";
    }

    @GetMapping("/qna/classification")
    public String getQnAClassificationList(Model model) {

        List<AdminQnAClassificationResponseDTO> content = adminService.getQnAClassificationList();

        model.addAttribute("list", content);

        return "admin/qnaClassification";
    }

    @GetMapping("/review")
    public String getReviewList(@RequestParam(name = "page", required = false, defaultValue = "1") int page
                                , @RequestParam(name = "searchType", required = false) String searchType
                                , @RequestParam(value = "keyword", required = false) String keyword
                                , Model model){
        /**
         * reviewId
         * writer
         * productName
         * updatedAt
         *
         * searchType -> userId, productName
         */

        PageCriteria cri = PageableRequestMapper.createDefaultAmountPageCriteria(page, keyword, searchType);
        List<AdminReviewListDTO> content = adminService.getReviewList(cri);
        int totalElements = adminService.getReviewListTotalElements(cri);
        PagingResponseDTO<AdminReviewListDTO> dto = responseMappingService.pagingResponseMapping(content, cri, totalElements);

        model.addAttribute("list", dto);

        return "admin/reviewList";
    }

    @GetMapping("/review/{reviewId}")
    public String getReviewDetail(@PathVariable("reviewId") long reviewId
                                    , Model model) {
        AdminReviewDetailResponseDTO content = adminService.getReviewDetail(reviewId);

        model.addAttribute("detail", content);

        return "admin/reviewDetail";
    }

    @GetMapping("/member")
    public String getMemberList(@RequestParam(name = "page", required = false, defaultValue = "1") int page
                                , @RequestParam(name = "searchType", required = false) String searchType
                                , @RequestParam(value = "keyword", required = false) String keyword
                                , Model model){
        PageCriteria cri = PageableRequestMapper.createDefaultAmountPageCriteria(page, keyword, searchType);
        List<AdminMemberResponseDTO> content = adminService.getMemberList(cri);
        int totalElements = adminService.getMemberListTotalElements(cri);
        PagingResponseDTO<AdminMemberResponseDTO> dto = responseMappingService.pagingResponseMapping(content, cri, totalElements);

        model.addAttribute("list", dto);

        return "admin/userList";
    }

    /**
     *
     * @param term = YYYY
     */
    @GetMapping("/sales/period/{term}")
    public String getPeriodSalesList(@PathVariable("term") int term
                                    , Model model) {

        AdminPeriodSalesResponseDTO<AdminPeriodSalesListDTO> content = adminService.getPeriodSalesListByYear(term);

        model.addAttribute("list", content);

        return "admin/periodSales";
    }

    /**
     *
     * @param term = YYYY-MM
     */
    @GetMapping("/sales/period/detail/{term}")
    public String getPeriodSalesDetail(@PathVariable("term") String term
                                        , Model model) {
        log.info("AdminController.getPeriodSalesDetail :: term : {}", term);
        AdminPeriodSalesMonthDetailDTO content = adminService.getPeriodDetail(term);

        model.addAttribute("list", content);

        return "admin/periodSalesDetail";
    }

    /**
     *
     * @param term = YYYY-MM-dd
     * @param page
     */
    @GetMapping("/sales/period/detail/date/{term}")
    public String getOrderListByDay(@PathVariable(value = "term") String term
                                    , @RequestParam(value = "page", required = false, defaultValue = "1") int page
                                    , Model model) {
        PageCriteria cri = PageableRequestMapper.createDefaultAmountPageCriteria(page, null, null);
        List<AdminDailySalesOrderResponseDTO> content = adminService.getOrderListByDay(term, cri);
        int totalElements = adminService.getOrderListByDayTotalCount(term);
        AdminDailySalesOrderListResponseDTO result = new AdminDailySalesOrderListResponseDTO(
                content
                , new PageDTO<>(cri, totalElements)
                , term
        );

        model.addAttribute("list", result);

        return "admin/salesOrderList";
    }

    @GetMapping("/sales/product")
    public String getProductSalesList(@RequestParam(value = "keyword", required = false) String keyword
                                    , @RequestParam(value = "page", required = false, defaultValue = "1") int page
                                    , Model model) {
        PageCriteria cri = PageableRequestMapper.createDefaultAmountPageCriteria(page, keyword, null);
        List<AdminProductSalesListDTO> content = adminService.getProductSalesList(cri);
        int totalElements = adminService.getProductSalesListTotalElements(cri);
        PagingResponseDTO<AdminProductSalesListDTO> result = responseMappingService.pagingResponseMapping(content, cri, totalElements);

        model.addAttribute("list", result);

        return "admin/productSales";
    }

    @GetMapping("/sales/product/detail/{productId}")
    public String getProductSalesDetail(@PathVariable("productId") String productId
                                        , Model model) {

        AdminProductSalesDetailDTO result = adminService.getProductSalesDetail(productId);

        model.addAttribute("detail", result);

        return "admin/productSalesDetail";
    }
}
