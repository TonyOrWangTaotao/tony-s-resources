package com.tony.test;/**
 * Created by Tony on 2018/3/14.
 */

import com.tony.domain.Assets;
import com.tony.service.AssetsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Tony
 * @create 2018-03-14 上午2:46
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Transactional
public class AssetsTest {
    @Autowired
    private AssetsService service;
    @Test
    public void test1(){
        List<Assets> list =service.queryAssetsList();
        for (Assets assets:list){
            System.out.println(assets);
        }
    }



}
