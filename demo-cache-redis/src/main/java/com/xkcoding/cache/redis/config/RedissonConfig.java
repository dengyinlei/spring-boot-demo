package com.xkcoding.cache.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @description : 
 * @author : Denley
 * @date : 2021/10/15 14:13
 **/
/**
 * redisson bean管理
 */
@Configuration
public class RedissonConfig {
    /**
     * Redisson客户端注册
     * 单机模式
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() throws IOException {

//       Config config = new Config();
//        SingleServerConfig singleServerConfig = config.useSingleServer();
//        singleServerConfig.setAddress("redis://127.0.0.1:6379");
//        singleServerConfig.setPassword("12345");
//        singleServerConfig.setTimeout(3000);
//        return Redisson.create(config)

        // 本例子使用的是yaml格式的配置文件，读取使用Config.fromYAML，如果是Json文件，则使用Config.fromJSON
        Config config = Config.fromYAML(RedissonConfig.class.getClassLoader().getResource("redisson-single.yml"));
        return Redisson.create(config);
    }


    /**
     * 主从模式 哨兵模式
     *
     **/
   /* @Bean
    public RedissonClient getRedisson() {
        RedissonClient redisson;
        Config config = new Config();
        config.useMasterSlaveServers()
                //可以用"rediss://"来启用SSL连接
                .setMasterAddress("redis://***(主服务器IP):6379").setPassword("web2017")
                .addSlaveAddress("redis://***（从服务器IP）:6379")
                .setReconnectionTimeout(10000)
                .setRetryInterval(5000)
                .setTimeout(10000)
                .setConnectTimeout(10000);//（连接超时，单位：毫秒 默认值：3000）;
        //  哨兵模式config.useSentinelServers().setMasterName("mymaster").setPassword("web2017").addSentinelAddress("***(哨兵IP):26379", "***(哨兵IP):26379", "***(哨兵IP):26380");
        redisson = Redisson.create(config);
        return redisson;
    }*/
}
