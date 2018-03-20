package com.servlet;/**
 * Created by Tony on 2018/3/19.
 */

import com.tony.util.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.servlet.http.HttpServlet;

/**
 * @author Tony
 * @create 2018-03-19 上午9:30
 **/
public class RedisServlet implements ApplicationListener<ContextRefreshedEvent> {

    public RedisServlet() {
        super();
    }
    @Autowired
    private RedisCacheUtil redisCacheUtil;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

    }
}
