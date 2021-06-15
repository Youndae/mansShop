package org.shop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.ProductImgVO;
import org.shop.domain.ProductOpVO;
import org.shop.domain.ProductVO;
import org.shop.domain.ThumbnailVO;
import org.shop.mapper.AdminMapper;
import org.shop.service.AdminService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

@RequestMapping("/admin/")
@Controller
@Log4j
@AllArgsConstructor
public class AdminController {

    private AdminMapper adminMapper;

    private AdminService adminService;

    @GetMapping("/productList")
    /*@PreAuthorize("hasRole('ROLE_ADMIN')")*/
    public void productList(Model model) throws Exception{
        //상품 목록
        log.info("pList");

        adminMapper.pList().forEach(product -> log.info(product));

        model.addAttribute("pList", adminMapper.pList());
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

    @GetMapping("/getAttachList")
    @ResponseBody
    public ResponseEntity<List<ProductVO>> getAttachList(){
        log.info("getAttachList");

        return new ResponseEntity<>(adminMapper.getAttachList(), HttpStatus.OK);
    }

    @GetMapping("/display")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(String firstThumbnail){
        log.info("thumb : " + firstThumbnail);

        File file = new File("E:\\upload\\Product\\" + firstThumbnail);

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

    @GetMapping("/productInfo/{pno}")
    public String productInfo(@PathVariable("pno") String pno, Model model){
        //상품 정보

        log.info("pno : " + pno);

        model.addAttribute("info", adminMapper.productInfo(pno));

        log.info("log : " + adminMapper.productInfo(pno));

        return "admin/productInfo";
    }

    @PostMapping("/modifyProductInfo")
    public void modifyProductInfo(){
        //상품 정보 수정
    }

    @PostMapping("/addProductOp")
    public void addProductOp(){
        //상품 옵션 추가
    }

    @GetMapping("/orderList")
    public void orderList(){
        //주문 목록
        //모든 주문 목록 출력
    }

    @PostMapping("/shippingProcess")
    public void shippingProcess(){
        //배송처리
    }

    @PostMapping("/orderPacking")
    public void orderPacking(){
        //상품 포장 처리
        //이건 shippingProcess랑 합쳐서 할지 따로 할지 좀 더 고민.
    }

    @GetMapping("/orderDetail")
    public void orderDetail(){
        //주문 상세 정보
    }

    @GetMapping("/adminQnAList")
    public void adminQnAList(){
        //QnA 리스트.
        //상품 QnA가 아닌 회원 문의 리스트트
    }

    @GetMapping("/adminQnAList/{qno}")
    public void adminQnADetail(){
        //QnA Detail
    }

    @PostMapping("/adminQnAComplete")
    public void adminQnAComplete(){
        //QnA 답변 완료 처리
    }

    @PostMapping("/adminQnAReply")
    public void adminQnAReply(){
        // QnA 댓글 처리
        // MyPageController에도 동일하게 있는데
        // 거기서 한번에 처리하도록 할지 admin과 user를 나눠서 처리할지 고민.
    }

    @GetMapping("/userInfo")
    public void userInfo(){
        //회원 정보
    }

    @GetMapping("/userList")
    public void userList(){
        //회원 목록
    }

    @GetMapping("/salesProductList")
    public void salesProductList(){
        //상품별 매출 목록
    }

    @GetMapping("/salesTermList")
    public void salesTermList(){
        //기간별 매출 목록
    }

    @GetMapping("/replyProductQnA")
    public void getReplyProductQnA(){
        //상품 문의 답글 작성 페이지
        //admin한테만 답글 작성 권한이 있으므로 여기서 처리
    }

    @PostMapping("/replyProductQnA")
    public void replyProductQnA(){
        //상품 문의 답글 작성 처리
    }


}
