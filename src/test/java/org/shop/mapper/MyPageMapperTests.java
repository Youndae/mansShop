package org.shop.mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.shop.domain.dto.myPage.out.LikeListDTO;
import org.shop.domain.dto.myPage.qna.out.MyPageMemberQnAListDTO;
import org.shop.domain.dto.myPage.qna.out.MyPageProductQnAListDTO;
import org.shop.domain.dto.myPage.out.MyPageReviewListDTO;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.enumeration.PageAmount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring-config/applicationContext.xml")
@Log4j
public class MyPageMapperTests {

    @Setter(onMethod_ = @Autowired)
    private MyPageMapper mapper;

    @Test
    public void getLikeList() {
        Criteria cri = new Criteria(1, PageAmount.LIKE_PRODUCT_AMOUNT.getAmount());
        String userId = "coco";
        List<LikeListDTO> content = mapper.getLikeList(userId, cri);
        int totalElements = mapper.getLikeTotalElements(userId);

        content.forEach(System.out::println);
        System.out.println("total : " + totalElements);
    }

    @Test
    public void productQnATest() {
        Criteria cri = new Criteria(1, PageAmount.DEFAULT_AMOUNT.getAmount());
        String userId = "coco";
        List<MyPageProductQnAListDTO> content = mapper.getProductQnAList(userId, cri);
        int totalElements = mapper.getProductQnATotalElements(userId);

        content.forEach(System.out::println);
        System.out.println("total : " + totalElements);
    }

    @Test
    public void memberQnATest() {
        Criteria cri = new Criteria(1, PageAmount.DEFAULT_AMOUNT.getAmount());
        String userId = "coco";
        List<MyPageMemberQnAListDTO> content = mapper.getMemberQnAList(userId, cri);
        int totalElements = mapper.getMemberQnATotalElements(userId);

        content.forEach(System.out::println);
        System.out.println("total : " + totalElements);
    }

    @Test
    public void reviewTest() {
        Criteria cri = new Criteria(1, PageAmount.DEFAULT_AMOUNT.getAmount());
        String userId = "coco";
        List<MyPageReviewListDTO> content = mapper.getReviewList(userId, cri);
        int totalElements = mapper.getReviewTotalElements(userId);

        content.forEach(System.out::println);
        System.out.println("total : " + totalElements);
    }
}
