package org.shop.mapper;

import org.shop.domain.Criteria;
import org.shop.domain.ProductOpVO;
import org.shop.domain.ProductVO;
import org.shop.domain.ThumbnailVO;

import java.util.List;

public interface MainMapper {

    public List<ProductOpVO> mainBest();

    public ProductVO productDetail(String pno);

    public List<ThumbnailVO> getProductThumb(String pno);

    public List<ProductOpVO> getProductOp(String pno);

    public void mainNew();
}
