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

        StringBuffer sb = new StringBuffer();
        String pnoPattern = sb.append(productOpVO.getPClassification())
                .append(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())).toString();

        productOpVO.setPno(pnoPattern);
        productOpVO.setPOpNo("Op_" + pnoPattern);
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

    @Override
    public void modifyProduct(ProductOpVO productOpVO, ThumbnailVO thumbnailVO, ProductImgVO productImgVO,
                              List<MultipartFile> firstThumb, List<MultipartFile> thumb, List<MultipartFile> infoImg,
                              List<String> delFirstThumb, List<String> delThumb, List<String> delInfoImg) throws Exception {

        String filePath = "E:\\upload\\Product\\";
        int step = adminMapper.maxStep(productOpVO.getPno());//mapper.maxStep 형태로 제일 큰 step값 가져올것.

        if(productOpVO.getPStock() == null){
            long stock = 0;
            productOpVO.setPStock(stock);
        }


        log.info("productOpVO : " + productOpVO);

        log.info("firstThumb size : " + firstThumb.size());

        /*처리 해야할 것
        * 이미지 파일 삭제
        * 삭제파일 DB 데이터 삭제
        * DB 데이터 수정(product)
        * 수정 이미지 DB에 업로드(p_thumbnail, productImg)
        *
        * 1. product, productOp Table 수정
        * 2. 삭제 이미지 DB 데이터 삭제쿼리(p_thumbnail, productImg)
        * 3. 추가 이미지 파일 업로드 및 DB insert(p_thumbnail, productImg)
        * 4. 이미지파일 삭제.
        */


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

    /*
    * 상품 삭제할 때.
    * DB는 다 CASCADE로 묶여있으니 상품을 통째로 삭제한다면
    * product 테이블에서 해당 데이터를 삭제하면 OP, Thumbnail, Img 테이블에서는 알아서 같이 삭제될것이고.
    * 만약 해당 옵션만 삭제한다면 OP만 삭제하고 thumbnail과 img테이블은 해당 옵션이 빠진 것으로 수정하게 될것.
    *   이런 상황이라면 파일삭제가 수정과 같다고 볼 수 있다. OP테이블은 나머지 두 테이블에 영향력이 없으니까.
    * 근데 수정시에는 여기서 한번에 처리하지 않으면 각 List를 다시 돌려서 삭제해야하는 번거로움이 생길 수 있음.
    *
    * 그럼 방법이
    * 1. deleteFiles에서는 파일만 삭제를 하고 각 메서드에서 처리한다.
    *   단, 이 방법으로 하게 되면 각 메서드에서 반복문으로 List를 훑으면서 이미지명이랑 경로만 deleteFiles로 넘겨주고 처리하게 된다.
    * 2. deleteFiles에서 조건문으로 처리한다.
    *   그럼 수정할때와 상품을 통째로 삭제할때의 type을 다르게 받아서 조건문으로 처리?
    *   ex) 수정시 = f, t, i형태로 각 테이블을 의미하게 하고. 삭제시에는 all 형태로 받아 쿼리문하나만 삭제하고, 파일은 총 3번 받아와서 처리.
    *       혹은 VO에 List로 thumbnail과 img를 받아올 수 있으니까 쿼리문으로 받아와서 firstThumb이랑 thumbnail이랑 img를 하나의 리스트로 처리한다음
    *       deleteFiles로 보내주는 방법.
    */


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
