package com.xkcoding.mq.kafka.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.GenericWebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.List;
/**
 * @description : 根据配置文件自动创建topic
 * @author : Denley
 * @date : 2021/12/21 19:24
 **/
@Configuration
public class TopicAdministrator {

    private final TopicConfigurations configurations;
    private final GenericWebApplicationContext context;

    public TopicAdministrator(TopicConfigurations configurations, GenericWebApplicationContext genericContext) {
        this.configurations = configurations;
        this.context = genericContext;
    }

    @PostConstruct
    public void init() {
        initializeBeans(configurations.getTopics());
    }

    /**
     * 程序启动时创建Topic
     * @param topics
     */
    private void initializeBeans(List<TopicConfigurations.Topic> topics) {
        /**
         * 通过注入一个 NewTopic 类型的 Bean 来创建 topic，如果 topic 已存在，则会忽略。
         */
        topics.forEach(t -> context.registerBean(t.name, NewTopic.class, t::toNewTopic));
    }
}
