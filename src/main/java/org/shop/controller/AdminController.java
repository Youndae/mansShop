package org.shop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.*;
import org.shop.mapper.AdminMapper;
import org.shop.service.AdminService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping("/admin/")
@Controller
@Log4j
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private AdminMapper adminMapper;

    private AdminService adminService;

    @GetMapping("/productList")
    public void productList(Model model, Criteria cri) throws Exception{
        //상품 목록
        log.info("pList");

        if(cri.getKeyword() == ""){
            cri.setKeyword(null);
        }

        if(cri.getClassification() == ""){
            cri.setClassification(null);
        }

        log.info("controller cri : " + cri);

        adminMapper.productList(cri).forEach(product -> log.info(product));

        model.addAttribute("pList", adminMapper.productList(cri));

        int total = adminMapper.getProductTotal(cri);
        log.info("Product Total : " + total);

        model.addAttribute("pageMaker", new PageDTO(cri, total));


    }

    @GetMapping("/addProduct")
    public void addProduct(){
        //상품 추가
    }

    @PostMapping("/addProduct")
    @ResponseBody
    public void insertProduct(ProductOpVO opVO, ThumbnailVO thumbnailVO, ProductImgVO imgVO,
                                @RequestParam("firstThumbFile") List<MultipartFile> firstFiles,
                                @RequestParam("thumbFiles") List<MultipartFile> thumbFiles,
                                @RequestParam("infoFiles") List<MultipartFile> infoFiles
                              ) throws Exception{





/*        log.info("opVO : " + opVO);

        log.info(opVO.getPClassification());
        log.info(opVO.getPName());

        log.info("firstfile length : " + firstFiles.size());
        log.info("thumbfile length : " + thumbFiles.size());
        log.info("infofile length : " + infoFiles.size());

        for(MultipartFile image : firstFiles){
            log.info("first img name : " + image.getOriginalFilename());
        }

        for(MultipartFile image : thumbFiles){
            log.info("thumbfile name : " + image.getOriginalFilename());
        }

        for(MultipartFile image : infoFiles){
            log.info("infofile Name : " + image.getOriginalFilename());
        }*/

        /*opVO.setPOpNo("7번상품옵션");
        opVO.setPno("7번상품");
        opVO.setFirstThumbnail("6번사진");*/

        /*adminMapper.addProduct(opVO);
        adminMapper.addProductOp(opVO);*/

        adminService.addProduct(opVO, thumbnailVO, imgVO, firstFiles, thumbFiles, infoFiles);

        log.info("insert complete");

    }

    @PostMapping("/modifyProduct")
    @ResponseBody
    public void modifyProductInfo(ProductOpVO productOpVO, ProductImgVO productImgVO, ThumbnailVO thumbnailVO,
                              @RequestParam("firstThumbFile") List<MultipartFile> firstFile,
                              @RequestParam("thumbFiles") List<MultipartFile> thumbFiles,
                              @RequestParam("infoFiles") List<MultipartFile> infoFiles,
                              @RequestParam(value = "deleteFirstThumbFile", required = false) List<String> deleteFirstThumbFile,
                              @RequestParam(value = "deleteThumbFiles", required = false) List<String> deleteThumbFiles,
                              @RequestParam(value = "deleteInfoFiles", required = false) List<String> deleteInfoFiles) throws Exception{

        //상품정보수정



        /*log.info("opVO : " + productOpVO);
        log.info("classification : " + productOpVO.getPClassification());
        log.info("pName : " + productOpVO.getPName());

        log.info("firstFile size : " + firstFile.size());
        log.info("thumbFiles size : " + thumbFiles.size());
        log.info("infoFiles size : " + infoFiles.size());
        log.info("deleteFirstThumbFile size : " + deleteFirstThumbFile.size());
        log.info("deleteThumbFiles size : " + deleteThumbFiles.size());
        log.info("deleteInfoFiles size : " + deleteInfoFiles.size());


        for(MultipartFile image : firstFile){
            log.info("firstThumb file : " + image.getOriginalFilename());
        }

        for(MultipartFile image : thumbFiles){
            log.info("thumbFiles file : " + image.getOriginalFilename());
        }

        for(MultipartFile image : infoFiles){
            log.info("infoFiles file : " + image.getOriginalFilename());
        }

        for(String image : deleteFirstThumbFile){
            log.info("deleteFirstThumbFile file : " + image);
        }

        for(String image : deleteThumbFiles){
            log.info("deleteThumbFiles file : " + image);
        }

        for(String image : deleteInfoFiles){
            log.info("deleteInfoFiles file : " + image);
        }*/


        log.info("controller size check : " + deleteFirstThumbFile == null);


        adminService.modifyProduct(productOpVO, thumbnailVO, productImgVO,
                                    firstFile, thumbFiles, infoFiles,
                                    deleteFirstThumbFile, deleteThumbFiles, deleteInfoFiles);

        log.info("complete");


    }

    @GetMapping("/getFirstThumb")
    @ResponseBody
    public ResponseEntity<List<ProductVO>> getFirstThumb(String pno){
        log.info("getFirstThumb : " + pno);

        return new ResponseEntity<>(adminMapper.getFirstThumb(pno), HttpStatus.OK);
    }

    @GetMapping("/getThumbnail")
    @ResponseBody
    public ResponseEntity<List<ThumbnailVO>> getThumbnail(String pno){
        log.info("getThumbnail : " + pno);
        log.info("return data : " + new ResponseEntity<>(adminMapper.getThumbnail(pno), HttpStatus.OK));

        return new ResponseEntity<>(adminMapper.getThumbnail(pno), HttpStatus.OK);
    }

    @GetMapping("/getInfoImage")
    @ResponseBody
    public ResponseEntity<List<ProductImgVO>> getInfoImg(String pno){
        log.info("get InfoImg : " + pno);

        return new ResponseEntity<>(adminMapper.getInfoImg(pno), HttpStatus.OK);
    }

    @GetMapping("/display")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(String image){
        log.info("thumb : " + image);

        File file = new File("E:\\upload\\Product\\" + image);

        log.info("file : " + file);

        ResponseEntity<byte[]> result = null;

        try{
            HttpHeaders header = new HttpHeaders();

            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/productInfo/{pOpNo}")
    public String productInfo(@PathVariable("pOpNo") String pOpNo, Model model){
        //상품 정보

        log.info("pno : " + pOpNo);

        model.addAttribute("info", adminMapper.productInfo(pOpNo));

        log.info("log : " + adminMapper.productInfo(pOpNo));

        return "admin/productInfo";
    }

    @PostMapping("/addProductOp")
    public String addProductOp(ProductOpVO productOpVO) throws Exception{
        //상품 옵션 추가

        adminService.addProductOp(productOpVO);


        log.info(productOpVO);

        return "redirect:/admin/productList";
    }

    @PostMapping("/closedProductOp")
    @ResponseBody
    public void closedProductOp(String pOpNo){
        //상품 옵션 비공개
        log.info("Closed ProductOp");
        log.info("pOpNo : " + pOpNo);
        adminMapper.closedProductOp(pOpNo);
    }

    @PostMapping("/openProductOp")
    @ResponseBody
    public void openProductOp(String pOpNo){
        //상품 옵션 공개
        log.info("Opened ProductOp");
        log.info("Open pOpNo : " + pOpNo);
        adminMapper.openProductOp(pOpNo);
    }

    @PostMapping("/closedProduct")
    @ResponseBody
    public void closedProduct(String pno){
        //상품 비공개
        log.info("Closed Product");
        log.info("closed pno : " + pno);
        adminMapper.closedProduct(pno);

    }

    @PostMapping("/openProduct")
    @ResponseBody
    public void openProduct(String pno){
        //상품 공개
        log.info("Opened Product");
        log.info("open pno : " + pno);
        adminMapper.openProduct(pno);

    }


    @GetMapping("/orderList")
    public void orderList(Model model, Criteria cri){
        //주문 목록
        //모든 주문 목록 출력

        log.info("orderList keyword : " + cri.getKeyword());

        model.addAttribute("order", adminMapper.orderList(cri));

        int total = adminMapper.getOrderTotal(cri);
        log.info("Product Total : " + total);

        model.addAttribute("pageMaker", new PageDTO(cri, total));

    }

    @GetMapping("/orderInfo/{orderNo}")
    public ResponseEntity<OrderVO> orderInfo(@PathVariable("orderNo") String orderNo){

        log.info("orderInfo orderNo : " + orderNo);

        return new ResponseEntity<>(adminMapper.orderInfo(orderNo), HttpStatus.OK);
    }

    @GetMapping("/orderInfoTable")
    @ResponseBody
    public ResponseEntity<List<OrderDetailVO>> orderInfoTable(String orderNo){

        log.info("orderInfoTable : " + orderNo);

        return new ResponseEntity<>(adminMapper.orderInfoTable(orderNo), HttpStatus.OK);
    }


    @PostMapping("/shippingProcess")
    @ResponseBody
    public int shippingProcess(String orderNo){
        //배송처리
        log.info("orderNo : " + orderNo);

        adminMapper.shippingProcessing(orderNo);

        return adminMapper.checkOrderStat(orderNo);
    }

    @GetMapping("/adminQnAList")
    public void adminQnAList(Model model, Criteria cri){
        //QnA 리스트.
        //상품 QnA가 아닌 회원 문의 리스트트

        model.addAttribute("list", adminMapper.adminQnAList(cri));

        int total = adminMapper.getAdminQnATotal(cri);
        log.info("Product Total : " + total);

        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }

    @GetMapping("/adminQnADetail/{qno}")
    public String adminQnADetail(@PathVariable("qno") int qno, Model model){
        //QnA Detail

        log.info("Detail qno : " + qno);

        model.addAttribute("aqDetail", adminMapper.adminQnADetail(qno));

        return "admin/adminQnADetail";
    }

    @PostMapping("/adminQnAComplete")
    @ResponseBody
    public int adminQnAComplete(int qno){
        //QnA 답변 완료 처리
        log.info("QnAComplete");

        adminMapper.adminQnAComplete(qno);

        return adminMapper.adminQnACheck(qno);
    }

    @GetMapping("/getQnAReply")
    @ResponseBody
    public ResponseEntity<List<MyQnAReplyVO>> getQnAReply(int qno){
        // QnA 댓글 처리
        // MyPageController에도 동일하게 있는데
        // 거기서 한번에 처리하도록 할지 admin과 user를 나눠서 처리할지 고민.

        log.info("controller qno : " + qno);
        log.info("ReplyList");

        return new ResponseEntity<>(adminMapper.adminQnAReplyList(qno), HttpStatus.OK);
    }

    @PostMapping("/QnAReply")
    @ResponseBody
    public void QnAReply(MyQnAReplyVO myQnAReplyVO, Principal principal){

        String id = principal.getName();

        myQnAReplyVO.setUserId(id);

        log.info("QnAReply Controller : " + myQnAReplyVO);

        adminMapper.adminQnAReply(myQnAReplyVO);

    }

    @PostMapping("/QnAReplyDel")
    @ResponseBody
    public void QnAReplyDel(int replyNo){
        log.info("controller rno : " + replyNo);

        adminMapper.QnAReplyDelete(replyNo);
    }

    @GetMapping("/userInfo/{userId}")
    public ResponseEntity<MemberVO> userInfo(@PathVariable("userId") String userId){
        //회원 정보
        log.info("get user Info controller : " + userId);


        return new ResponseEntity<>(adminMapper.userInfo(userId), HttpStatus.OK);
    }


    @GetMapping("/userList")
    public void userList(Model model, Criteria cri){
        //회원 목록
        model.addAttribute("uList", adminMapper.userList(cri));

        int total = adminMapper.getUserTotal(cri);
        log.info("Product Total : " + total);

        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }

    @GetMapping("/salesProductList")
    public void salesProductList(Model model, Criteria cri){
        //상품별 매출 목록

        log.info("salesProductList cri : " + cri);

        model.addAttribute("spList", adminMapper.salesProductList(cri));

        int total = adminMapper.getSalesProductTotal(cri);
        log.info("salesProduct Total : " + total);

        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }

    @GetMapping("/salesTermList")
    public void salesTermList(Model model, Criteria cri){
        //기간별 매출 목록

        log.info("salesTermList cri : " + cri);

        model.addAttribute("stList", adminMapper.salesTermList(cri));

        int total = adminMapper.getSalesTermTotal(cri);

        log.info("salesTermList Total : " + total);

        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }

    @GetMapping("/salesTermSelect")
    public ResponseEntity<List<SalesVO>> salesTermSelect(){
        log.info("salesTerm Select Option List");
        return new ResponseEntity<>(adminMapper.salesTermSelect(), HttpStatus.OK);
    }

    @GetMapping("/productQnAList")
    public void productQnAList(Model model, Criteria cri){

        model.addAttribute("list", adminMapper.productQnAList(cri));

        int total = adminMapper.getProductQnATotal(cri);

        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }

    @GetMapping("/productQnADetail/{pQnANo}")
    public String productQnADetail(@PathVariable("pQnANo") long pQnANo, Model model){

        model.addAttribute("detail", adminMapper.productQnADetail(pQnANo));

        return "admin/productQnADetail";
    }

    @PostMapping("/productQnAReply")
    public String replyProductQnA(ProductQnAVO productQnAVO, Principal principal){
        //상품 문의 답글 작성 처리

        productQnAVO.setUserId(principal.getName());

        log.info("productQnAVO : " + productQnAVO);

        adminMapper.productQnAReply(productQnAVO);

        return "redirect:/admin/productQnAList";
    }
}
