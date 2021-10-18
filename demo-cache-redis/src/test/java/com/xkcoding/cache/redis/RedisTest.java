package com.xkcoding.cache.redis;

import com.xkcoding.cache.redis.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * <p>
 * Redis测试
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-15 17:17
 */
@Slf4j
public class RedisTest extends SpringBootDemoCacheRedisApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisCacheTemplate;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    public RedissonClient redissonClient;

    @Test
    public void testInsert(){
        User user = new User(28L,"LiYi");
        redisUtil.set("LiYi",user);
        redisUtil.incr("stock",1);
    }
    @Test
    public void testRedission(){
        String key = "dec_store_lock_";
        RLock lock = redissonClient.getLock(key);
        try {
            lock.lock();
            if (redisUtil.get("stock")!= null ||  (int) redisUtil.get("stock") > 0) {
                redisUtil.decr("stock", 1);
            }
        } catch (Exception e) {
            e.getMessage();
        }finally{
            // 解锁
            lock.unlock();
        }
    }


    /**
     * 1. 操作 String ：
     */
    @Test
    public void testData() {
        // 插入 字符串
        RBucket<String> keyObj = redissonClient.getBucket("keyStr");
        keyObj.set("testStr", 300l, TimeUnit.SECONDS);
        //查询 字符串
        RBucket<String> keyGet = redissonClient.getBucket("keyStr");
        System.out.println(keyGet.get());


        // 插入 list
        List<Integer> list = redissonClient.getList("list");
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        //查询 list
        List<Integer>  listGet = redissonClient.getList("list");
        System.out.println(listGet.toString());



        //插入 map
        RMap<Object, Object> addMap = redissonClient.getMap("addMap");
        addMap.put("man1","a");
        addMap.put("man2","b");
        addMap.put("man3","c");

        //查询 map
        RMap<Object, Object> mapGet = redissonClient.getMap("addMap");
        System.out.println(mapGet.get("man1"));



        //设置 set
        RSet<Object> testSet = redissonClient.getSet("testSet");
        testSet.add("S");
        testSet.add("D");
        testSet.add("F");
        testSet.add("G");

        //查询 set
        RSet<Object> setGet = redissonClient.getSet("testSet");
        System.out.println(setGet.readAll());

    }


    /**
     * 测试 Redis 操作
     */
    @Test
    public void get() {
        // 测试线程安全，程序结束查看redis中count的值是否为1000
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        IntStream.range(0, 1000).forEach(i -> executorService.execute(() -> stringRedisTemplate.opsForValue().increment("count", 1)));

        stringRedisTemplate.opsForValue().set("k1", "v1");
        String k1 = stringRedisTemplate.opsForValue().get("k1");
        log.debug("【k1】= {}", k1);

        // 以下演示整合，具体Redis命令可以参考官方文档
        String key = "xkcoding:user:1";
        redisCacheTemplate.opsForValue().set(key, new User(1L, "user1"));
        // 对应 String（字符串）
        User user = (User) redisCacheTemplate.opsForValue().get(key);
        log.debug("【user】= {}", user);
    }
}
