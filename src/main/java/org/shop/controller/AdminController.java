package org.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.ResultProperties;
import org.shop.domain.dto.admin.*;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.dto.paging.PageDTO;
import org.shop.domain.entity.*;
import org.shop.mapper.AdminMapper;
import org.shop.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RequestMapping("/admin/")
@Controller
@Log4j
@RequiredArgsConstructor
public class AdminController {

    private final AdminMapper adminMapper;

    private final AdminService adminService;

    //상품 목록
    @GetMapping("/productList")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void productList(Model model, Criteria cri) {
        if(cri.getKeyword() == "")
            cri.setKeyword(null);

        if(cri.getClassification() == "")
            cri.setClassification(null);

        model.addAttribute("pList", adminMapper.productList(cri));
        int total = adminMapper.getProductTotal(cri);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }

    //상품 추가 페이지
    @GetMapping("/addProduct")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addProduct(){

    }

    //상품 추가 처리
    @PostMapping("/addProduct")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public String insertProduct(ProductInsertDTO dto,
                              @RequestParam("firstThumbFile") MultipartFile firstFiles,
                              @RequestParam(value = "thumbFiles", required = false) List<MultipartFile> thumbFiles,
                              @RequestParam("infoFiles") List<MultipartFile> infoFiles) {

        try{
            return adminService.addProduct(dto, firstFiles, thumbFiles, infoFiles);
        }catch (Exception e){
            return ResultProperties.ERROR;
        }
    }

    //상품 수정 처리
    @PatchMapping("/modifyProduct")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public String modifyProductInfo(ProductModifyDTO dto,
                                  @RequestParam(value = "firstThumbFile", required = false) MultipartFile firstFile,
                                  @RequestParam(value = "thumbFiles", required = false) List<MultipartFile> thumbFiles,
                                  @RequestParam(value = "infoFiles", required = false) List<MultipartFile> infoFiles,
                                  @RequestParam(value = "deleteFirstThumbFile", required = false) String deleteFirstThumbFile,
                                  @RequestParam(value = "deleteThumbFiles", required = false) List<String> deleteThumbFiles,
                                  @RequestParam(value = "deleteInfoFiles", required = false) List<String> deleteInfoFiles) {
        try{
            return adminService.modifyProduct(dto, firstFile, thumbFiles, infoFiles,
                    deleteFirstThumbFile, deleteThumbFiles, deleteInfoFiles);
        }catch (Exception e){
            return ResultProperties.ERROR;
        }
    }

    //대표 썸네일 데이터
    @GetMapping("/getFirstThumb")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public ResponseEntity<List<ProductFirstThumbnailDTO>> getFirstThumb(String pno){

        return new ResponseEntity<>(adminMapper.getFirstThumb(pno), HttpStatus.OK);
    }

    //대표 썸네일을 제외한 나머지 썸네일 데이터 리스트
    @GetMapping("/getThumbnail")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public ResponseEntity<List<ProductThumbnailDTO>> getThumbnail(String pno){

        return new ResponseEntity<>(adminMapper.getThumbnail(pno), HttpStatus.OK);
    }

    //상품 정보 이미지 데이터 리스트
    @GetMapping("/getInfoImage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public ResponseEntity<List<ProductInfoImageDTO>> getInfoImg(String pno){

        return new ResponseEntity<>(adminMapper.getInfoImg(pno), HttpStatus.OK);
    }

    //상품 상세 정보 페이지(수정 및 옵션 추가 기능이 존재)
    @GetMapping("/productInfo/{pOpNo}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String productInfo(@PathVariable("pOpNo") String pOpNo, Model model){

        model.addAttribute("info", adminMapper.productInfo(pOpNo));

        return "admin/productInfo";
    }

    //상품 옵션 추가 처리
    @PostMapping("/addProductOp")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProductOp(ProductOpInsertDTO dto) {
        adminService.addProductOp(dto);

        return "redirect:/admin/productList";
    }

    //상품 옵션(1개) 비공개 처리
    @PostMapping("/closedProductOp")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public void closedProductOp(String pOpNo){

        adminMapper.closedProductOp(pOpNo);
    }

    //상품 옵션(1개) 공개 처리
    @PostMapping("/openProductOp")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public void openProductOp(String pOpNo){

        adminMapper.openProductOp(pOpNo);
    }

    //상품 비공개 처리
    @PostMapping("/closedProduct")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public void closedProduct(String pno){

        adminMapper.closedProduct(pno);
    }

    //상품 공개 처리
    @PostMapping("/openProduct")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public void openProduct(String pno){

        adminMapper.openProduct(pno);
    }

    //주문 목록 페이지
    @GetMapping("/orderList")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void orderList(Model model, Criteria cri){


        model.addAttribute("order", adminMapper.orderList(cri));
        int total = adminMapper.getOrderTotal(cri);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }

