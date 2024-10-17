package org.shop.mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.shop.domain.dto.cart.business.CartMemberDTO;
import org.shop.domain.dto.cart.business.OptionAndCountListDTO;
import org.shop.domain.dto.cart.out.CartDetailResponseDTO;
import org.shop.domain.dto.order.OrderListRequestDTO;
import org.shop.domain.dto.order.in.ProductOrderListDTO;
import org.shop.domain.dto.order.out.OrderListDTO;
import org.shop.domain.dto.order.out.OrderListResponseDTO;
import org.shop.domain.dto.order.out.OrderProductDTO;
import org.shop.domain.dto.paging.PagingResponseDTO;
import org.shop.service.CartService;
import org.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring-config/applicationContext.xml")
@Log4j
public class OrderServiceTests {

    @Setter(onMethod_ = @Autowired)
    private OrderService orderService;

    @Setter(onMethod_ = @Autowired)
    private ProductMapper productMapper;

    @Setter(onMethod_ = @Autowired)
    private CartDetailMapper cartDetailMapper;

    @Test
    public void getProductOrder() throws Exception{
        List<Long> optionNoList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();

        optionNoList.add(5416L);
        optionNoList.add(5417L);

        countList.add(3);
        countList.add(2);

        /**
         * 88,000 20% 70,400
         *
         * 5416 -> size : s, color : white, count = 3, 211,200
         * 5417 -> size M, color white, count = 2, 140,800
         */

        OptionAndCountListDTO dto = new OptionAndCountListDTO(optionNoList, countList);

        List<OrderProductDTO> response = productMapper.findOrderProductByOptionIds(dto);

        response.forEach(System.out::println);
    }

    @Test
    public void cartIdCheck() throws Exception {
        Long checkId = cartDetailMapper.findCartIdByCartIdAndDetailId(71L);

        System.out.println(checkId);
    }

    @Test
    public void getProducts() throws Exception {
        List<Long> cartDetailIds = new ArrayList<>();
        cartDetailIds.add(71L);
        cartDetailIds.add(72L);

        /*
            3527
                BAGS20240629210038
                DummyBAGS145
                XL
                black
                count = 5
                95,000
                0%
                95,000
                475,000
            5314
                TOP20240629211605
                DummyTOP179
                L
                black
                count = 3
                79,000
                0%
                79,000
                237,000
         */
        List<OrderProductDTO> products = cartDetailMapper.findOrderProductByOptionIds(cartDetailIds);

        products.forEach(System.out::println);
    }

    @Test
    public void memberOrderListToCoco() throws Exception{
        ProductOrderListDTO dto = new ProductOrderListDTO("coco");
        OrderListRequestDTO orderDTO = new OrderListRequestDTO("3", 1);

        PagingResponseDTO<OrderListDTO> result = orderService.getMemberOrderList(dto, orderDTO);
        System.out.println(result);
    }

    @Test
    public void anonymousOrderList() throws Exception {
        ProductOrderListDTO dto = new ProductOrderListDTO("미가입자정코코", "010-2024-0930");
        OrderListRequestDTO orderDTO = new OrderListRequestDTO("3", 1);

        OrderListResponseDTO<OrderListDTO> result = orderService.getAnonymousOrderList(dto, orderDTO);
        System.out.println(result.getContent());
        System.out.println(result.getPageMaker());
        System.out.println(result);
    }
}
