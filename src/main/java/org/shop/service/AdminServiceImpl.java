package org.shop.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
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
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{

    private AdminMapper adminMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public int addProduct(ProductInsertDTO dto
                            , MultipartFile firstThumb
                            , List<MultipartFile> thumb
                            , List<MultipartFile> infoImg) throws Exception{

        int step = 0;

        long stock = 0;

        if(dto.getPStock() != null)
            stock = dto.getPStock();

        log.info("get pSize : " + dto.getPSize());

        StringBuffer sb = new StringBuffer();
        String pnoPattern = sb.append(dto.getPClassification())
                .append(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())).toString();

        String firstThumbName;


        firstThumbName = imgProc(firstThumb);


        Product product = Product.builder()
                .pno(pnoPattern)
                .pName(dto.getPName())
                .pClassification(dto.getPClassification())
                .pPrice(dto.getPPrice())
                .firstThumbnail(firstThumbName)
                .build();
        log.info("product Entity : " + product);
        adminMapper.addProduct(product);

        ProductOp productOp = ProductOp.builder()
                .pOpNo("Op_" + pnoPattern)
                .pno(pnoPattern)
                .pSize(dto.getPSize())
                .pColor(dto.getPColor())
                .pStock(stock)
                .build();
        adminMapper.addProductOp(productOp);

        log.info("addProductOp success");

        addOtherImg(pnoPattern, step, thumb, infoImg);

        return ResultProperties.SUCCESS;
    }

    public String imgProc(MultipartFile image) throws Exception{

        log.info("imgProc");

        String originalName = image.getOriginalFilename();
        StringBuffer sb = new StringBuffer();
        String saveName = sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()))
                .append(UUID.randomUUID().toString())
                .append(originalName.substring(originalName.lastIndexOf("."))).toString();

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

        int step = adminMapper.maxStep(dto.getPno());

        long stock = 0;
        if(dto.getPStock() != null)
            stock = dto.getPStock();


        log.info("productOpVO : " + dto);

        if(firstThumb != null){
            Product product = Product.builder()
                    .pno(dto.getPno())
                    .pName(dto.getPName())
                    .pClassification(dto.getPClassification())
                    .pPrice(dto.getPPrice())
                    .firstThumbnail(imgProc(firstThumb))
                    .build();
            adminMapper.modifyProductThumb(product);
        }else{
            Product product = Product.builder()
                    .pno(dto.getPno())
                    .pName(dto.getPName())
                    .pClassification(dto.getPClassification())
                    .pPrice(dto.getPPrice())
                    .build();
            adminMapper.modifyProductInfo(product);
        }

        ProductOp productOp = ProductOp.builder()
                .pOpNo(dto.getPOpNo())
                .pSize(dto.getPSize())
                .pColor(dto.getPColor())
                .pStock(stock)
                .build();

        adminMapper.modifyProductOp(productOp);

        log.info("modify productOp success");

        //삭제이미지 DB 데이터 삭제 및 파일 삭제

        if(delFirstThumb != null)
            deleteFiles(delFirstThumb);

        if(delThumb != null){
            for(String imageName : delThumb)
                deleteFiles(imageName);

            adminMapper.deleteThumb(delThumb);
        }

        if(delInfoImg != null){
            for(String imageName : delInfoImg)
                deleteFiles(imageName);

            adminMapper.deleteInfoImg(delInfoImg);
        }

        addOtherImg(dto.getPno(), step, thumb, infoImg);

        return ResultProperties.SUCCESS;

    }

    public void addOtherImg(String pno, int step, List<MultipartFile> thumb, List<MultipartFile> infoImg) throws Exception{

        if(thumb.size() != 0){
            List<ProductThumbnail> thumbList = new ArrayList<>();
            for(MultipartFile image : thumb){
                log.info("Thumbnail save");
                StringBuffer sb = new StringBuffer();
                String thumbPattern = sb.append("s_")
                        .append(pno + "_")
                        .append(new SimpleDateFormat("yyyyMMddHHmmss")
                                .format(System.currentTimeMillis()))
                        .append(UUID.randomUUID()).toString();
                thumbList.add(ProductThumbnail.builder()
                        .pno(pno)
                        .thumbNo(thumbPattern)
                        .pThumbnail(imgProc(image))
                        .build());
            }

            log.info("thumbNo");
            for(int i = 0; i < thumbList.size(); i++){
                log.info("thumb" + i + "'s No : " + thumbList.get(i).getThumbNo());
            }

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

        File file = new File(FileProperties.FILE_PATH + images);

        if(file.exists())
            file.delete();

    }


    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public int addProductOp(ProductOpInsertDTO dto) throws Exception{
        StringBuffer sb = new StringBuffer();

        ProductOp productOp = ProductOp.builder()
                .pOpNo(sb.append("Op_")
                        .append(dto.getPClassification())
                        .append(new SimpleDateFormat("yyyyMMddHHmmss")
                                .format(System.currentTimeMillis())).toString())
                .pno(dto.getPno())
                .pSize(dto.getPSize())
                .pColor(dto.getPColor())
                .pStock(dto.getPStock())
                .build();

        adminMapper.addProductOp(productOp);
        return ResultProperties.SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public int qnAReplyProc(MyQnAReplyDTO dto, Principal principal) throws Exception{

        MyQnAReply myQnaReply = MyQnAReply.builder()
                .qno(dto.getQno())
                .userId(principal.getName())
                .qrContent(dto.getQrContent())
                .build();

        log.info("QnAReply Controller : " + myQnaReply);

        adminMapper.adminQnAReply(myQnaReply);

        return ResultProperties.SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public int productQnAReplyProc(ProductQnAReplyDTO dto, Principal principal) throws Exception{

        ProductQnA productQna = ProductQnA.builder()
                .pno(dto.getPno())
                .userId(principal.getName())
                .pQnAContent(dto.getPQnAContent())
                .pQnANo(dto.getPQnANo())
                .build();

        adminMapper.productQnAReply(productQna);
        adminMapper.productQnAComplete(dto.getPQnANo());
        return ResultProperties.SUCCESS;

    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public int qnaReplyDelete(int replyNo) throws Exception{

        adminMapper.qnAReplyDelete(replyNo);

        return ResultProperties.SUCCESS;
    }
}
