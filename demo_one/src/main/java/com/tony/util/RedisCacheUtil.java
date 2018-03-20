package com.tony.util;/**
 * Created by Tony on 2018/3/18.
 */

import com.tony.domain.Assets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Redis缓存工具类(自己玩玩)
 * ValueOperations　　——基本数据类型和实体类的缓存
 * ListOperations　　 ——list的缓存
 * SetOperations　　  ——set的缓存
 * HashOperations　　 Map的缓存
 * @author Tony
 * @create 2018-03-18 下午3:37
 **/
@Service
public class RedisCacheUtil<T> {
    //@Qualifier 表明哪个类才是我们需要的  Spring的Bean的注入注解,该注解指定注入Bean的名称
    @Autowired @Qualifier("jedisTemplate")
    public RedisTemplate redisTemplate;

    /**
     *缓存基本对象,Integer String 对象等
     * @param key 缓存的key值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    public <T> ValueOperations setCacheObject(String key,T value){
        ValueOperations operation = redisTemplate.opsForValue();
        operation.set(key,value);
        return  operation;
    }

    public <T> T getValueOperations(String key){
        ValueOperations operation =  redisTemplate.opsForValue();
        return (T) operation.get(key);
    }
    public <T> HashOperations<String,Integer,T> setCacheIntegerMap(String key,Map<Integer,T> dataMap) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {

            for (Map.Entry<Integer, T> entry : dataMap.entrySet()) {

                System.out.println("======================="+"Key = " + entry.getKey() + ", Value = " + entry.getValue());
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }

        }
        return hashOperations;
    }

    public Map<Integer,T> getCacheIntegerMap(String assetsMap) {
        Map<Integer, T> map = redisTemplate.opsForHash().entries(assetsMap);
        redisTemplate.opsForValue();
        return map;
    }
}
