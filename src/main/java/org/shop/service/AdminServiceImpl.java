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

    /*@Override
    //이미지 업로드 test메서드.
    public void test(ProductOpVO productOpVO, ThumbnailVO thumbnailVO, ProductImgVO productImgVO,
                     List<MultipartFile> firstThumb, List<MultipartFile> thumb, List<MultipartFile> infoImg,
                     HttpServletRequest request) throws Exception{

        *//*String filePath = request.getSession().getServletContext().getRealPath("/resources/img/");*//*

        String filePath = "E:\\upload";

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

        *//*log.info("filePath : " + filePath);*//*

        try{
            String originalname = image.getOriginalFilename();
            StringBuffer sb = new StringBuffer();
            String saveName = sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()))
                    .append(UUID.randomUUID().toString())
                    .append(originalname.substring(originalname.lastIndexOf("."))).toString();
            *//*String saveFile = filePath + saveName;*//*

            File saveFile = new File(filePath, saveName);

            image.transferTo(saveFile);
            log.info("image save. saveName : " + saveName);

            return saveName;
        }catch (Exception e){
            log.info("Exception!!!");
            e.printStackTrace();
        }

        log.info("why?");
        return "";
    }*/





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

        productOpVO.setPno(productOpVO.getPClassification()+productOpVO.getPName());
        productOpVO.setPOpNo(productOpVO.getPClassification() + productOpVO.getPName() + productOpVO.getPSize() + productOpVO.getPColor());
        log.info("pno : " + productOpVO.getPno());
        //mapper.addProduct로 product테이블과 productOp테이블에 먼저 insert

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
}
