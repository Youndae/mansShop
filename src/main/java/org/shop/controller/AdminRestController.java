package org.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.dto.admin.member.in.AdminMemberPointRequestDTO;
import org.shop.domain.dto.admin.member.out.AdminMemberDetailDTO;
import org.shop.domain.dto.admin.product.in.AdminPatchDiscountDTO;
import org.shop.domain.dto.admin.product.in.AdminProductImageDTO;
import org.shop.domain.dto.admin.product.in.AdminProductDeleteImageDTO;
import org.shop.domain.dto.admin.product.in.AdminProductPatchDTO;
import org.shop.domain.dto.admin.product.out.AdminDiscountProductDTO;
import org.shop.domain.dto.admin.product.out.AdminDiscountSelectProductDTO;
import org.shop.domain.dto.admin.order.out.AdminOrderDetailDTO;
import org.shop.domain.dto.admin.sales.business.AdminPeriodClassificationDTO;
import org.shop.domain.dto.admin.sales.out.AdminClassificationSalesResponseDTO;
import org.shop.domain.dto.admin.sales.out.AdminPeriodSalesResponseDTO;
import org.shop.domain.dto.myPage.qna.in.QnAReplyRequestDTO;
import org.shop.domain.enumeration.Result;
import org.shop.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@Slf4j
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminRestController {

    private final AdminService adminService;

    @PostMapping(value = "/product", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> insertProduct(@RequestParam(value = "firstThumbnail", required = false)MultipartFile firstThumbnail
                                                , @RequestParam(value = "thumbnails", required = false) List<MultipartFile> thumbnails
                                                , @RequestParam(value = "infoImages", required = false) List<MultipartFile> infoImages
                                                , @ModelAttribute AdminProductPatchDTO dto) {
        AdminProductImageDTO imageDTO = AdminProductImageDTO.builder()
                                                            .firstThumbnail(firstThumbnail)
                                                            .thumbnails(thumbnails)
                                                            .infoImages(infoImages)
                                                            .build();

        String result = adminService.insertProduct(imageDTO, dto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping(value = "/product/{productId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> patchProduct(@PathVariable("productId") String productId
                                                , @RequestParam(value = "deleteOptionIds", required = false) List<Long> deleteOptionIds
                                               , @RequestParam(value = "firstThumbnail", required = false)MultipartFile firstThumbnail
                                               , @RequestParam(value = "thumbnails", required = false) List<MultipartFile> thumbnails
                                               , @RequestParam(value = "infoImages", required = false) List<MultipartFile> infoImages
                                               , @ModelAttribute AdminProductPatchDTO patchDTO
                                               , @ModelAttribute AdminProductDeleteImageDTO deleteImageDTO) {

        AdminProductImageDTO imageDTO = AdminProductImageDTO.builder()
                                                            .firstThumbnail(firstThumbnail)
                                                            .thumbnails(thumbnails)
                                                            .infoImages(infoImages)
                                                            .build();

        /*
            SpringMVC에서는 MultipartFile을 DTO에 매핑하는 것이 잘 동작하지 않을 수도 있다.
            @ModelAttribute의 경우 주로 일반 문자열 및 숫자 타입의 formData를 DTO로 매핑하는데 사용된다.
            MultipartFile은 HTTP Request body에서 binary data를 처리하기 때문에 이 방식으로 매핑하는데 어려움이 있다.
            @ModelAttribute가 사용되면 Spring은 해당 DTO의 필드에 대한 값을 URL 쿼리 문자열 또는 form field에서 찾으려고 한다.
            그러나 MultipartFile은 이러한 방식으로 처리되지 않기 때문에 DTO에 올바르게 매핑되지 않을 수 있다.

            SpringBoot에서는 Jackson과 통합되어 JSON 데이터처리에 Jacson 라이브러리를 사용한다.
            그래서 DTO와 JSON의 변환을 자동으로 처리하기 때문에 DTO에 파일과 다른 데이터가 혼합되어 있을 때 더 매끄럽게 작동할 수 있는 것이다.
            SpringMVC에서는 이러한 통합이 자동으로 이루어지지 않기때문에 MultipartFile과 DTO의 조합이 덜 직관적일 수 있다.

            SpringBoot에서 ModelAttribute는 HTTP 요청을 처리할 때 파일 업로드와 다른 데이터 처리를 통합적으로 다룰 수 있는 지원을 제공한다.
            그러나 SpringMVC에서는 이러한 처리의 자동화 지원이 부족하기 떄문에 개발자가 수작업을 통해 처리하게 된다.

            SpringBoot의 Controller에서 @ModelAttribute는 MultipartFile과 일반 필드들을 함께 처리하는데 있어서 더 자연스럽게 동작하며,
            MultipartFile을 포함한 DTO를 수용할 수 있는 방식으로 설계되어 있다.
            또한, MultipartResolver를 구성하여 파일 업로드를 지원하기 때문에 SpringMVC보다 더 쉽게 파일 업로드를 설정할 수 있다.
            Spring MVC에서는 MultipartResolver를 수동으로 설정해야 하며 이 과정에서 발생할 수 있는 문제에 대한 책임이 개발자에게 있다.
         */

        String result = adminService.patchProduct(productId, deleteOptionIds, imageDTO, deleteImageDTO, patchDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/product/discount/category/{classificationId}")
    public ResponseEntity<List<AdminDiscountProductDTO>> getProductListOfClassification(@PathVariable("classificationId") String classificationId) {

        List<AdminDiscountProductDTO> result = adminService.getProductListOfClassification(classificationId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/product/discount/select/{productId}")
    public ResponseEntity<AdminDiscountSelectProductDTO> getDiscountProductData(@PathVariable("productId") String productId) {
        AdminDiscountSelectProductDTO result = adminService.getDiscountProductData(productId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/product/discount")
    public ResponseEntity<String> patchDiscount(@RequestBody AdminPatchDiscountDTO discountDTO) {
        String result = adminService.patchDiscountProduct(discountDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/order/detail/{orderId}")
    public ResponseEntity<AdminOrderDetailDTO> getOrderDetail(@PathVariable("orderId") long orderId) {

        AdminOrderDetailDTO result = adminService.getOrderDetail(orderId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/order/order-check/{orderId}")
    public ResponseEntity<String> patchOrderCheck(@PathVariable("orderId") long orderId) {
        String result = adminService.patchOrderStatus(orderId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/qna/product/reply")
    public ResponseEntity<String> postProductQnAReply(@RequestBody QnAReplyRequestDTO replyDTO
                                                    , Principal principal) {
        String result = adminService.postProductQnAReply(replyDTO, principal);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PatchMapping("/qna/product/reply/{replyId}")
    public ResponseEntity<String> patchProductQnAReply(@PathVariable("replyId") long replyId
                                                    , @RequestBody Map<String, String> content
                                                    , Principal principal) {
        QnAReplyRequestDTO replyDTO = new QnAReplyRequestDTO(replyId, content);
        String result = adminService.patchProductQnAReply(replyDTO);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PatchMapping("/qna/product/{qnaId}")
    public ResponseEntity<String> patchProductQnAStatus(@PathVariable("qnaId") long qnaId) {

        String result = adminService.patchProductQnAStatusToComplete(qnaId);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/qna/member/reply")
    public ResponseEntity<String> postMemberQnAReply(@RequestBody QnAReplyRequestDTO replyDTO
                                                    , Principal principal) {
        String result = adminService.postMemberQnAReply(replyDTO, principal);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PatchMapping("/qna/member/reply/{replyId}")
    public ResponseEntity<String> patchMemberQnAReply(@PathVariable("replyId") long replyId
                                                    , @RequestBody Map<String, String> content) {

        QnAReplyRequestDTO replyDTO = new QnAReplyRequestDTO(replyId, content);
        String result = adminService.patchMemberQnAReply(replyDTO);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PatchMapping("/qna/member/{qnaId}")
    public ResponseEntity<String> patchMemberQnAStatus(@PathVariable("qnaId") long qnaId) {

        String result = adminService.patchMemberQnAStatusToComplete(qnaId);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/qna/classification")
    public ResponseEntity<String> postQnAClassification(@RequestBody Map<String, String> content) {
        if(content != null){
            String result = adminService.postQnAClassification(content.get("content"));

            return new ResponseEntity<>(null, HttpStatus.OK);
        }else
            return new ResponseEntity<>(Result.FAIL.getResultKey(), HttpStatus.OK);

    }

    @DeleteMapping("/qna/classification/{classificationId}")
    public ResponseEntity<String> deleteQnAClassification(@PathVariable("classificationId") long classificationId) {
        String result = adminService.deleteQnAClassification(classificationId);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/review/reply/{reviewId}")
    public ResponseEntity<String> postReviewReply(@PathVariable("reviewId") long reviewId
                                                , @RequestBody Map<String, String> content) {
        String result = adminService.postReviewReply(reviewId, content.get("content"));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/member/{userId}")
    public ResponseEntity<?> getMemberData(@PathVariable("userId") String userId) {

        AdminMemberDetailDTO result = adminService.getMemberDetail(userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/member/point")
    public ResponseEntity<String> patchPoint(@RequestBody AdminMemberPointRequestDTO dto) {
        String result = adminService.patchPoint(dto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     *
     * @param term = YYYY-MM
     * @param classification
     */
    @GetMapping("/sales/period/detail/classification")
    public ResponseEntity<AdminClassificationSalesResponseDTO> getPeriodSalesByClassification(@RequestParam(value = "term") String term
                                                                                            , @RequestParam(value = "classification") String classification) {

        AdminClassificationSalesResponseDTO content = adminService.getPeriodClassificationList(term, classification);

        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    /**
     *
     * @param term = YYYY-MM-dd
     */
    @GetMapping("/sales/period/detail/day")
    public ResponseEntity<AdminPeriodSalesResponseDTO<AdminPeriodClassificationDTO>> getPeriodSalesDailyDetail(@RequestParam(value = "term") String term) {

        AdminPeriodSalesResponseDTO<AdminPeriodClassificationDTO> content = adminService.getPeriodSalesByDay(term);

        return new ResponseEntity<>(content, HttpStatus.OK);
    }
}
