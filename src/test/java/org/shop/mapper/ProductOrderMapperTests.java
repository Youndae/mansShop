package org.shop.mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.shop.domain.dto.admin.order.out.AdminOrderResponseDTO;
import org.shop.domain.dto.admin.sales.business.AdminPeriodSalesListDTO;
import org.shop.domain.dto.paging.PageCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring-config/applicationContext.xml",
                        "file:src/main/webapp/WEB-INF/spring-config/security-context.xml"})
@Log4j
public class ProductOrderMapperTests {

    @Setter(onMethod_ = @Autowired)
    private ProductOrderMapper mapper;

    @Test
    public void getListOrderByStock() {
        PageCriteria cri = new PageCriteria(1, 20, null, null);
        List<AdminOrderResponseDTO> dto = mapper.findAllNewOrderList(cri);

        System.out.println(dto);
    }

    @Test
    public void getAllList() {
        PageCriteria cri = new PageCriteria(1, 20, null, null);

        List<AdminOrderResponseDTO> dto = mapper.findAll(cri);

        System.out.println(dto);
    }

    @Test
    public void getPeriodList() {
        int term = 2024;
        List<AdminPeriodSalesListDTO> list = mapper.findPeriodList(term);
        list.forEach(System.out::println);
    }
}
