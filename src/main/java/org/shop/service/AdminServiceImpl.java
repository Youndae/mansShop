package org.shop.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.ProductImgVO;
import org.shop.domain.ProductOpVO;
import org.shop.domain.ThumbnailVO;
import org.shop.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@Service
@Log4j
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{

    private AdminMapper adminMapper;

    @Override
    public void addProduct(ProductOpVO productOpVO, ThumbnailVO thumbnailVO, ProductImgVO productImgVO,
                           List<MultipartFile> firstThumb, List<MultipartFile> thumb, List<MultipartFile> infoImg
                            ) throws Exception {

        String filePath = "E:\\upload\\Product\\";
        int step = 1;

        if(productOpVO.getPStock() == null){
            long stock = 0;
            productOpVO.setPStock(stock);
        }

        log.info("get pSize : " + productOpVO.getPSize());

        StringBuffer sb = new StringBuffer();
        String pnoPattern = sb.append(productOpVO.getPClassification())
                .append(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())).toString();

        productOpVO.setPno(pnoPattern);
        productOpVO.setPOpNo("Op_" + pnoPattern);
        log.info("pno : " + productOpVO.getPno());

        log.info("productOpVO : " + productOpVO);

        for(MultipartFile image : firstThumb){
            log.info("firstThumbnail save");

            productOpVO.setFirstThumbnail(imgProc(image, filePath));
            log.info("productOpVO : " + productOpVO);

            adminMapper.addProduct(productOpVO);
        }

        adminMapper.addProductOp(productOpVO);

        log.info("addproductOp success");

        for(MultipartFile image : thumb){
            log.info("Thumbnail save");

            thumbnailVO.setPno(productOpVO.getPno());
            thumbnailVO.setThumbNo("s_" + productOpVO.getPno());
            thumbnailVO.setPThumbnail(imgProc(image, filePath));
            log.info("thumbnailVO : " + thumbnailVO);

            adminMapper.addProductThumbnail(thumbnailVO);
        }

        for(MultipartFile image : infoImg){
            log.info("ProductInfo save");

            productImgVO.setPno(productOpVO.getPno());
            productImgVO.setPImgStep(step++);
            productImgVO.setPImg(imgProc(image, filePath));

            log.info("productImgVO : " + productImgVO);

            adminMapper.addProductInfo(productImgVO);
        }

    }

    public String imgProc(MultipartFile image, String filePath) throws Exception{

        log.info("imgProc");

        try{
            String originalName = image.getOriginalFilename();
            StringBuffer sb = new StringBuffer();
            String saveName = sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()))
                    .append(UUID.randomUUID().toString())
                    .append(originalName.substring(originalName.lastIndexOf("."))).toString();

            File saveFile = new File(filePath, saveName);

            image.transferTo(saveFile);

            log.info("Save image. saveName : " + saveName);

            return saveName;
        }catch (IOException e){
            log.info("image save Error");
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public void modifyProduct(ProductOpVO productOpVO, ThumbnailVO thumbnailVO, ProductImgVO productImgVO,
                              List<MultipartFile> firstThumb, List<MultipartFile> thumb, List<MultipartFile> infoImg,
                              List<String> delFirstThumb, List<String> delThumb, List<String> delInfoImg) throws Exception {

        String filePath = "E:\\upload\\Product\\";
        int step = adminMapper.maxStep(productOpVO.getPno());

        if(productOpVO.getPStock() == null){
            long stock = 0;
            productOpVO.setPStock(stock);
        }

        log.info("productOpVO : " + productOpVO);

        log.info("firstThumb size : " + firstThumb.size());

        if(firstThumb.size() != 0){
            for(MultipartFile image : firstThumb){
                log.info("firstThumbnail save");

                productOpVO.setFirstThumbnail(imgProc(image, filePath));
                log.info("productOpVO : " + productOpVO);
            }
            adminMapper.modifyProductThumb(productOpVO);
        }else{
            adminMapper.modifyProductInfo(productOpVO);
        }

        adminMapper.modifyProductOp(productOpVO);

        log.info("modify productOp success");

        //삭제이미지 DB 데이터 삭제 및 파일 삭제

        if(delFirstThumb != null){
            deleteFiles("f", delFirstThumb, filePath);
        }

        if(delThumb != null){
            deleteFiles("t", delThumb, filePath);
        }

        if(delInfoImg != null){
            deleteFiles("i", delInfoImg, filePath);
        }

        for(MultipartFile image : thumb){
            log.info("Thumbnail save");

            thumbnailVO.setPno(productOpVO.getPno());
            thumbnailVO.setThumbNo("s_" + productOpVO.getPno());
            thumbnailVO.setPThumbnail(imgProc(image, filePath));
            log.info("thumbnailVO : " + thumbnailVO);

            adminMapper.addProductThumbnail(thumbnailVO);
        }

        for(MultipartFile image : infoImg){
            log.info("ProductInfo save");

            productImgVO.setPno(productOpVO.getPno());
            productImgVO.setPImgStep(++step);
            productImgVO.setPImg(imgProc(image, filePath));

            log.info("productImgVO : " + productImgVO);

            adminMapper.addProductInfo(productImgVO);
        }

    }

    public void deleteFiles(String type, List<String> images, String path){

        for(int i = 0; i < images.size(); i++){
            log.info(images.get(i) + " delete");
            File file = new File(path + images.get(i));
            if(file.exists()){
                if(file.delete()){
                    log.info("delete complete");
                    if(type == "t"){
                        adminMapper.deleteThumb(images.get(i));
                        log.info("delete Thumb");
                    }else if(type == "i"){
                        adminMapper.deleteInfoImg(images.get(i));
                        log.info("delete info");
                    }
                }else{
                    log.info("delete failed");
                }
            }else{
                log.info("null");
            }
        }
    }


    @Override
    public void addProductOp(ProductOpVO productOpVO) throws Exception {
        StringBuffer sb = new StringBuffer();

        String pOpNo = sb.append("Op_")
                .append(productOpVO.getPClassification())
                .append(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())).toString();

        productOpVO.setPOpNo(pOpNo);

        adminMapper.addProductOp(productOpVO);
    }
}
