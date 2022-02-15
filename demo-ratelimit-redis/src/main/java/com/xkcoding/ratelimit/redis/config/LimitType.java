package com.xkcoding.ratelimit.redis.config;

/**
 * @description : 限流类型
 * @author : Denley
 * @date : 2022/1/11 17:42
 **/
public enum LimitType {
    /**
 * 自定义key
 */
CUSTOMER,

    /**
     * 请求者IP
     */
    IP;

}
