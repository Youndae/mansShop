package org.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.dto.myPage.out.*;
import org.shop.domain.dto.myPage.qna.out.*;
import org.shop.domain.dto.order.OrderListRequestDTO;
import org.shop.domain.dto.order.in.ProductOrderListDTO;
import org.shop.domain.dto.order.out.OrderListDTO;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.dto.paging.PagingResponseDTO;
import org.shop.domain.enumeration.MailSuffix;
import org.shop.domain.enumeration.PageAmount;
import org.shop.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RequestMapping("/my-page")
@Controller
@Slf4j
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    private final OrderService orderService;

    private final ResponseMappingService responseMappingService;

    private final PrincipalService principalService;

    private final CookieService cookieService;

    @GetMapping("/order")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String memberOrderList(@RequestParam(name = "term", required = false, defaultValue = "3") String term
            , @RequestParam(name = "page", required = false, defaultValue = "1") int page
            , Principal principal
            , Model model){
        OrderListRequestDTO orderDTO = new OrderListRequestDTO(term, page);
        ProductOrderListDTO dto = new ProductOrderListDTO(principal.getName());
        PagingResponseDTO<OrderListDTO> result = orderService.getMemberOrderList(dto, orderDTO);

        model.addAttribute("list", result);

        return "/main/nonOrderList";
    }

    @GetMapping("/review/post/{orderDetailId}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String postReview(@PathVariable("orderDetailId") long orderDetailId
                            , Principal principal
                            , Model model) {
        String userId = principalService.getUserIdToPrincipal(principal);
        MyPageReviewPostDTO dto = myPageService.getPostReviewData(orderDetailId, userId);

        model.addAttribute("detail", dto);

        return "myPage/insertReview";
    }

    // 관심상품
    @GetMapping("/like")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String likeList(@RequestParam(name = "page", required = false, defaultValue = "1") int page
                            , Principal principal
                            , Model model) {

        Criteria cri = new Criteria(page, PageAmount.LIKE_PRODUCT_AMOUNT.getAmount());
        String userId = principalService.getUserIdToPrincipal(principal);
        List<LikeListDTO> content = myPageService.getLikeList(cri, userId);
        int totalElements = myPageService.getLikeTotalElements(userId);
        PagingResponseDTO<LikeListDTO> result = responseMappingService.pagingResponseMapping(content, cri, totalElements);

        model.addAttribute("likeList", result);

        return "/myPage/likeList";
    }

    @GetMapping("/qna/product")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String productQnAList(@RequestParam(name = "page", required = false, defaultValue = "1") int page
                                , Principal principal
                                , Model model) {
        Criteria cri = new Criteria(page, PageAmount.DEFAULT_AMOUNT.getAmount());
        String userId = principalService.getUserIdToPrincipal(principal);
        List<MyPageProductQnAListDTO> content = myPageService.getProductQnAList(userId, cri);
        int totalElements = myPageService.getProductQnATotalElements(userId);
        PagingResponseDTO<MyPageProductQnAListDTO> result = responseMappingService.pagingResponseMapping(content, cri, totalElements);

        model.addAttribute("qna", result);

        return "/myPage/productQnAList";
    }

    @GetMapping("/qna/product/{qnaId}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String productQnADetail(@PathVariable("qnaId") long qnaId
                                    , Principal principal
                                    , Model model) {
        String userId = principalService.getUserIdToPrincipal(principal);
        MyPageProductQnADetailResponseDTO result = myPageService.getProductQnADetail(qnaId, userId);

        model.addAttribute("detail", result);

        return "/myPage/productQnADetail";
    }
    @GetMapping("/qna/member/insert")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String insertMemberQnA(Model model) {
        List<MyPageQnAClassificationDTO> dto = myPageService.getQnAClassification();

        model.addAttribute("classification", dto);

        return "/myPage/insertMemberQnA";
    }

    @GetMapping("/qna/member/patch/{qnaId}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String patchMemberQnA(@PathVariable("qnaId") long qnaId
                                , Principal principal
                                , Model model){
        log.info("patch MemberQnA");
        String userId = principalService.getUserIdToPrincipal(principal);
        MemberQnAPatchResponseDTO patchDTO = myPageService.getMemberQnAPatchData(qnaId, userId);
        List<MyPageQnAClassificationDTO> classificationDTO = myPageService.getQnAClassification();

        model.addAttribute("data", patchDTO);
        model.addAttribute("classification", classificationDTO);

        return "/myPage/patchMemberQnA";
    }

    @GetMapping("/qna/member")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String memberQnAList(@RequestParam(name = "page", required = false, defaultValue = "1") int page
                                , Principal principal
                                , Model model) {
        Criteria cri = new Criteria(page, PageAmount.DEFAULT_AMOUNT.getAmount());
        String userId = principalService.getUserIdToPrincipal(principal);
        List<MyPageMemberQnAListDTO> content = myPageService.getMemberQnAList(userId, cri);
        int totalElements = myPageService.getMemberQnATotalElements(userId);
        PagingResponseDTO<MyPageMemberQnAListDTO> result = responseMappingService.pagingResponseMapping(content, cri, totalElements);

        model.addAttribute("qna", result);

        return "/myPage/memberQnAList";
    }

    @GetMapping("/qna/member/{qnaId}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String memberQnADetail(@PathVariable("qnaId") long qnaId
                                , Principal principal
                                , Model model) {
        String userId = principalService.getUserIdToPrincipal(principal);
        MyPageMemberQnADetailResponseDTO result = myPageService.getMemberQnADetail(qnaId, userId);

        model.addAttribute("detail", result);

        return "/myPage/memberQnADetail";
    }

    @GetMapping("/review")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String reviewList(@RequestParam(name = "page", required = false, defaultValue = "1") int page
                            , Principal principal
                            , Model model) {
        Criteria cri = new Criteria(page, PageAmount.DEFAULT_AMOUNT.getAmount());
        String userId = principalService.getUserIdToPrincipal(principal);
        List<MyPageReviewListDTO> content = myPageService.getReviewList(userId, cri);
        int totalElements = myPageService.getReviewTotalElements(userId);
        PagingResponseDTO<MyPageReviewListDTO> result = responseMappingService.pagingResponseMapping(content, cri, totalElements);

        model.addAttribute("list", result);

        return "/myPage/memberReviewList";
    }

    @GetMapping("/review/patch/{reviewId}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String patchReview(@PathVariable("reviewId") long reviewId
                                , Principal principal
                                , Model model) {
        String userId = principalService.getUserIdToPrincipal(principal);
        MyPageReviewPatchDTO dto = myPageService.getPatchReviewData(reviewId, userId);

        model.addAttribute("data", dto);

        return "/myPage/memberReviewModify";
    }

    @GetMapping("/member/info")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String getMemberInfoPage(Principal principal, Model model) {
        String userId = principalService.getUserIdToPrincipal(principal);

        model.addAttribute("uid", userId);

        return "/myPage/modifyCheck";
    }

    @GetMapping("/member/patch-info")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String getPatchMemberInfo(Principal principal, Model model, HttpServletRequest request) {
        String userId = principalService.getUserIdToPrincipal(principal);
        cookieService.checkMemberCheckCookie(userId, request);
        MyPageMemberInfoDTO result = myPageService.getMemberInfo(userId);

        model.addAttribute("data", result);
        model.addAttribute("mailSuffix", MailSuffix.values());

        return "/myPage/modifyInfo";
    }

}
