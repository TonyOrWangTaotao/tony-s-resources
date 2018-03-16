package com.tony.redis;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 暂时作废,用spring整合redis
 *
 * Created by liam on 17/4/12.
 */
public class RedisUtil {
    protected static ReentrantLock lockPool = new ReentrantLock();
    protected static ReentrantLock lockJedis = new ReentrantLock();

    protected static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    //Redis服务器IP
    private static String ADDR_ARRAY = "127.0.0.1";

    //Redis的端口号
    private static int PORT = 6379;

    //访问密码
    private static String AUTH = "wangtaotao520";
    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 100;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 20;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 5000;

    //超时时间
    private static int TIMEOUT = 10000;

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;

    /**
     * redis过期时间,以秒为单位
     */
    public final static int EXRP_HALF_HOUR = 60 * 30 ;            //半小时
    public final static int EXRP_HOUR = 60 * 60 ;            //一小时
    public final static int EXRP_DAY = 60 * 60 * 24;        //一天
    public final static int EXRP_MONTH = 60 * 60 * 24 * 30; //一个月
    public final static int EXRP_DING = 60 * 6 * 19 ;       //钉钉会在2小时内失效

    /**
     * 初始化Redis连接池
     */
    private static  void initialPool() {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
//            jedisPool = new JedisPool(config,Constant.getRedisIp(), PORT, TIMEOUT, Constant.getRedisPassword());
            jedisPool = new JedisPool(config,ADDR_ARRAY.split(",")[0], PORT, TIMEOUT, AUTH);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("First create JedisPool error : " + e);
//            try {
//                //如果第一个IP异常，则访问第二个IP
//                JedisPoolConfig config = new JedisPoolConfig();
//                config.setMaxTotal(MAX_ACTIVE);
//                config.setMaxIdle(MAX_IDLE);
//                config.setMaxWaitMillis(MAX_WAIT);
//                config.setTestOnBorrow(TEST_ON_BORROW);
//                jedisPool = new JedisPool(config, ADDR_ARRAY.split(",")[1], PORT, TIMEOUT, AUTH);
//            } catch (Exception e2) {
//                logger.error("Second create JedisPool error : " + e2);
//            }
        }
    }

    /**
     * 在多线程环境同步初始化
     */
    private static synchronized void poolInit() {
        lockPool.lock();
        try {
            if (jedisPool == null) {
                initialPool();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lockPool.unlock();
        }
    }

    public static synchronized Jedis getJedis() {
//        lockJedis.lock();
        boolean broken = false;
        if (jedisPool == null) {
            poolInit();
        }
        Jedis jedis = null;
        try {
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
            }else{
                logger.error("jedisPool > " + jedisPool);
            }
        } catch (JedisException e) {
            broken = handleJedisException(e);
            e.printStackTrace();
            logger.error("Get jedis error : " + e);
        }
//        finally {
//            returnResource(jedis,broken);
////            lockJedis.unlock();
//        }
        return jedis;
    }
    /**
     * Handle jedisException, write log and return whether the connection is broken.
     */
    private static boolean handleJedisException(JedisException jedisException) {
        if (jedisException instanceof JedisConnectionException) {
            logger.error("Redis connection " +  " lost.", jedisException);
        } else if (jedisException instanceof JedisDataException) {
            if ((jedisException.getMessage() != null) && (jedisException.getMessage().indexOf("READONLY") != -1)) {
                logger.error("Redis connection are read-only slave.", jedisException);
            } else {
                // dataException, isBroken=false
                return false;
            }
        } else {
            logger.error("Jedis exception happen.", jedisException);
        }
        return true;
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void returnResource(final Jedis jedis,boolean conectionBroken ) {
        try {

            if (jedis != null && jedisPool != null) {
                if(conectionBroken){
                    jedisPool.returnBrokenResource(jedis);
                }else{
                    jedisPool.returnResource(jedis);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    /**
     * 设置 String
     *
     * @param key
     * @param value
     */
    public synchronized static void setString(String key, String value) {
        Jedis jedis =  getJedis();
        boolean broken = false;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            getJedis().set(key, value);
        }catch (JedisException e){
            broken = handleJedisException(e);
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally {
            returnResource(jedis,broken);
        }
    }

    /**
     * 设置 过期时间
     *
     * @param key
     * @param seconds 以秒为单位
     * @param value
     */
    public static void setString(String key, int seconds, String value) {
        Jedis jedis =  getJedis();
        boolean broken = false;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            getJedis().setex(key, seconds, value);
        }catch (JedisException e){
            broken = handleJedisException(e);
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally {
            returnResource(jedis,broken);
        }
    }

    /**
     * 获取String值
     *
     * @param key
     * @return value
     */
    public static String getString(String key) {
        Jedis jedis =  getJedis();
        boolean broken = false;
        try {
            return jedis.get(key);
        }catch (JedisException e){
            broken = handleJedisException(e);
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally {
            returnResource(jedis,broken);
        }
        return null;
    }

    /**
     *
     * @param key
     * @param resources
     * @param time
     */
    public static void setList(String key,List<String> resources,Integer time){
        Jedis jedis =  getJedis();
        boolean broken = false;
        try {
            jedis.del(key);
            for(String str : resources){
                jedis.lpush(key,str);
            }
            if(time != null && time > 0)
                setExpire(key,time);
        }catch (JedisException e){
            broken = handleJedisException(e);
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally {
            returnResource(jedis,broken);
        }

    }

    /**
     * 取出权限
     * @param key
     * @return
     */
    public static List<String> getList(String key){
        Jedis jedis =  getJedis();
        boolean broken = false;
        try {
            List<String> list = jedis.lrange(key, 0, -1);
            return list;
        }catch (JedisException e){
            broken = handleJedisException(e);
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally {
            returnResource(jedis,broken);
        }

        return null;
    }

    /**
     * 设置过期时间
     * @param key
     * @param time
     */
    public static void setExpire(String key ,Integer time){
        Jedis jedis = getJedis();
        boolean broken = false;
        try {
             jedis.expire(key, time);
        }catch (JedisException e){
            broken = handleJedisException(e);
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally {
            returnResource(jedis,broken);
        }
    }

    /**
     * 通配找出key
     * @param key
     * @return
     */
    public static Set<String> getKeys(String key){
        Jedis jedis = getJedis();
        boolean broken = false;
        try {
            return jedis.keys(key);
        }catch (JedisException e){
            broken = handleJedisException(e);
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally {
            returnResource(jedis,broken);
        }
        return null;
    }

    /**
     * 批量删除
     * @param key
     */
    public static void delBatch(String key){
        Jedis jedis = getJedis();
        boolean broken = false;
        try {
            if(StringUtils.isNotBlank(key)){
                if(!key.contains("\\*"))
                    key+="*";
                Set<String> set = jedis.keys(key);
                if(set != null)
                    for(String str : set){
                        jedis.del(str);
                    }
            }
        }catch (JedisException e){
            broken = handleJedisException(e);
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally {
            returnResource(jedis,broken);
        }
    }





    public static void main(String[] args) {
        //Connecting to Redis server on localhost 172.16.2.115
        RedisUtil.setString("a",10,"1111");
        System.out.println(RedisUtil.getString("a"));

        Set<String> s = getKeys("z*");
        for(String str : s){
            System.out.println(str);
        }

        List<String> l = getList("dinga9ba4ef63c85a9db35c2f4657eb6378fpermission_resources15");
        for(String str : l  ){
            System.out.println(str);
        }

//        List<String> strs = new ArrayList<>();
//        strs.add("/a");
//        strs.add("/a");
//        strs.add("/b");
//        setPermissionResources(Constant.PERMISSIONS_RESOURCES +0,strs);
//        setExpire(Constant.PERMISSIONS_RESOURCES +0,10);
//        List<String> strings = getJedis().lrange(Constant.PERMISSIONS_RESOURCES + 0, 0, -1);
//        System.out.println(strings);


//        Jedis jedis = new Jedis("127.0.0.1",6379);
//        jedis.auth("zcl123456");
//        System.out.println("Connection to server sucessfully");
//        //check whether server is running or not
//        System.out.println("Server is running: "+jedis.ping());
//        jedis.setex("a",10,"12323232");
    }
}
