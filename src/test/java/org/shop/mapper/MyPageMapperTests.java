package org.shop.mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.shop.domain.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring-config/applicationContext.xml")
@Log4j
public class MyPageMapperTests {

    @Setter(onMethod_ = @Autowired)
    private MyPageMapper mapper;

    @Test
    public void getModifyInfo(){
        MemberVO vo = mapper.getModifyInfo("user1");

        log.info(vo);


    }
}
