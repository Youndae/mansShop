package org.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
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
        return new ProductReviewDTO(
                productMapper.getProductReviewTotal(pno),
                productMapper.getProductReview(cri, pno));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addCart(List<String> pOpNo
                    , List<String> pCount
                    , List<String> pPrice
                    , Principal principal
                    , HttpServletRequest request
                    , HttpServletResponse response) {
        CartDetail cartDetail;
        Cart cart = cookieService.checkCookie(request, principal, response, true);
        String cartNo = productMapper.checkCartNo(cart);
        cart.setCartNo(cartNo);
        List<CartDetail> addCartDetailList = new ArrayList<>();

        //장바구니에 회원 혹은 쿠키에 해당하는 데이터가 있다면
        //userCartPOpNoList에 해당 데이터의 pOpNo 리스트를 조회해 담아주고
        //추가할 상품의 pOpNo와 비교해 수량 증감 || 상품 추가 여부를 판단한다.
        if(cartNo != null){
            List<CartDetail> updateCartDetailList = new ArrayList<>();
            List<String> userCartPOpNoList = productMapper.checkDetailOption(cartNo);

            for(int i = 0; i < pOpNo.size(); i++) {
                cartDetail = buildCartDetail(cartNo, pOpNo.get(i), pCount.get(i), pPrice.get(i));

                //detail에 같은 옵션 상품이 존재한다면
                if(userCartPOpNoList.contains(pOpNo.get(i)))
                    updateCartDetailList.add(cartDetail);
                else //detail에 같은 옵션 상품이 존재하지 않는다면 detail 테이블에 데이터 추가.
                    addCartDetailList.add(cartDetail);
            }

            if(updateCartDetailList.size() != 0)
                productMapper.updateCartDetail(updateCartDetailList);

            if(addCartDetailList.size() != 0)
                productMapper.addCartDetail(addCartDetailList);
            //cart 테이블 데이터의 updatedAt(수정일자) 수정.
            productMapper.updateCart(cart);
        }else{ //장바구니 테이블에 회원 혹은 쿠키에 해당하는 데이터가 없다면
            //cart insert
            productMapper.addCart(cart);

            for(int i = 0; i < pOpNo.size(); i++) {
                cartDetail = buildCartDetail(cart.getCartNo(), pOpNo.get(i), pCount.get(i), pPrice.get(i));
                addCartDetailList.add(cartDetail);
            }
            //detail insert
            productMapper.addCartDetail(addCartDetailList);
        }
        return ResultProperties.SUCCESS;
    }

    public CartDetail buildCartDetail(String cartNo, String pOpNo, String pCount, String pPrice){
        return CartDetail.builder()
                .cartNo(cartNo)
                .cdNo(
                        new SimpleDateFormat("yyyyMMddHHmmss")
                                        .format(System.currentTimeMillis()
                                ) + pOpNo + pCount
                )
                .pOpNo(pOpNo)
                .cCount(Integer.parseInt(pCount))
                .cPrice(Long.parseLong(pPrice))
                .build();
    }

    @Override
    public String likeProduct(String pno, Principal principal) {
        try{
            String uid = principal.getName();
            productMapper.likeProduct(LikeProduct.builder()
                    .pno(pno)
                    .likeNo(uid + pno)
                    .userId(uid)
                    .build()
            );

            return ResultProperties.SUCCESS;
        }catch (Exception e) {
            log.error("likeProduct Exception : " + e.getMessage());
            return ResultProperties.ERROR;
        }
    }

    @Override
    public String deLikeProduct(String pno, Principal principal){
        try{
            productMapper.deLikeProduct(LikeProduct.builder()
                    .pno(pno)
                    .userId(principal.getName())
                    .build()
            );

            return ResultProperties.SUCCESS;
        }catch (Exception e ){
            log.error("deLikeProduct Exception  : " + e.getMessage());
            return ResultProperties.ERROR;
        }
    }

    @Override
    public String qnAInsertProc(ProductQnAInsertDTO dto, Principal principal) {
        try{
            productMapper.insertPQnA(ProductQnA.builder()
                    .pno(dto.getPno())
                    .userId(principal.getName())
                    .pQnAContent(dto.getPQnAContent())
                    .build()
            );

            return ResultProperties.SUCCESS;
        }catch (Exception e) {
            log.error("QnAInsertProc Exception : " + e.getMessage());
            return ResultProperties.ERROR;
        }


    }
}
