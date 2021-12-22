package com.xkcoding.mq.kafka.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author : Denley
 * @description :
 * @date : 2021/12/22 11:32
 **/
@Service
@Slf4j
public class BookProducerService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private static Gson gson = new GsonBuilder().create();
    public void sendMessage(String topic, Object o) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, gson.toJson(o));
        // 获取发送结果
        //异步获取
        future.addCallback(result -> log.info("生产者成功发送消息到topic:{} partition:{}的消息", result.getRecordMetadata().topic(), result.getRecordMetadata().partition()),
            ex -> log.error("生产者发送消失败，原因：{}", ex.getMessage()));
    }
}
