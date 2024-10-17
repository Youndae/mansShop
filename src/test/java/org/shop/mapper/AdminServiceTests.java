package org.shop.mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.shop.domain.dto.admin.product.out.AdminProductListDTO;
import org.shop.domain.dto.admin.product.out.AdminProductPatchDataDTO;
import org.shop.domain.dto.admin.qna.out.AdminQnAListResponseDTO;
import org.shop.domain.dto.paging.PageCriteria;
import org.shop.domain.dto.product.business.ProductOptionDTO;
import org.shop.domain.entity.Product;
import org.shop.domain.mapper.PageableRequestMapper;
import org.shop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring-config/applicationContext.xml"
, "file:src/main/webapp/WEB-INF/spring-config/security-context.xml"})
@Log4j
public class AdminServiceTests {

    @Setter(onMethod_ = @Autowired)
    private AdminService adminService;

    @Setter(onMethod_ = @Autowired)
    private ProductOptionMapper productOptionMapper;

    @Setter(onMethod_ = @Autowired)
    private ProductMapper productMapper;

    @Test
    public void getProductList() {
        PageCriteria cri = new PageCriteria(1, 20, null, null);
        List<AdminProductListDTO> dto = adminService.getProductList(cri);
        int totalElements = adminService.getProductListTotalElements(cri);

        dto.forEach(System.out::println);
        System.out.println("-----------------------");
        System.out.println("totalElements : " + totalElements);
    }

    @Test
    public void getProductSearchList() {
        PageCriteria cri = new PageCriteria(1, 20, "OUTER", null);
        List<AdminProductListDTO> dto = adminService.getProductList(cri);
        int totalElements = adminService.getProductListTotalElements(cri);

        dto.forEach(System.out::println);
        System.out.println("-----------------------");
        System.out.println("totalElements : " + totalElements);
    }

    @Test
    public void getPatchData() {
        String pid = "PANTS20240629211706";
        AdminProductPatchDataDTO result = adminService.getProductPatchData(pid);

        System.out.println(result);
    }

    @Test
    public void getProductEntity() {
        String pid = "PANTS20240629211706";
        Product product = productMapper.findById(pid);

        System.out.println(product);

        System.out.println("------------------------");

        List<ProductOptionDTO> optionList = productOptionMapper.findAllDetailByProductId(pid);
        optionList.forEach(System.out::println);
    }

    @Test
    public void getProductQnAList() {
        PageCriteria cri = PageableRequestMapper.createDefaultAmountPageCriteria(1, "coco", null);
        List<AdminQnAListResponseDTO> dto = adminService.getProductQnAList(cri, "new");

        System.out.println(dto);
    }


}
