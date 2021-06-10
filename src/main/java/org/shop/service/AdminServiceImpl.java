package org.shop.service;

import lombok.extern.log4j.Log4j;
import org.shop.domain.ProductImgVO;
import org.shop.domain.ProductOpVO;
import org.shop.domain.ThumbnailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@Service
@Log4j
public class AdminServiceImpl implements AdminService{


    @Override
    //이미지 업로드 test메서드.
    public void test(ProductOpVO productOpVO, ThumbnailVO thumbnailVO, ProductImgVO productImgVO,
                     List<MultipartFile> firstThumb, List<MultipartFile> thumb, List<MultipartFile> infoImg,
                     HttpServletRequest request) throws Exception{

        String filePath = request.getSession().getServletContext().getRealPath("resources/");

        log.info("filePath : " + filePath);

        for(MultipartFile image : firstThumb){
            log.info("firstThumb start");
                productOpVO.setPno(productOpVO.getPClassification() + productOpVO.getPName());
                productOpVO.setFirstThumbnail(test2(image, filePath));
                log.info("firstImg : " + productOpVO.getFirstThumbnail());

        }
        log.info("first end");

        for(MultipartFile image : thumb){
            log.info("thumb start");
            thumbnailVO.setPThumbnail(test2(image, filePath));
            log.info("pthumb name : " + thumbnailVO.getPThumbnail());
            log.info("thumb save complete");
            thumbnailVO.setThumbNo("s_"+productOpVO.getPno());
            log.info("set ThumbNo");
            thumbnailVO.setPno(productOpVO.getPno());
            log.info("set Thumb pno");
        }
        log.info("thumb end");
        int step = 1;
        for(MultipartFile image : infoImg){
            log.info("info start");
            productImgVO.setPImg(test2(image, filePath));
            productImgVO.setPImgStep(step);
            productImgVO.setPno(productOpVO.getPno());
            step++;

        }
        log.info("info end");


        log.info("productOpVo : " +productOpVO);
        log.info("thumbnailVO : " + thumbnailVO);
        log.info("productImgVO : " + productImgVO);
    }

    public String test2(MultipartFile image, String filePath){

        /*log.info("filePath : " + filePath);*/

        try{
            String originalname = image.getOriginalFilename();
            StringBuffer sb = new StringBuffer();
            String saveName = sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()))
                    .append(UUID.randomUUID().toString())
                    .append(originalname.substring(originalname.lastIndexOf("."))).toString();
            String saveFile = filePath + saveName;

            /*image.transferTo(new File(saveFile));*/
            log.info("image save. saveName : " + saveName);

            return saveName;
        }catch (Exception e){
            log.info("Exception!!!");
            e.printStackTrace();
        }

        log.info("why?");
        return "";
    }





    @Override
    public void addProduct(ProductOpVO productOpVO, ProductImgVO productImgVO, ThumbnailVO thumbnailVO,
                           List<MultipartFile> firstThumb, List<MultipartFile> thumb, List<MultipartFile> infoImg,
                           HttpServletRequest request) throws Exception {

        productOpVO.setPno(productOpVO.getPClassification()+productOpVO.getPName());
        /*productOpVO.setFirstThumbnail(imgProc(firstThumb, request, "first"));*/

        //mapper.addProduct로 product테이블과 productOp테이블에 먼저 insert

        imgProc(firstThumb, request, "first", productOpVO);


        imgProc(thumb, request, "thumb", productOpVO);
        imgProc(infoImg, request, "info", productOpVO);
    }

    public void imgProc(List<MultipartFile> images, HttpServletRequest request, String imgType, ProductOpVO productOpVO) throws Exception{
        ProductImgVO productImgVO;
        ThumbnailVO thumbnailVO;

        int step = 1;

        String filePath = request.getSession().getServletContext().getRealPath("img/");

        for(MultipartFile image : images){
            String originalName = image.getOriginalFilename();
            if(imgType == "first"){
                try{
                    StringBuffer sb = new StringBuffer();
                    String saveName = sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()))
                            .append(UUID.randomUUID().toString())
                            .append(originalName.substring(originalName.lastIndexOf("."))).toString();
                    String saveFile = filePath + saveName;

                    image.transferTo(new File(saveFile));
                    log.info("file save");
                    productOpVO.setFirstThumbnail(saveName);

                    //mapper

                    log.info(productOpVO);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else if(imgType == "thumb"){
                try{
                    StringBuffer sb = new StringBuffer();
                    String saveName = sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()))
                            .append(UUID.randomUUID().toString())
                            .append(originalName.substring(originalName.lastIndexOf("."))).toString();
                    String saveFile = filePath + saveName;

                    image.transferTo(new File(saveFile));
                    log.info("file save");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else if(imgType == "info"){

            }
        }

    }
}
