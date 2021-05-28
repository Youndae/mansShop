package org.shop.mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.shop.domain.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring-config/applicationContext.xml")
@Log4j
public class MemberMapperTests {

    @Autowired
    private DataSource ds;

    @Setter(onMethod_ = @Autowired)
    private MemberMapper mapper;

    @Test
    public void testInsertMember(){
        String sql = "insert into member(userId, userPw, userName, userEmail, UserPhone, userBirth) values(?, ?, ?, ?, ?, sysdate)";



        for(int i = 0; i < 5; i++){
            Connection con = null;
            PreparedStatement pstmt = null;

            try{
                con = ds.getConnection();
                pstmt = con.prepareStatement(sql);


                pstmt.setString(1, "user" + i);
                pstmt.setString(2, "1234");
                pstmt.setString(3, "user" + i);
                pstmt.setString(4, "user@google.com");
                pstmt.setString(5, "010-0000-000"+i);

                pstmt.executeUpdate();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(pstmt != null) {
                    try{
                        pstmt.close();
                    }catch (Exception e){

                    }
                }

                if(con != null){
                    try{
                        con.close();
                    }catch (Exception e){

                    }
                }
            }
        }
    }
/*
    @Test
    public void userInfoTest(){
        MemberVO vo = mapper.getModifyInfo("user1");

        log.info(vo);

    }*/
}
