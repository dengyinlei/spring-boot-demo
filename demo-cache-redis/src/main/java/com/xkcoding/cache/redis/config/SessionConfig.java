package com.xkcoding.cache.redis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
/**
 * 从EnableRedisHttpSession注解源码可以看到默认session过期时间是1800秒，同理，我们可以自定义这个时间
 * // session托管到redis
 *     // maxInactiveIntervalInSeconds单位：秒；
 *     // RedisFlushMode有两个参数：ON_SAVE（表示在response commit前刷新缓存），IMMEDIATE（表示只要有更新，就刷新缓存）
 * maxInactiveIntervalInSeconds: 设置 Session 失效时间，使用 Redis Session 之后，
 * 原 Spring Boot 的 server.session.timeout 属性不再生效。
 * @description :
 * @author : Denley
 * @date : 2021/10/15 13:10
 **/
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
public class SessionConfig {
}
