package org.shop.service;

import org.shop.domain.ProductImgVO;
import org.shop.domain.ProductOpVO;
import org.shop.domain.ThumbnailVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AdminService {

    public void addProduct(ProductOpVO productOpVO, ProductImgVO productImgVO, ThumbnailVO thumbnailVO, List<MultipartFile> firstThumb, List<MultipartFile> thumb, List<MultipartFile> infoImg, HttpServletRequest request) throws Exception;
    public void test(ProductOpVO productOpVO, ProductImgVO productImgVO, ThumbnailVO thumbnailVO,
                     List<MultipartFile> firstThumb, List<MultipartFile> thumb, List<MultipartFile> infoImg,
                     HttpServletRequest request) throws Exception;

}
