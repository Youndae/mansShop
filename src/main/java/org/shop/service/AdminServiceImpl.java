package org.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.FileProperties;
import org.shop.domain.ResultProperties;
import org.shop.domain.dto.admin.*;
import org.shop.domain.entity.*;
import org.shop.mapper.AdminMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int addProduct(ProductInsertDTO dto
                            , MultipartFile firstThumb
                            , List<MultipartFile> thumb
                            , List<MultipartFile> infoImg) throws Exception{

        int step = 0;

        Product product = Product.builder()
                                .pClassification(dto.getPClassification())
                                .pName(dto.getPName())
                                .pPrice(dto.getPPrice())
                                .firstThumbnail(imgProc(firstThumb))
                                .build();

        adminMapper.addProduct(product);

        adminMapper.addProductOp(ProductOp.builder()
                                        .pno(product.getPno())
                                        .pSize(dto.getPSize())
                                        .pColor(dto.getPColor())
                                        .pStock(dto.getPStock())
                                        .build()
                                );

        addOtherImg(product.getPno(), step, thumb, infoImg);

        return ResultProperties.SUCCESS;
    }

    public String imgProc(MultipartFile image) throws Exception{

        if (image == null)
            return null;

        String originalName = image.getOriginalFilename();
        StringBuffer sb = new StringBuffer();
        String saveName = sb.append(new SimpleDateFormat("yyyyMMddHHmmss")
                                        .format(System.currentTimeMillis()))
                                        .append(UUID.randomUUID().toString())
                                        .append(originalName.substring(originalName.lastIndexOf(".")))
                                        .toString();

        File saveFile = new File(FileProperties.FILE_PATH, saveName);

        image.transferTo(saveFile);
        log.info("Save image. saveName : " + saveName);

        return saveName;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyProduct(ProductModifyDTO dto
                                , MultipartFile firstThumb
                                , List<MultipartFile> thumb
                                , List<MultipartFile> infoImg
                                , String delFirstThumb
                                , List<String> delThumb
                                , List<String> delInfoImg) throws Exception{
        //이미지 순서 번호
        int step = adminMapper.maxStep(dto.getPno());
        //상품 재고
        long stock = dto.getPStock();

        log.info("productOpDTO : " + dto);

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

        log.info("modify productOp success");

        //삭제이미지 DB 데이터 삭제 및 파일 삭제

        //delete firstThumbnail
        deleteFiles(delFirstThumb);

        //delete thumbnail
        if(delThumb != null){
            for(String imageName : delThumb)
                deleteFiles(imageName);

            adminMapper.deleteThumb(delThumb);
        }

        //delete infoImage
        if(delInfoImg != null){
            for(String imageName : delInfoImg)
                deleteFiles(imageName);

            adminMapper.deleteInfoImg(delInfoImg);
        }

        //firstThumb을 제외한 나머지 thumbnail, infoImage 저장 처리
        addOtherImg(dto.getPno(), step, thumb, infoImg);

        return ResultProperties.SUCCESS;
    }

    public void addOtherImg(String pno
                            , int step
                            , List<MultipartFile> thumb
                            , List<MultipartFile> infoImg) throws Exception{

        if(thumb.size() != 0){
            List<ProductThumbnail> thumbList = new ArrayList<>();
            for(MultipartFile image : thumb){
                log.info("Thumbnail save");

                thumbList.add(ProductThumbnail.builder()
                                        .pno(pno)
                                        .pThumbnail(imgProc(image))
                                        .build()
                                );
            }
            log.info("thumbNo");
            adminMapper.addProductThumbnail(thumbList);
        }

        if(infoImg.size() != 0){
            List<ProductImg> infoImgList = new ArrayList<>();
            for(MultipartFile image : infoImg){
                log.info("ProductInfo save");
                infoImgList.add(ProductImg.builder()
                        .pno(pno)
                        .pImgStep(++step)
                        .pImg(imgProc(image))
                        .build());
            }
            adminMapper.addProductInfo(infoImgList);
        }
    }

    public void deleteFiles(String images) throws Exception{

        if (images == null)
            return;

        File file = new File(FileProperties.FILE_PATH + images);

        if(file.exists())
            file.delete();

    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int addProductOp(ProductOpInsertDTO dto) throws Exception{

        adminMapper.addProductOp(ProductOp.builder()
                .pno(dto.getPno())
                .pSize(dto.getPSize())
                .pColor(dto.getPColor())
                .pStock(dto.getPStock())
                .build());

        return ResultProperties.SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int qnAReplyProc(MyQnAReplyDTO dto, Principal principal) throws Exception{

        adminMapper.adminQnAReply(MyQnAReply.builder()
                                            .qno(dto.getQno())
                                            .userId(principal.getName())
                                            .qrContent(dto.getQrContent())
                                            .build()
                                );

        return ResultProperties.SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int productQnAReplyProc(ProductQnAReplyDTO dto, Principal principal) throws Exception{

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
    @Transactional(rollbackFor = {Exception.class})
    public int qnaReplyDelete(int replyNo) throws Exception{

        adminMapper.qnAReplyDelete(replyNo);

        return ResultProperties.SUCCESS;
    }
}
