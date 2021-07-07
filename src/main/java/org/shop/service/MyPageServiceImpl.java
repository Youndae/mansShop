package org.shop.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.mapper.MyPageMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j
@AllArgsConstructor
public class MyPageServiceImpl implements MyPageService{

    private MyPageMapper myPageMapper;

    @Override
    public void deleteCart(String id, List<String> pOpNoList) {
        for(int i = 0; i < pOpNoList.size(); i++){
            String pOpNo = pOpNoList.get(i);

            myPageMapper.deleteCart(id, pOpNo);
        }
    }
}
