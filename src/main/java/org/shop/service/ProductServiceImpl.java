package org.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.shop.domain.ResultProperties;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.dto.product.ProductQnADTO;
import org.shop.domain.dto.product.ProductQnAInsertDTO;
import org.shop.domain.dto.product.ProductReviewDTO;
import org.shop.domain.entity.CartDetail;
import org.shop.domain.entity.Cart;
import org.shop.domain.entity.LikeProduct;
import org.shop.domain.entity.ProductQnA;
import org.shop.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductMapper productMapper;

    private final CookieService cookieService;

    @Override
    public ProductQnADTO getProductQnA(Criteria cri, String pno) {

        return new ProductQnADTO(
                productMapper.getProductQnATotal(pno),
                productMapper.getProductQnA(cri, pno)
        );
    }

    @Override
    public ProductReviewDTO getProductReview(Criteria cri, String pno) {

        log.info("ProductReview Service pno : " + pno + " cri" + cri.getPageNum());

        log.info("get ProductReviewTotal : " + productMapper.getProductReviewTotal(pno));

        log.info("getProductReview : " + productMapper.getProductReview(cri, pno));

        return new ProductReviewDTO(
                productMapper.getProductReviewTotal(pno),
                productMapper.getProductReview(cri, pno));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int addCart(List<String> pOpNo
            , List<String> pCount
            , List<String> pPrice
            , Principal principal
            , HttpServletRequest request
            , HttpServletResponse response) throws Exception{
        log.info("addCart impl");

        CartDetail cartDetail;

        Cart cart;

        cart = cookieService.checkCookie(request, principal, response, true);

        String cartNo = productMapper.checkCartNo(cart);

        //장바구니에 회원 혹은 쿠키에 해당하는 데이터가 있다면
        if(cartNo != null){

            List<CartDetail> updateCartDetailList = new ArrayList<>();
            List<CartDetail> addCartDetailList = new ArrayList<>();

            List<String> userCartPOpNoList = productMapper.checkDetailOption(cartNo);

            for(int i = 0; i < pOpNo.size(); i++) {
                cartDetail = setDetail(cartNo, i, pOpNo, pCount, pPrice);

                //detail에 같은 옵션 상품이 존재한다면
                if(userCartPOpNoList.contains(pOpNo.get(i))){
                    updateCartDetailList.add(cartDetail);
                }else{ //detail에 같은 옵션 상품이 존재하지 않는다면 detail 테이블에 데이터 추가.
                    addCartDetailList.add(cartDetail);
                }
            }

            if(updateCartDetailList.size() != 0)
                productMapper.updateCartDetail(updateCartDetailList);

            if(addCartDetailList.size() != 0)
                productMapper.addCartDetail(addCartDetailList);
            //cart 테이블 데이터의 updatedAt(수정일자) 수정.
            productMapper.updateCart(cart);
        }else{ //장바구니 테이블에 회원 혹은 쿠키에 해당하는 데이터가 없다면
            /*cart.setCartNo(LocalDateTime.now() + RandomStringUtils.random(4, true, true));*/
            cart.setCartNo(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()).toString() + RandomStringUtils.random(4, true, true));

            //cart insert
            productMapper.addCart(cart);

            List<CartDetail> addCartDetailList = new ArrayList<>();

            for(int i = 0; i < pOpNo.size(); i++){
                cartDetail = setDetail(cart.getCartNo(), i, pOpNo, pCount, pPrice);

                addCartDetailList.add(cartDetail);
            }
            //detail insert
            productMapper.addCartDetail(addCartDetailList);
        }
        return ResultProperties.SUCCESS;

    }

    public CartDetail setDetail(String cartNo, int i, List<String> pOpNo, List<String> pCount, List<String> pPrice){

            CartDetail cartDetail = CartDetail.builder()
                    .cartNo(cartNo)
                    .cdNo(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()).toString() + pOpNo.get(i) + pCount.get(i))
                    .pOpNo(pOpNo.get(i))
                    .cCount(Integer.parseInt(pCount.get(i)))
                    .cPrice(Long.parseLong(pPrice.get(i)))
                    .build();

        return cartDetail;

    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int likeProduct(String pno, Principal principal) throws Exception{

        String uid = principal.getName();

        LikeProduct likeProduct = LikeProduct.builder()
                .pno(pno)
                .likeNo(uid + pno)
                .userId(uid)
                .build();

        productMapper.likeProduct(likeProduct);
        return ResultProperties.SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int deLikeProduct(String pno, Principal principal) throws Exception{

        LikeProduct likeProduct = LikeProduct.builder()
                .pno(pno)
                .userId(principal.getName())
                .build();

        productMapper.deLikeProduct(likeProduct);
        return ResultProperties.SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int qnAInsertProc(ProductQnAInsertDTO dto, Principal principal) throws Exception{

        ProductQnA productQna = ProductQnA.builder()
                .pno(dto.getPno())
                .userId(principal.getName())
                .pQnAContent(dto.getPQnAContent())
                .build();

        productMapper.insertPQnA(productQna);
        return ResultProperties.SUCCESS;
    }
}
