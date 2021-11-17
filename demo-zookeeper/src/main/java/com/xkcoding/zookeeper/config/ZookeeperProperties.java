package com.xkcoding.zookeeper.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Denley
 * @version : 1.0
 * @className : ZookeeperProperties
 * @description : TODO
 * @date : 2021-11-17 22:05:01
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "zookeeper")
public class ZookeeperProperties {

    /** 重试次数 */
    private int retryCount;

    /** 重试间隔时间 */
    private int elapsedTimeMs;

    /**连接地址 */
    private String address;

    /**Session过期时间 */
    private int sessionTimeoutMs;

    /**连接超时时间 */
    private int connectionTimeoutMs;
}
