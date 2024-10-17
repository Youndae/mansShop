package org.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.dto.paging.PageDTO;
import org.shop.domain.dto.paging.PagingResponseDTO;
import org.shop.domain.dto.product.business.ProductOptionDTO;
import org.shop.domain.dto.product.business.ProductQnAListDTO;
import org.shop.domain.dto.product.business.ProductQnAReplyListDTO;
import org.shop.domain.dto.product.business.ProductReviewListDTO;
import org.shop.domain.dto.product.in.ProductQnARequestDTO;
import org.shop.domain.dto.product.out.ProductDetailDTO;
import org.shop.domain.entity.*;
import org.shop.domain.enumeration.PageAmount;
import org.shop.domain.enumeration.Result;
import org.shop.exception.customException.CustomNotFoundException;
import org.shop.exception.enumeration.ErrorCode;
import org.shop.mapper.*;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductMapper productMapper;

    private final ProductLikeMapper productLikeMapper;

    private final ProductOptionMapper productOptionMapper;

    private final ProductThumbnailMapper productThumbnailMapper;

    private final ProductInfoImageMapper productInfoImageMapper;

    private final ProductReviewMapper productReviewMapper;

    private final ProductQnAMapper productQnAMapper;

    private final ProductQnAReplyMapper productQnAReplyMapper;


    @Override
    public ProductDetailDTO productDetail(String productId, Principal principal) {
        boolean likeStat = false;
        if(principal != null)
            likeStat = productLikeMapper.countByUserIdAndProductId(principal.getName(), productId);

        Product product = productMapper.findById(productId);

        if(product == null)
            throw new CustomNotFoundException(ErrorCode.NOT_FOUND, ErrorCode.NOT_FOUND.getMessage());

        List<ProductOptionDTO> optionDTOs = productOptionMapper.findAllDetailByProductId(productId);
        List<String> productThumbnailList = productThumbnailMapper.findAllByProductId(productId);
        List<String> productInfoImageList = productInfoImageMapper.findAllByProductId(productId);
        PagingResponseDTO<ProductReviewListDTO> review = getDetailReview(productId, 1);
        PagingResponseDTO<ProductQnAListDTO> productQnA = getDetailProductQnA(productId, 1);

        return ProductDetailDTO.builder()
                                .product(product)
                                .likeStat(likeStat)
                                .options(optionDTOs)
                                .thumbnails(productThumbnailList)
                                .infoImages(productInfoImageList)
                                .reviews(review)
                                .qnAs(productQnA)
                                .build();
    }

    @Override
    public PagingResponseDTO<ProductReviewListDTO> getDetailReview(String productId, int page) {
        Criteria cri = new Criteria(page, PageAmount.PRODUCT_REVIEW_AND_QNA_AMOUNT.getAmount());
        List<ProductReviewListDTO> reviewList = productReviewMapper.findAllDetailByProductId(productId, cri);
        int totalElements = productReviewMapper.countByProductId(productId);

        return new PagingResponseDTO<>(reviewList, new PageDTO<>(cri, totalElements));
    }

    @Override
    public PagingResponseDTO<ProductQnAListDTO> getDetailProductQnA(String productId, int page) {
        Criteria cri = new Criteria(page, PageAmount.PRODUCT_REVIEW_AND_QNA_AMOUNT.getAmount());
        List<ProductQnA> qnaList = productQnAMapper.findAllDetailByProductId(productId, cri);
        int totalElements = productQnAMapper.countByProductId(productId);
        List<ProductQnAListDTO> content = new ArrayList<>();

        if(totalElements > 0){
            List<Long> qnaIds = qnaList.stream().map(ProductQnA::getId).collect(Collectors.toList());
            List<ProductQnAReply> qnaReplyList = productQnAReplyMapper.findAllByQnAIds(qnaIds);
            int replyIdx = 0;

            for(int i = 0; i < qnaList.size(); i++) {
                List<ProductQnAReplyListDTO> replyList = new ArrayList<>();
                ProductQnA qnaEntity = qnaList.get(i);

                for(int j = replyIdx; j < qnaReplyList.size(); j++) {
                    if(qnaEntity.getId() == qnaReplyList.get(j).getQnaId())
                        replyList.add(new ProductQnAReplyListDTO(qnaReplyList.get(j)));
                    else{
                        replyIdx = j;
                        break;
                    }
                }
                content.add(new ProductQnAListDTO(qnaEntity, replyList));
            }
        }

        return new PagingResponseDTO<>(content, new PageDTO<>(cri, totalElements));
    }

    @Override
    public String insertQnA(ProductQnARequestDTO dto) {

        try{
            productQnAMapper.saveQnA(dto);
        }catch (Exception e){
            return Result.FAIL.getResultKey();
        }

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public String likeProduct(String pno, Principal principal) {
        try{
            String uid = principal.getName();
            productLikeMapper.likeProduct(ProductLike.builder()
                    .productId(pno)
                    .userId(uid)
                    .build()
            );

            return Result.SUCCESS.getResultKey();
        }catch (Exception e) {
            log.error("likeProduct Exception : " + e.getMessage());
            return Result.ERROR.getResultKey();
        }
    }

    @Override
    public String deLikeProduct(String pno, Principal principal){
        try{
            productLikeMapper.deLikeProduct(ProductLike.builder()
                    .productId(pno)
                    .userId(principal.getName())
                    .build()
            );

            return Result.SUCCESS.getResultKey();
        }catch (Exception e ){
            log.error("deLikeProduct Exception  : " + e.getMessage());
            return Result.ERROR.getResultKey();
        }
    }
}
