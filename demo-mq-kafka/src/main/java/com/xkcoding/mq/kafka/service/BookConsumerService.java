package com.xkcoding.mq.kafka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xkcoding.mq.kafka.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;

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


    //声明consumerID为demo，监听topicName为topic.quick.demo的Topic
    //这个消费者的 containerFactory 是SpringBoot 提供的 kafkaListenerContainerFactory 这个bean
    // 如果在@KafkaListener属性中没有指定 containerFactory
    // 那么Spring Boot 会默认注入 name 为“kafkaListenerContainerFactory” 的 containerFactory。
    @KafkaListener(id = "demo", topics = "topic.quick.demo")
    public void listen(String msgData) {
        log.info("demo receive : " + msgData);
    }

    @KafkaListener(topics = "k010", containerFactory = "myKafkaContainerFactory")
    public void listen(String msgData, Acknowledgment ack) {
        log.info("demo receive : " + msgData);
        //手动提交
        //enable.auto.commit参数设置成false。那么就是Spring来替为我们做人工提交，从而简化了人工提交的方式。
        //所以kafka和springboot结合中的enable.auto.commit为false为spring的人工提交模式。
        //enable.auto.commit为true是采用kafka的默认提交模式。
        ack.acknowledge();
    }



    /**
     * 监听topic1主题,单条消费
     */
    @KafkaListener(topics = "topic1")
    public void listen0(ConsumerRecord<String, String> record) {
        consumer(record);
    }

    /**
     * 监听topic2主题,单条消费
     */
    @KafkaListener(topics = "${topicName.topic2}")
    public void listen1(ConsumerRecord<String, String> record) {
        consumer(record);
    }

    /**
     * 监听topic3和topic4,单条消费
     */
    @KafkaListener(topics = {"topic3", "topic4"})
    public void listen2(ConsumerRecord<String, String> record) {
        consumer(record);
    }

    /**
     * 监听topic5,批量消费
     */
    @KafkaListener(topics = "${topicName.topic2}", containerFactory = "batchFactory")
    public void listen2(List<ConsumerRecord<String, String>> records) {
        batchConsumer(records);
    }

    /**
     * 单条消费
     */
    public void consumer(ConsumerRecord<String, String> record) {
        log.debug("主题:{}, 内容: {}", record.topic(), record.value());
    }

    /**
     * 批量消费
     */
    public void batchConsumer(List<ConsumerRecord<String, String>> records) {
        records.forEach(record -> consumer(record));
    }


    @KafkaListener(topics = {"${topic.name}"}, containerFactory = "batchFactory", id = "consumer")
    public void listen(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
        log.info("batch listen size {}.", records.size());
        System.out.println("此线程消费"+records.size()+"条消息----线程名:"+Thread.currentThread().getName());
        records.forEach(record -> System.out.println("topic名称:"+record.topic()+"\n"+"分区位置:"+record.partition()+"\n"+"key:"+record.key()+"\n"+"偏移量:"+record.offset()+"\n"+"消息内容:"+record.value()));
        try {
            records.forEach(it -> consumer(it));
        } finally {
            ack.acknowledge();  //手动提交偏移量
        }
    }



}
