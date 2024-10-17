package org.shop.mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.shop.domain.dto.cart.business.CartMemberDTO;
import org.shop.domain.dto.cart.out.CartDetailResponseDTO;
import org.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring-config/applicationContext.xml")
@Log4j
public class CartServiceTests {

    @Setter(onMethod_ = @Autowired)
    private CartService cartService;

    @Test
    public void getDetail() throws Exception{
        CartMemberDTO cartMemberDTO = new CartMemberDTO("coco", null);

        CartDetailResponseDTO dto = cartService.getCartDetail(cartMemberDTO);

        System.out.println("price : " + dto.getTotalPrice());
        System.out.println("content");
        dto.getContent().forEach(System.out::println);
    }

}
