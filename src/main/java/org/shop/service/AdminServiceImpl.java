package org.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.dto.admin.member.in.AdminMemberPointRequestDTO;
import org.shop.domain.dto.admin.member.out.AdminMemberDetailDTO;
import org.shop.domain.dto.admin.member.out.AdminMemberResponseDTO;
import org.shop.domain.dto.admin.order.business.AdminOrderDetailListDTO;
import org.shop.domain.dto.admin.order.out.AdminOrderDetailDTO;
import org.shop.domain.dto.admin.order.out.AdminOrderResponseDTO;
import org.shop.domain.dto.admin.product.business.AdminProductOptionDTO;
import org.shop.domain.dto.admin.product.business.AdminProductStockInfoDTO;
import org.shop.domain.dto.admin.product.in.AdminPatchDiscountDTO;
import org.shop.domain.dto.admin.product.in.AdminProductDeleteImageDTO;
import org.shop.domain.dto.admin.product.in.AdminProductImageDTO;
import org.shop.domain.dto.admin.product.in.AdminProductPatchDTO;
import org.shop.domain.dto.admin.product.out.*;
import org.shop.domain.dto.admin.qna.out.AdminMemberQnADetailDTO;
import org.shop.domain.dto.admin.qna.out.AdminProductQnADetailDTO;
import org.shop.domain.dto.admin.qna.out.AdminQnAClassificationResponseDTO;
import org.shop.domain.dto.admin.qna.out.AdminQnAListResponseDTO;
import org.shop.domain.dto.admin.review.business.AdminReviewDetailDTO;
import org.shop.domain.dto.admin.review.business.AdminReviewReplyDTO;
import org.shop.domain.dto.admin.review.out.AdminReviewDetailResponseDTO;
import org.shop.domain.dto.admin.review.out.AdminReviewListDTO;
import org.shop.domain.dto.admin.sales.business.*;
import org.shop.domain.dto.admin.sales.out.*;
import org.shop.domain.dto.myPage.qna.business.MyPageMemberQnADetailDTO;
import org.shop.domain.dto.myPage.qna.business.MyPageProductQnADetailDTO;
import org.shop.domain.dto.myPage.qna.business.QnAReplyListDTO;
import org.shop.domain.dto.myPage.qna.in.QnAReplyRequestDTO;
import org.shop.domain.dto.paging.PageCriteria;
import org.shop.domain.dto.product.business.ProductOptionDTO;
import org.shop.domain.entity.*;
import org.shop.domain.enumeration.OrderStatus;
import org.shop.domain.enumeration.Result;
import org.shop.mapper.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final ProductMapper productMapper;

    private final ProductOptionMapper productOptionMapper;

    private final ProductThumbnailMapper productThumbnailMapper;

    private final ProductInfoImageMapper productInfoImageMapper;

    private final ClassificationMapper classificationMapper;

    private final ProductOrderMapper productOrderMapper;

    private final ProductOrderDetailMapper productOrderDetailMapper;

    private final ProductQnAMapper productQnAMapper;

    private final ProductQnAReplyMapper productQnAReplyMapper;

    private final MemberQnAMapper memberQnAMapper;

    private final MemberQnAReplyMapper memberQnAReplyMapper;

    private final QnAClassificationMapper qnAClassificationMapper;

    private final MemberMapper memberMapper;

    private final ProductReviewMapper productReviewMapper;

    private final ProductReviewReplyMapper productReviewReplyMapper;

    @Value("#{filePath['file.path']}")
    private String filePath;

    @Override
    public List<AdminProductListDTO> getProductList(PageCriteria cri) {
        return productMapper.findAllByAdminProduct(cri);
    }

    @Override
    public int getProductListTotalElements(PageCriteria cri) {
        return productMapper.countByAdminProductTotalElements(cri);
    }

    @Override
    public AdminProductDetailDTO getProductDetail(String productId) {
        Product productEntity = productMapper.findById(productId);
        List<String> thumbnailList = productThumbnailMapper.findAllByProductId(productId);
        List<String> infoImageList = productInfoImageMapper.findAllByProductId(productId);
        List<ProductOptionDTO> optionList = productOptionMapper.findAllDetailByProductId(productId);

        return new AdminProductDetailDTO(productEntity, thumbnailList, infoImageList, optionList);
    }

    @Override
    public AdminProductPatchDataDTO getProductPatchData(String productId) {
        Product productEntity = productMapper.findById(productId);
        List<String> thumbnailList = productThumbnailMapper.findAllByProductId(productId);
        List<String> infoImageList = productInfoImageMapper.findAllByProductId(productId);
        List<ProductOptionDTO> optionList = productOptionMapper.findAllDetailByProductId(productId);
        List<String> classificationList = getProductClassificationList();

        return new AdminProductPatchDataDTO(productEntity, thumbnailList, infoImageList, classificationList, optionList);
    }

    @Override
    public List<String> getProductClassificationList() {
        return classificationMapper.findAllId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insertProduct(AdminProductImageDTO imageDTO, AdminProductPatchDTO dto) {

        Product productEntity = dto.toPostEntity();
        String firstThumbnailSaveName = null;
        List<String> thumbnailSaveNames = new ArrayList<>();
        List<String> infoImageSaveNames = new ArrayList<>();
        Map<String, Object> params;
        try {
            firstThumbnailSaveName = imageInsert(imageDTO.getFirstThumbnail());
            productEntity.setThumbnail(firstThumbnailSaveName);
            productMapper.saveProduct(productEntity); //selectKey

            List<ProductOption> options = dto.getNewOptions().stream()
                                            .map(option -> option.toEntity(productEntity.getId()))
                                            .collect(Collectors.toList());
            params = setParams("options", options);
            productOptionMapper.saveOptions(params);

            thumbnailSaveNames = insertImageList(imageDTO.getThumbnails());
            List<ProductThumbnail> thumbnails = thumbnailSaveNames.stream()
                                                    .map(v -> new ProductThumbnail(productEntity.getId(), v))
                                                    .collect(Collectors.toList());
            params = setParams("thumbnails", thumbnails);
            productThumbnailMapper.saveThumbnails(params);

            infoImageSaveNames = insertImageList(imageDTO.getInfoImages());
            List<ProductInfoImage> infoImages = infoImageSaveNames.stream()
                                                    .map(v -> new ProductInfoImage(productEntity.getId(), v))
                                                    .collect(Collectors.toList());
            params = setParams("infoImages", infoImages);
            productInfoImageMapper.saveInfoImages(params);
        }catch (Exception e) {
            log.warn("Failed to admin postProduct");
            e.printStackTrace();
            deleteImageFile(firstThumbnailSaveName);
            thumbnailSaveNames.forEach(this::deleteImageFile);
            infoImageSaveNames.forEach(this::deleteImageFile);

            throw new IllegalArgumentException("Failed postProduct", e);
        }

        return productEntity.getId();
    }

    private <T> Map<String, Object> setParams(String key, List<T> value) {
        Map<String, Object> params = new HashMap<>();
        params.put(key, value);

        return params;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String patchProduct(String productId
                            , List<Long> deleteOptionIds
                            , AdminProductImageDTO imageDTO
                            , AdminProductDeleteImageDTO deleteImageDTO
                            , AdminProductPatchDTO patchDTO) {
        Product productEntity = patchDTO.toPatchEntity(productId);
        String firstThumbnailSaveName = null;
        List<String> thumbnailSaveNames = new ArrayList<>();
        List<String> infoImageSaveNames = new ArrayList<>();
        Map<String, Object> params;
        try{
            if(imageDTO.getFirstThumbnail() != null){
                firstThumbnailSaveName = imageInsert(imageDTO.getFirstThumbnail());
                productEntity.setThumbnail(firstThumbnailSaveName);
            }
            productMapper.patchProduct(productEntity);

            if(patchDTO.getNewOptions() != null){
                List<ProductOption> newOptions = patchDTO.getNewOptions().stream().map(option -> option.toEntity(productId)).collect(Collectors.toList());
                params = setParams("options", newOptions);
                productOptionMapper.saveOptions(params);
            }

            if(patchDTO.getOldOptions() != null) {
                List<ProductOption> oldOptions = patchDTO.getOldOptions().stream().map(option -> option.toEntity(productId)).collect(Collectors.toList());
                productOptionMapper.patchOptions(oldOptions);
            }

            if(deleteOptionIds.size() != 0)
                productOptionMapper.deleteByIds(deleteOptionIds);

            if(imageDTO.getThumbnails() != null){
                thumbnailSaveNames = insertImageList(imageDTO.getThumbnails());
                List<ProductThumbnail> thumbnails = thumbnailSaveNames.stream().map(v -> new ProductThumbnail(productId, v)).collect(Collectors.toList());
                params = setParams("thumbnails", thumbnails);
                productThumbnailMapper.saveThumbnails(params);
            }

            if(imageDTO.getInfoImages() != null){
                infoImageSaveNames = insertImageList(imageDTO.getInfoImages());
                List<ProductInfoImage> infoImages = infoImageSaveNames.stream().map(v -> new ProductInfoImage(productId, v)).collect(Collectors.toList());
                params = setParams("infoImages", infoImages);
                productInfoImageMapper.saveInfoImages(params);
            }

            productThumbnailMapper.deleteByImageNames(deleteImageDTO.getDeleteThumbnails());
            productInfoImageMapper.deleteByImageNames(deleteImageDTO.getDeleteInfoImages());

            deleteFileToImageDTO(deleteImageDTO);

        }catch (Exception e) {
            log.warn("Failed to admin patchProduct");
            e.printStackTrace();
            if(firstThumbnailSaveName != null)
                deleteImageFile(firstThumbnailSaveName);

            thumbnailSaveNames.forEach(this::deleteImageFile);
            infoImageSaveNames.forEach(this::deleteImageFile);

            throw new IllegalArgumentException("Failed patchProduct", e);
        }

        return productId;
    }

    private String imageInsert(MultipartFile file) throws Exception{
        StringBuffer sb = new StringBuffer();
        String saveName = sb.append(new SimpleDateFormat("yyyyMMddHHmmss")
                                        .format(System.currentTimeMillis())
                            )
                            .append(UUID.randomUUID())
                            .append(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")))
                            .toString();

        String saveFile = filePath + saveName;
        file.transferTo(new File(saveFile));

        return saveName;
    }

    private void deleteImageFile(String imageName) {
        File file = new File(filePath + imageName);

        if(file.exists())
            file.delete();
    }

    private List<String> insertImageList(List<MultipartFile> images) throws Exception{
        List<String> saveNames = new ArrayList<>();
        for(MultipartFile file : images)
            saveNames.add(imageInsert(file));

        return saveNames;
    }

    private void deleteFileToImageDTO(AdminProductDeleteImageDTO deleteImageDTO) {
        if(deleteImageDTO.getDeleteFirstThumbnail() != null)
            deleteImageFile(deleteImageDTO.getDeleteFirstThumbnail());

        Optional.ofNullable(deleteImageDTO.getDeleteThumbnails()).ifPresent(l -> l.forEach(this::deleteImageFile));
        Optional.ofNullable(deleteImageDTO.getDeleteInfoImages()).ifPresent(l -> l.forEach(this::deleteImageFile));
    }

    @Override
    public List<AdminProductStockDTO> getProductStockList(PageCriteria cri) {
        List<AdminProductStockInfoDTO> dto = productMapper.findAllOrderByStock(cri);
        List<String> productIds = dto.stream().map(AdminProductStockInfoDTO::getProductId).collect(Collectors.toList());
        List<ProductOption> optionList = productOptionMapper.findAllByProductId(productIds);

        return dto.stream()
                    .map(info -> {
                            String productId = info.getProductId();
                            List<AdminProductOptionDTO> options = optionList.stream()
                                    .filter(option ->
                                            productId.equals(option.getProductId())
                                    )
                                    .map(AdminProductOptionDTO::new)
                                    .collect(Collectors.toList());
                            return new AdminProductStockDTO(info, options);
                    })
                    .collect(Collectors.toList());
    }

    @Override
    public List<AdminDiscountResponseDTO> getProductDiscountList(PageCriteria cri) {
        return productMapper.findAllByDiscountList(cri);
    }

    @Override
    public int getProductDiscountListTotalElements(PageCriteria cri) {
        return productMapper.countByDiscountList(cri);
    }

    @Override
    public List<AdminDiscountProductDTO> getProductListOfClassification(String classificationId) {
        return productMapper.findAllByClassificationId(classificationId);
    }

    @Override
    public AdminDiscountSelectProductDTO getDiscountProductData(String productId) {
        Product product = productMapper.findById(productId);

        return new AdminDiscountSelectProductDTO(product);
    }

    @Override
    public String patchDiscountProduct(AdminPatchDiscountDTO discountDTO) {
        try {
            productMapper.patchDiscountProduct(discountDTO);
        }catch (Exception e) {
            log.warn("adminServiceImpl.patchDiscountProduct error");
            e.printStackTrace();

            return Result.ERROR.getResultKey();
        }

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public List<AdminOrderResponseDTO> getNewOrderList(PageCriteria cri) {
        return productOrderMapper.findAllNewOrderList(cri);
    }

    @Override
    public int getNewOrderListTotalElements(PageCriteria cri) {
        return productOrderMapper.countByNewOrderList(cri);
    }

    @Override
    public List<AdminOrderResponseDTO> getAllOrderList(PageCriteria cri) {
        return productOrderMapper.findAll(cri);
    }

    @Override
    public int getAllOrderListTotalElements(PageCriteria cri) {
        return productOrderMapper.countByAllList(cri);
    }

    @Override
    public AdminOrderDetailDTO getOrderDetail(long orderId) {
        ProductOrder productOrder = productOrderMapper.findById(orderId);
        List<AdminOrderDetailListDTO> details = productOrderDetailMapper.findAllByOrderId(orderId);
        return new AdminOrderDetailDTO(productOrder, details);
    }

    @Override
    public String patchOrderStatus(long orderId) {
        try {
            productOrderMapper.patchOrderStatus(orderId, OrderStatus.PREPARATION.getStatusStr());
        }catch (Exception e) {
            log.warn("AdminServiceImpl.patchOrderStatus error");
            e.printStackTrace();
            return Result.FAIL.getResultKey();
        }

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public List<AdminQnAListResponseDTO> getProductQnAList(PageCriteria cri, String type) {

        return productQnAMapper.findAllByAdminQnAList(cri, type);
    }

    @Override
    public int getProductQnAListTotalElements(PageCriteria cri, String type) {
        return productQnAMapper.countByAdminQnAList(cri, type);
    }

    @Override
    public AdminProductQnADetailDTO getProductQnADetail(long qnaId) {
        MyPageProductQnADetailDTO content = productQnAMapper.findDetailById(qnaId);
        List<QnAReplyListDTO> replyList = productQnAReplyMapper.findAllByQnAId(qnaId);

        return new AdminProductQnADetailDTO(content, replyList);
    }

    @Override
    public List<AdminQnAListResponseDTO> getMemberQnAList(PageCriteria cri, String type) {
        return memberQnAMapper.findAllByAdminQnAList(cri, type);
    }

    @Override
    public int getMemberQnAListTotalElements(PageCriteria cri, String type) {
        return memberQnAMapper.countByAdminQnAList(cri, type);
    }

    @Override
    public AdminMemberQnADetailDTO getMemberQnADetail(long qnaId) {
        MyPageMemberQnADetailDTO content = memberQnAMapper.findDetailById(qnaId);
        List<QnAReplyListDTO> replyList = memberQnAReplyMapper.findAllByQnAId(qnaId);

        return new AdminMemberQnADetailDTO(content, replyList);
    }

    @Override
    public List<AdminQnAClassificationResponseDTO> getQnAClassificationList() {
        List<QnAClassification> entity = qnAClassificationMapper.findAll();

        return entity.stream()
                    .map(AdminQnAClassificationResponseDTO::new)
                    .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String postProductQnAReply(QnAReplyRequestDTO replyDTO, Principal principal) {
        ProductQnAReply entity = replyDTO.toProductQnAReplyEntity(principal.getName());
        productQnAReplyMapper.insertReply(entity);
        productQnAMapper.patchStatusToTrue(entity.getQnaId());

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public String patchProductQnAReply(QnAReplyRequestDTO replyDTO) {
        try {
            productQnAReplyMapper.patchReply(replyDTO);
        }catch (Exception e) {
            log.warn("AdminServiceImpl.patchProductQnAReply error");
            e.printStackTrace();

            return Result.FAIL.getResultKey();
        }

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public String patchProductQnAStatusToComplete(long qnaId) {
        try {
            productQnAMapper.patchStatusToTrue(qnaId);
        }catch (Exception e) {
            log.warn("AdminServiceImpl.patchProductQnAStatusToComplete error");
            e.printStackTrace();

            return Result.FAIL.getResultKey();
        }

        return Result.SUCCESS.getResultKey();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String postMemberQnAReply(QnAReplyRequestDTO replyDTO, Principal principal) {
        MemberQnAReply entity = replyDTO.toMemberQnAReplyEntity(principal.getName());
        memberQnAReplyMapper.insertReply(entity);
        memberQnAMapper.patchStatusToTrue(entity.getQnaId());

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public String patchMemberQnAReply(QnAReplyRequestDTO replyDTO) {
        try {
            MemberQnAReply entity = replyDTO.toMemberQnAReplyEntity(null);
            memberQnAReplyMapper.patchContent(entity);
        }catch (Exception e) {
            log.warn("AdminServiceImpl.patchMemberQnAReply error");
            e.printStackTrace();

            return Result.FAIL.getResultKey();
        }

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public String patchMemberQnAStatusToComplete(long qnaId) {
        try {
            memberQnAMapper.patchStatusToTrue(qnaId);
        }catch (Exception e) {
            log.warn("AdminServiceImpl.patchMemberQnAStatusToComplete error");
            e.printStackTrace();

            return Result.FAIL.getResultKey();
        }

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public String postQnAClassification(String content) {
        try {
            qnAClassificationMapper.insertClassification(content);
        }catch (Exception e) {
            log.warn("AdminServiceImpl.postQnAClassification error");
            e.printStackTrace();

            return Result.FAIL.getResultKey();
        }

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public String deleteQnAClassification(long classificationId) {
        try {
            qnAClassificationMapper.deleteClassification(classificationId);
        }catch (Exception e) {
            log.warn("AdminServiceImpl.deleteQnAClassification error");
            e.printStackTrace();

            return Result.FAIL.getResultKey();
        }

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public List<AdminMemberResponseDTO> getMemberList(PageCriteria cri) {
        return memberMapper.findAllByAdminMemberList(cri);
    }

    @Override
    public int getMemberListTotalElements(PageCriteria cri) {
        return memberMapper.countByAdminMemberList(cri);
    }

    @Override
    public AdminMemberDetailDTO getMemberDetail(String userId) {
        Member entity = memberMapper.findById(userId);

        return new AdminMemberDetailDTO(entity);
    }

    @Override
    public String patchPoint(AdminMemberPointRequestDTO dto) {

        try{
            memberMapper.patchPoint(dto);
        }catch (Exception e) {
            log.warn("AdminServiceImpl.patchPoint error");
            e.printStackTrace();

            return Result.FAIL.getResultKey();
        }

        return Result.SUCCESS.getResultKey();
    }

    @Override
    public AdminPeriodSalesResponseDTO<AdminPeriodSalesListDTO> getPeriodSalesListByYear(int term) {
        List<AdminPeriodSalesListDTO> termList = productOrderMapper.findPeriodList(term);
        List<AdminPeriodSalesListDTO> contentList = new ArrayList<>();
        long yearSales = 0;
        long yearSalesQuantity = 0;
        long yearOrderQuantity = 0;

        for(int i = 1; i < 13; i++) {
            AdminPeriodSalesListDTO content = new AdminPeriodSalesListDTO(i);

            for(int j = 0; j < termList.size(); j++) {
                if(termList.get(j).getMonth() == i){
                    content = termList.get(j);
                    yearSales += content.getSales();
                    yearSalesQuantity += content.getSalesQuantity();
                    yearOrderQuantity += content.getOrderQuantity();

                    termList.remove(j);
                    break;
                }
            }
            contentList.add(content);
        }

        return new AdminPeriodSalesResponseDTO<>(contentList, String.valueOf(term), yearSales, yearSalesQuantity, yearOrderQuantity);
    }

    @Override
    public AdminPeriodSalesMonthDetailDTO getPeriodDetail(String term) {
        AdminSalesTermDTO termDTO = new AdminSalesTermDTO(term);
        log.info("getPeriodDetail! :: termDTO : {}", termDTO);
        AdminPeriodSalesStatisticsDTO monthStatistics = productOrderMapper.findPeriodStatistics(termDTO);
        List<AdminBestSalesProductDTO> bestProductList = productOrderMapper.findPeriodBestProduct(termDTO);
        List<AdminPeriodSalesListDTO> dailySalesList = getPeriodSalesList(termDTO);
        List<AdminPeriodClassificationDTO> classificationList = getClassificationSalesList(termDTO);
        termDTO.setMinusYear();
        AdminPeriodSalesStatisticsDTO lastYearStatistics = productOrderMapper.findPeriodStatistics(termDTO);

        return new AdminPeriodSalesMonthDetailDTO(
                term
                , monthStatistics
                , lastYearStatistics
                , bestProductList
                , classificationList
                , dailySalesList
        );
    }

    public List<AdminPeriodSalesListDTO> getPeriodSalesList(AdminSalesTermDTO termDTO) {
        List<AdminPeriodSalesListDTO> dailySales = productOrderMapper.findPeriodDailyList(termDTO);
        List<AdminPeriodSalesListDTO> contentList = new ArrayList<>();
        int lastDay = YearMonth.of(termDTO.getYear(), termDTO.getMonth()).lengthOfMonth();

        if(dailySales.size() != lastDay) {
            for(int i = 1; i <= lastDay; i++) {
                AdminPeriodSalesListDTO content = new AdminPeriodSalesListDTO(i);

                for(int j = 0; j < dailySales.size(); j++) {
                    if(i == dailySales.get(j).getMonth()){
                        content = dailySales.get(j);
                        dailySales.remove(j);
                        break;
                    }
                }

                contentList.add(content);
            }
        }else
            contentList = dailySales;

        return contentList;
    }

    public List<AdminPeriodClassificationDTO> getClassificationSalesList(AdminSalesTermDTO termDTO) {
        List<AdminPeriodClassificationDTO> classificationSales = productOrderDetailMapper.findPeriodClassification(termDTO);
        List<Classification> classificationList = classificationMapper.findAllIdOrderByClassificationStep();
        List<AdminPeriodClassificationDTO> response = new ArrayList<>();

        if(classificationList.size() != classificationSales.size()) {
            for(int i = 0; i < classificationList.size(); i++) {
                String id = classificationList.get(i).getId();
                AdminPeriodClassificationDTO content = new AdminPeriodClassificationDTO(id);

                for(int j = 0; i < classificationSales.size(); j++) {
                    if(id.equals(classificationSales.get(j).getClassification())){
                        content = classificationSales.get(j);
                        break;
                    }
                }

                response.add(content);
            }
        }else
            response = classificationSales;

        return response;
    }

    @Override
    public AdminPeriodSalesResponseDTO<AdminPeriodClassificationDTO> getPeriodSalesByDay(String term) {
        AdminSalesTermDTO termDTO = new AdminSalesTermDTO(term);

        AdminClassificationSalesDTO salesDTO = productOrderMapper.findDailySales(termDTO);
        List<AdminPeriodClassificationDTO> classificationList = productOrderDetailMapper.findPeriodClassification(termDTO);


        return new AdminPeriodSalesResponseDTO<>(
                classificationList
                , term
                , salesDTO.getSales()
                , salesDTO.getSalesQuantity()
                , salesDTO.getOrderQuantity()
        );
    }

    @Override
    public AdminClassificationSalesResponseDTO getPeriodClassificationList(String term, String classification) {
        AdminSalesTermDTO termDTO = new AdminSalesTermDTO(term);
        AdminClassificationSalesDTO classificationSalesDTO = productOrderDetailMapper.findPeriodClassificationSales(termDTO, classification);
        List<AdminClassificationSalesListDTO> productList = productOrderDetailMapper.findPeriodClassificationProductSales(termDTO, classification);

        return new AdminClassificationSalesResponseDTO(classification, classificationSalesDTO, productList);
    }

    @Override
    public List<AdminDailySalesOrderResponseDTO> getOrderListByDay(String term, PageCriteria cri) {
        AdminSalesTermDTO termDTO = new AdminSalesTermDTO(term);
        List<ProductOrder> orderEntity = productOrderMapper.findAllByDayToPagination(termDTO, cri);
        List<Long> orderIds = orderEntity.stream().map(ProductOrder::getId).collect(Collectors.toList());
        List<AdminSalesOrderDetailDTO> detailList = productOrderDetailMapper.findAllByOrderIds(orderIds);

        List<AdminDailySalesOrderResponseDTO> response = new ArrayList<>();

        for(int i = 0; i < orderEntity.size(); i++) {
            ProductOrder entity = orderEntity.get(i);
            List<AdminSalesOrderListDTO> detailContent = detailList.stream()
                                                            .filter(orderDetail ->
                                                                    entity.getId() == orderDetail.getOrderId()
                                                            )
                                                            .map(AdminSalesOrderListDTO::new)
                                                            .collect(Collectors.toList());
            response.add(new AdminDailySalesOrderResponseDTO(entity, detailContent));
        }

        return response;
    }

    @Override
    public int getOrderListByDayTotalCount(String term) {
        AdminSalesTermDTO termDTO = new AdminSalesTermDTO(term);
        return productOrderMapper.countBySalesOrderList(termDTO);
    }

    @Override
    public List<AdminProductSalesListDTO> getProductSalesList(PageCriteria cri) {
        return productMapper.getProductSalesList(cri);
    }

    @Override
    public int getProductSalesListTotalElements(PageCriteria cri) {
        return productMapper.getProductSalesListTotalElements(cri);
    }

    @Override
    public AdminProductSalesDetailDTO getProductSalesDetail(String productId) {
        int thisYear = LocalDate.now().getYear();

        AdminProductSalesDTO totalSalesDTO = productOrderMapper.getProductSales(productId);
        AdminSalesDTO yearSalesDTO = productOrderDetailMapper.getProductPeriodSales(thisYear, productId);
        AdminSalesDTO lastYearSalesDTO = productOrderDetailMapper.getProductPeriodSales(thisYear - 1, productId);
        List<AdminPeriodSalesListDTO> monthSalesDTO = productOrderMapper.getProductMonthPeriodSales(thisYear, productId);
        List<AdminProductSalesOptionDTO> optionTotalSalesList = productOrderDetailMapper.getProductOptionSales(null, productId);
        List<AdminProductSalesOptionDTO> optionYearSalesList = productOrderDetailMapper.getProductOptionSales(thisYear, productId);
        List<AdminProductSalesOptionDTO> optionLastYearSalesList = productOrderDetailMapper.getProductOptionSales(thisYear - 1, productId);
        List<ProductOption> productOptions = productOptionMapper.findAllOptionByProductId(productId);

        List<AdminPeriodSalesListDTO> monthSalesMappingDTO = new ArrayList<>();
        for(int i = 1; i < 13; i++) {
            AdminPeriodSalesListDTO monthContent = new AdminPeriodSalesListDTO(i);

            for (AdminPeriodSalesListDTO adminPeriodSalesListDTO : monthSalesDTO) {
                if (i == adminPeriodSalesListDTO.getMonth()) {
                    monthContent = adminPeriodSalesListDTO;
                    break;
                }
            }

            monthSalesMappingDTO.add(monthContent);
        }

        optionTotalSalesList = optionDataMapping(optionTotalSalesList, productOptions);
        optionYearSalesList = optionDataMapping(optionYearSalesList, productOptions);
        optionLastYearSalesList = optionDataMapping(optionLastYearSalesList, productOptions);

        return new AdminProductSalesDetailDTO(
                thisYear
                , totalSalesDTO
                , yearSalesDTO
                , lastYearSalesDTO
                , monthSalesMappingDTO
                , optionTotalSalesList
                , optionYearSalesList
                , optionLastYearSalesList
        );
    }

    public List<AdminProductSalesOptionDTO> optionDataMapping(List<AdminProductSalesOptionDTO> optionList, List<ProductOption> productOption) {
        List<AdminProductSalesOptionDTO> returnDTOList = new ArrayList<>();

        if(productOption.size() != optionList.size()){
            for(int i = 0; i < productOption.size(); i++){
                ProductOption option = productOption.get(i);
                AdminProductSalesOptionDTO optionDTO = new AdminProductSalesOptionDTO(
                        option
                        , 0
                        , 0
                );
                for(int j = 0; j < optionList.size(); j++) {
                    if(option.getId() == optionList.get(j).getOptionId()){
                        optionDTO = optionList.get(j);
                        break;
                    }
                }
                returnDTOList.add(optionDTO);
            }

            return returnDTOList;
        }else
            return optionList;
    }

    @Override
    public List<AdminReviewListDTO> getReviewList(PageCriteria cri) {
        return productReviewMapper.findAllByAdminList(cri);
    }

    @Override
    public int getReviewListTotalElements(PageCriteria cri) {
        return productReviewMapper.countAdminReviewListTotalElements(cri);
    }

    @Override
    public AdminReviewDetailResponseDTO getReviewDetail(long reviewId) {
        AdminReviewDetailDTO reviewContent = productReviewMapper.findDetailByReviewId(reviewId);
        AdminReviewReplyDTO replyContent = productReviewReplyMapper.findDetailByReviewId(reviewId);
        return new AdminReviewDetailResponseDTO(reviewContent, replyContent);
    }

    @Override
    public String postReviewReply(long reviewId, String content) {
        try{
            productReviewReplyMapper.saveReply(reviewId, content, "admin");
        }catch (Exception e) {
            log.warn("AdminServiceImpl.postReviewReply error");
            e.printStackTrace();

            return Result.FAIL.getResultKey();
        }
        return Result.SUCCESS.getResultKey();
    }
}
