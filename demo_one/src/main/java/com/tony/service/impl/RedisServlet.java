package com.tony.service.impl;/**
 * Created by Tony on 2018/3/19.
 */

import com.tony.domain.Assets;
import com.tony.service.AssetsService;
import com.tony.util.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Tony
 * @create 2018-03-19 上午9:30
 **/
@Service
public class RedisServlet implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RedisCacheUtil redisCacheUtil;
    @Autowired
    private AssetsService service;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
            //Spring启动时候缓存数据

            System.out.println("\n\n\n_________\n\n缓存数据 \n\n ________\n\n\n\n");

            List<Assets> assetsList = service.queryAssetsList();
            Map<Integer, Assets> assetsMap = new HashMap<Integer, Assets>();
            int size = assetsList.size();
            for (int i = 0; i < size; i++) {
                assetsMap.put(i, assetsList.get(i));
            }
            redisCacheUtil.setCacheIntegerMap("assetsMap", assetsMap);
            //redisCacheUtil.setCacheObject("tao","你好");
        }

}
