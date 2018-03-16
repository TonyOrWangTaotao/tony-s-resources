package com.tony.test;/**
 * Created by Tony on 2018/3/15.
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.security.Key;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 玩玩redis
 *
 * @author Tony
 * @create 2018-03-15 下午3:44
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Transactional
public class RedisTest {

    private Jedis joinJedis() {
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        //查看服务是否运行
        jedis.auth("wangtaotao520");
        System.out.println("服务正在运行 "+jedis.ping());
        return jedis;
    }
    @Test
    public void redisTest() throws Exception {
        Jedis jedis = joinJedis();
        jedis.set("name","汪涛涛");
        System.out.println("设置"+jedis.get("name")+"成功");
    }
    @Test
    public void redisTest1() throws Exception {
        Jedis jedis = joinJedis();
        System.out.println("获取"+jedis.get("name")+"成功");
    }
    @Test
    public void redisTest2() throws Exception {
        Jedis jedis = joinJedis();
        jedis.del("name");
        System.out.println("删除"+jedis.get("name")+"成功");
    }
    @Test
    public void redisTest3() throws Exception {
        Jedis jedis = joinJedis();
        jedis.lpush("site-list", "Runoob");
        jedis.lpush("site-list", "Google");
        jedis.lpush("site-list", "Taobao");
        // 获取存储的数据并输出
        List<String> list = jedis.lrange("site-list", 0 ,2);
        for(int i=0; i<list.size(); i++) {
            System.out.println("列表项为: "+list.get(i));
        }
    }
    @Test
    public void redisTest4() throws Exception {
        Jedis jedis = joinJedis();
        Set<String> set = jedis.keys("*");
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()){
            String key =iterator.next();
            System.out.println(key);
        }
    }
    @Test
    public void redisTest5() throws Exception {
        Jedis jedis = joinJedis();
        jedis.subscribe(
                new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        System.out.println(message);
                        super.onMessage(channel, message);
                    }
                }, "tao");

    }


}
