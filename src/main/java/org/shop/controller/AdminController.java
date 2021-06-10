package org.shop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.ProductImgVO;
import org.shop.domain.ProductOpVO;
import org.shop.domain.ThumbnailVO;
import org.shop.mapper.AdminMapper;
import org.shop.service.AdminService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/admin/")
@Controller
@Log4j
@AllArgsConstructor
public class AdminController {

    private AdminMapper adminMapper;

    private AdminService adminService;

    @GetMapping("/productList")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
                                @RequestParam("infoFiles") List<MultipartFile> infoFiles,
                                HttpServletRequest request
                              ) throws Exception{

        String path = request.getSession().getServletContext().getRealPath("IMG/");

        log.info("controller path : " + path);



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

        adminService.test(opVO, thumbnailVO, imgVO, firstFiles, thumbFiles, infoFiles, request);

        log.info("insert complete");
    }

    @GetMapping("/productInfo")
    public void productInfo(){
        //상품 정보
    }

    @PostMapping("/modifyProductInfo")
    public void modifyProductInfo(){
        //상품 정보 수정
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
