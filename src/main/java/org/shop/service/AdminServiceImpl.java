package org.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.ResultProperties;
import org.shop.domain.dto.admin.*;
import org.shop.domain.entity.*;
import org.shop.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AdminMapper adminMapper;

    @Value("#{filePath['file.path']}")
    private String filePath;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addProduct(ProductInsertDTO dto
                            , MultipartFile firstThumb
                            , List<MultipartFile> thumb
                            , List<MultipartFile> infoImg) {

        int step = 0;

        Product product = Product.builder()
                                .pClassification(dto.getPClassification())
                                .pName(dto.getPName())
                                .pPrice(dto.getPPrice())
                                .firstThumbnail(imgProc(firstThumb))
                                .build();

        //상품 데이터 저장
        adminMapper.addProduct(product);
        //상품 옵션 데이터 저장
        adminMapper.addProductOp(ProductOp.builder()
                .pno(product.getPno())
                .pSize(dto.getPSize())
                .pColor(dto.getPColor())
                .pStock(dto.getPStock())
                .build()
        );

        //상품 썸네일, 정보이미지 파일 저장 및 데이터 리스트화 및 데이터 저장
        if(!thumb.isEmpty())
            adminMapper.addProductThumbnail(saveProductThumbnail(product.getPno(), thumb));

        if(!infoImg.isEmpty())
            adminMapper.addProductInfo(saveProductInfoImage(product.getPno(), step, infoImg));

        return ResultProperties.SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String modifyProduct(ProductModifyDTO dto
                                , MultipartFile firstThumb
                                , List<MultipartFile> thumb
                                , List<MultipartFile> infoImg
                                , String delFirstThumb
                                , List<String> delThumb
                                , List<String> delInfoImg) {
        //이미지 순서 번호
        int step = adminMapper.maxStep(dto.getPno());
        //상품 재고
        long stock = dto.getPStock();

        //상품 테이블 수정
        adminMapper.modifyProduct(Product.builder()
                                            .pno(dto.getPno())
                                            .pName(dto.getPName())
                                            .pClassification(dto.getPClassification())
                                            .pPrice(dto.getPPrice())
                                            .firstThumbnail(imgProc(firstThumb))
                                            .build()
                                        );
        //상품 옵션 테이블 수정
        adminMapper.modifyProductOp(ProductOp.builder()
                                            .pOpNo(dto.getPOpNo())
                                            .pSize(dto.getPSize())
                                            .pColor(dto.getPColor())
                                            .pStock(stock)
                                            .build()
                                    );

        //삭제이미지 DB 데이터 삭제 및 파일 삭제
        //firstThumbnail 파일 삭제
        deleteFiles(delFirstThumb);
        //썸네일 파일 삭제
        if(!delThumb.isEmpty()){
            for(String imageName : delThumb)
                deleteFiles(imageName);

            adminMapper.deleteThumb(delThumb);
        }

        //정보 이미지 파일 삭제
        if(!delInfoImg.isEmpty()){
            for(String imageName : delInfoImg)
                deleteFiles(imageName);

            adminMapper.deleteInfoImg(delInfoImg);
        }

        //상품 썸네일, 정보이미지 파일 저장 및 데이터 리스트화 및 데이터 저장
        if(!thumb.isEmpty()) {
            List<ProductThumbnail> addProductThumbList = saveProductThumbnail(dto.getPno(), thumb);
            adminMapper.addProductThumbnail(addProductThumbList);
        }
        if(!infoImg.isEmpty()) {
            List<ProductImg> addProductImageList = saveProductInfoImage(dto.getPno(), step, infoImg);
            adminMapper.addProductInfo(addProductImageList);
        }

        return ResultProperties.SUCCESS;
    }

    //이미지 파일 저장
    public String imgProc(MultipartFile image) {
        if (image == null)
            return null;

        String originalName = image.getOriginalFilename();
        StringBuffer sb = new StringBuffer();
        String saveName = sb.append(new SimpleDateFormat("yyyyMMddHHmmss")
                        .format(System.currentTimeMillis()))
                .append(UUID.randomUUID().toString())
                .append(originalName.substring(originalName.lastIndexOf(".")))
                .toString();
        File saveFile = new File(filePath, saveName);

        try {
            image.transferTo(saveFile);
            return saveName;
        } catch (IOException e) {
            log.error("image transfer Error : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //썸네일 파일 저장 및 데이터 리스트화
    public List<ProductThumbnail> saveProductThumbnail(String pno, List<MultipartFile> thumb) {
        List<ProductThumbnail> thumbList = new ArrayList<>();

        for(MultipartFile image : thumb)
            thumbList.add(
                    ProductThumbnail.builder()
                            .pno(pno)
                            .pThumbnail(imgProc(image))
                            .build()
            );

        return thumbList;
    }

    //상품 정보 이미지 파일 저장 및 데이터 리스트화
    public List<ProductImg> saveProductInfoImage(String pno, int step, List<MultipartFile> infoImg) {
        List<ProductImg> infoImgList = new ArrayList<>();

        for(MultipartFile image : infoImg)
            infoImgList.add(
                    ProductImg.builder()
                            .pno(pno)
                            .pImgStep(++step)
                            .pImg(imgProc(image))
                            .build()
            );

        return infoImgList;
    }

    //파일 삭제
    public void deleteFiles(String images) {
        if (images == null)
            return;

        File file = new File(filePath + images);

        if(file.exists())
            file.delete();

    }

    @Override
    public String addProductOp(ProductOpInsertDTO dto) {
        adminMapper.addProductOp(ProductOp.builder()
                .pno(dto.getPno())
                .pSize(dto.getPSize())
                .pColor(dto.getPColor())
                .pStock(dto.getPStock())
                .build());

        return ResultProperties.SUCCESS;
    }

    @Override
    public String qnAReplyProc(MyQnAReplyDTO dto, Principal principal) {
        try{
            adminMapper.adminQnAReply(MyQnAReply.builder()
                    .qno(dto.getQno())
                    .userId(principal.getName())
                    .qrContent(dto.getQrContent())
                    .build()
            );

            return ResultProperties.SUCCESS;
        }catch (Exception e) {
            log.error("QnAReplyProc Exception  : " + e.getMessage());
            return ResultProperties.ERROR;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String productQnAReplyProc(ProductQnAReplyDTO dto, Principal principal) {
        adminMapper.productQnAReply(ProductQnA.builder()
                                            .pno(dto.getPno())
                                            .userId(principal.getName())
                                            .pQnAContent(dto.getPQnAContent())
                                            .pQnANo(dto.getPQnANo())
                                            .build()
                                    );

        adminMapper.productQnAComplete(dto.getPQnANo());

        return ResultProperties.SUCCESS;

    }

    @Override
    public String qnaReplyDelete(int replyNo) {
        try{
            adminMapper.qnAReplyDelete(replyNo);

            return ResultProperties.SUCCESS;
        }catch (Exception e) {
            log.error("QnAReplyDelete Exception : " + e.getMessage());
            return ResultProperties.ERROR;
        }
    }
}
