package org.shop.mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring-config/applicationContext.xml",
                        "file:src/main/webapp/WEB-INF/spring-config/security-context.xml"})
@Log4j
public class MemberMapperTests {

    @Autowired
    private DataSource ds;

    @Setter(onMethod_ = @Autowired)
    private MemberMapper mapper;

    @Setter(onMethod_ = @Autowired)
    private BCryptPasswordEncoder pwencoder;

    @Setter(onMethod_ = @Autowired)
    private RedisTemplate redisTemplate;

    @Test
    public void redisTest(){
        String key = "coco";
        String value = "123678";

        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        String result = stringValueOperations.get(key);

        if(result == null)
            System.out.println("equals null : " + result);
        else if(result.equals(""))
            System.out.println("equals blank");

    }


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
                pstmt.setString(2, pwencoder.encode("1234"));
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

    @Test
    public void testAdminInsert(){
        String sql = "insert into member(userId, userPw, userName, userEmail, UserPhone, userBirth) values(?, ?, ?, ?, ?, sysdate)";

            Connection con = null;
            PreparedStatement pstmt = null;

            try{
                con = ds.getConnection();
                pstmt = con.prepareStatement(sql);

                pstmt.setString(1, "admin");
                pstmt.setString(2, pwencoder.encode("1234"));
                pstmt.setString(3, "admin");
                pstmt.setString(4, "admin@google.com");
                pstmt.setString(5, "010-1000-0000");

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

    @Test
    public void testMemberAuth(){
        String sql = "insert into member_auth(userId, auth) values(?, ?)";

        for(int i = 0; i < 5; i++){
            Connection con = null;
            PreparedStatement pstmt = null;

            try{
                con = ds.getConnection();
                pstmt = con.prepareStatement(sql);

                pstmt.setString(1, "user"+i);
                pstmt.setString(2, "ROLE_MEMBER");

                pstmt.executeUpdate();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(con != null){
                    try{
                        con.close();
                    }catch (Exception e){}
                }

                if(pstmt != null){
                    try{
                        pstmt.close();
                    }catch (Exception e){}
                }
            }
        }
    }

    @Test
    public void testAdminAuth(){
        String sql = "insert into member_auth(userId, auth) values(?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = ds.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, "admin");
            pstmt.setString(2, "ROLE_ADMMIN");

            pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(con != null){
                try{
                    con.close();
                }catch (Exception e){}
            }

            if(pstmt != null){
                try{
                    pstmt.close();
                }catch (Exception e){}
            }
        }
    }

}
