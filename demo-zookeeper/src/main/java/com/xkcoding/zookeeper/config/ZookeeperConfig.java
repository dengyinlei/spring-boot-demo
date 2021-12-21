package com.xkcoding.zookeeper.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Denley
 * @version : 1.0
 * @className : ZookeeperConfig
 * @description : TODO
 * @date : 2021-11-17 22:05:28
 **/
@Configuration
public class ZookeeperConfig {
    /**
     * 创建 CuratorFramework 对象并连接 Zookeeper
     *
     * @param zookeeperProperties 从 Spring 容器载入 zookeeperProperties Bean 对象，读取连接 ZK 的参数
     * @return CuratorFramework
     */
    @Bean(initMethod = "start")
    public CuratorFramework curatorFramework(ZookeeperProperties zookeeperProperties) {
        return CuratorFrameworkFactory.newClient(
            zookeeperProperties.getAddress(),
            zookeeperProperties.getSessionTimeoutMs(),
            zookeeperProperties.getConnectionTimeoutMs(),
            new RetryNTimes(zookeeperProperties.getRetryCount(),
                zookeeperProperties.getElapsedTimeMs()));
    }
}
