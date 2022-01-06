package com.xkcoding.mq.kafka.config;

import com.xkcoding.mq.kafka.constants.KafkaConsts;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;

/**
 * <p>
 * kafka配置类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-01-07 14:49
 */
@Configuration
@EnableConfigurationProperties({KafkaProperties.class})
@EnableKafka
@AllArgsConstructor
public class KafkaConfig {
    private final KafkaProperties kafkaProperties;

    /**
     * 生产者模板
     * @return
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * 生产者工厂
     * @return
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        //设置其为批量消费并设置并发量为3，这个并发量根据分区数决定，必须小于等于分区数，否则会有线程一直处于空闲状态。
        factory.setConcurrency(KafkaConsts.DEFAULT_PARTITION_NUM);
        //批量消费
        //设置为批量消费，每个批次数量在Kafka配置参数中设置ConsumerConfig.MAX_POLL_RECORDS_CONFIG
        factory.setBatchListener(true);
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }

    /**创建消费者工厂
     *
     * @return
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties());
    }

    @Bean("ackContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> ackContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        /**
         * //
         * AckMode 如下:
         *
         * RECORD :当listener一读到消息，就提交offset
         *
         * BATCH : poll() 函数读取到的所有消息,就提交offset
         *
         * TIME : 当超过设置的ackTime ，即提交Offset
         *
         * COUNT ：当超过设置的COUNT，即提交Offset
         *
         * COUNT_TIME ：TIME和COUNT两个条件都满足，提交offset
         *
         * MANUAL ： Acknowledgment.acknowledge()即提交Offset，和Batch类似
         *
         * MANUAL_IMMEDIATE： Acknowledgment.acknowledge()被调用即提交Offset
         */
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.setConcurrency(KafkaConsts.DEFAULT_PARTITION_NUM);
        return factory;
    }

    /**
     *  消费者批量工程
     */
    @Bean
    public KafkaListenerContainerFactory<?> batchFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        //设置为批量消费，每个批次数量在Kafka配置参数中设置ConsumerConfig.MAX_POLL_RECORDS_CONFIG
        factory.setBatchListener(true);
        return factory;
    }


    /**
     * 我们可以通过添加一个自定义的过滤器来配置监听器来消费特定类型的消息。这可以通过给KafkaListenerContainerFactory设置一个RecordFilterStrategy来完成。
     *
     * @Bean
     * @return
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>
    filterKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, String> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setRecordFilterStrategy(
            record -> record.value().contains("World"));
        return factory;
    }


}
