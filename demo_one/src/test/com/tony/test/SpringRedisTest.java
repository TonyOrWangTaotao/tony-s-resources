package com.tony.test;/**
 * Created by Tony on 2018/3/18.
 */

import com.tony.domain.Assets;
import com.tony.util.RedisCacheUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Redis + Spring
 *
 * @author Tony
 * @create 2018-03-18 下午4:02
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Transactional
public class SpringRedisTest {

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Test
    @RequestMapping("testGetCache")
    public void testGetCache() {
        Map<Integer, Assets> countryMap = redisCacheUtil.getCacheIntegerMap("0");

        for (int key : countryMap.keySet()) {
            System.out.println("key = " + key + ",value=" + countryMap.get(key));
        }

    }
    @Test
    @RequestMapping("testGetCache1")
    public void testGetCache1() {
        String str = (String) redisCacheUtil.getValueOperations("tao");
        System.out.println(str);
    }

}
