package com.xkcoding.mq.kafka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xkcoding.mq.kafka.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

/**
 * @description : 
 * @author : Denley
 * @date : 2021/12/22 11:44
 **/
@Service
@Slf4j
public class BookConsumerService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @KafkaListener(topics = {"${kafka.topics[0].name}"}, groupId = "denley",containerFactory = "ackContainerFactory")
    public void handleMessage(ConsumerRecord<String, String> bookConsumerRecord, Acknowledgment acknowledgment) {
        try {
            Book book = objectMapper.readValue(bookConsumerRecord.value(), Book.class);
            log.info("消费者消费topic:{} partition:{}的消息 -> {}", bookConsumerRecord.topic(), bookConsumerRecord.partition(), book.toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            // 手动提交 offset
            acknowledgment.acknowledge();
         }
    }
}