    //주문 상세 정보 데이터
    @GetMapping("/orderInfo/{orderNo}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    public ResponseEntity<AdminProductOrderDTO> orderInfo(@PathVariable("orderNo") String orderNo){

        return new ResponseEntity<>(adminMapper.orderInfo(orderNo), HttpStatus.OK);
    }

    //주문 정보 데이터 리스트
    @GetMapping("/orderInfoTable")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @ResponseBody
    public ResponseEntity<List<AdminOrderInfoTableDTO>> orderInfoTable(String orderNo){

        return new ResponseEntity<>(adminMapper.orderInfoTable(orderNo), HttpStatus.OK);
    }

    //주문 상품 배송 처리(관리자의 확인 처리)
    @PatchMapping("/shippingProcess")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public String shippingProcess(@RequestBody Map<String, String> orderNoMap){

        String orderNo = orderNoMap.get("orderNo");
        adminMapper.shippingProcessing(orderNo);

        return String.valueOf(adminMapper.checkOrderStat(orderNo));
    }

    //사용자 문의 리스트 페이지
    @GetMapping("/adminQnAList")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void adminQnAList(Model model, Criteria cri){

        model.addAttribute("list", adminMapper.adminQnAList(cri));
        int total = adminMapper.getAdminQnATotal(cri);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }

    //사용자 문의 상세 페이지
    @GetMapping("/adminQnADetail/{qno}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminQnADetail(@PathVariable("qno") int qno, Model model){

        model.addAttribute("aqDetail", adminMapper.adminQnADetail(qno));

        return "admin/adminQnADetail";
    }

    //사용자 문의 답변 완료 처리
    @PatchMapping("/adminQnAComplete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public String adminQnAComplete(@RequestBody Map<String, Integer> qnoMap){
        int qno = qnoMap.get("qno");
        adminMapper.adminQnAComplete(qno);

        return String.valueOf(adminMapper.adminQnACheck(qno));
    }

    //사용자 문의 상세 페이지의 댓글(관리자의 답변) 데이터 리스트
    @GetMapping("/getQnAReply")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public ResponseEntity<List<MyQnAReplyGetDTO>> getQnAReply(int qno){

        return new ResponseEntity<>(adminMapper.adminQnAReplyList(qno), HttpStatus.OK);
    }

    //사용자 문의 상세 페이지의 댓글(관리자의 답변) 처리
    @PostMapping("/QnAReply")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public String qnAReply(MyQnAReplyDTO dto, Principal principal){

        return adminService.qnAReplyProc(dto, principal);
    }

    //사용자 문의 상세 페이지의 댓글(관리자의 답변) 삭제 처리
    @DeleteMapping("/QnAReplyDel")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public String qnAReplyDel(int replyNo){

        return adminService.qnaReplyDelete(replyNo);
    }

    //사용자(회원) 데이터
    @GetMapping("/userInfo/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<MemberInfoDTO> userInfo(@PathVariable("userId") String userId){

        return new ResponseEntity<>(adminMapper.userInfo(userId), HttpStatus.OK);
    }

    //사용자(회원) 목록
    @GetMapping("/userList")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void userList(Model model, Criteria cri){
        List<MemberListDTO> dto = adminMapper.userList(cri);

        log.info("dto : " + dto);

        model.addAttribute("uList", dto);
        int total = adminMapper.getUserTotal(cri);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }

    //상품별 매출 페이지
    @GetMapping("/salesProductList")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void salesProductList(Model model, Criteria cri){
        model.addAttribute("spList", adminMapper.salesProductList(cri));
        int total = adminMapper.getSalesProductTotal(cri);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }

    //기간별 매출 페이지
    @GetMapping("/salesTermList")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void salesTermList(Model model, Criteria cri){
        model.addAttribute("stList", adminMapper.salesTermList(cri));
        int total = adminMapper.getSalesTermTotal(cri);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }

    //기간별 매출 페이지의 존재하는 연도 데이터 리스트
    @GetMapping("/salesTermSelect")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Sales>> salesTermSelect(){

        return new ResponseEntity<>(adminMapper.salesTermSelect(), HttpStatus.OK);
    }

    //상품 문의 목록 페이지
    @GetMapping("/productQnAList")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    public void productQnAList(Model model, Criteria cri){
        model.addAttribute("list", adminMapper.productQnAList(cri));
        int total = adminMapper.getProductQnATotal(cri);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }

    //상품 문의 상세 페이지
    @GetMapping("/productQnADetail/{pQnANo}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    public String productQnADetail(@PathVariable("pQnANo") long pQnANo, Model model){
        model.addAttribute("detail", adminMapper.productQnADetail(pQnANo));

        return "admin/productQnADetail";
    }

    //상품 문의 답글 작성 처리
    @PostMapping("/productQnAReply")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    public String replyProductQnA(ProductQnAReplyDTO dto, Principal principal){
        adminService.productQnAReplyProc(dto, principal);

        return "redirect:/admin/productQnAList";
    }
}
