package com.xkcoding.mq.kafka.controller;

import com.xkcoding.mq.kafka.entity.Book;
import com.xkcoding.mq.kafka.service.BookProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/kafka")
@Slf4j
public class SendController {
    @Autowired
    private BookProducerService bookProducerService;
    private AtomicLong atomicLong = new AtomicLong();

    @Value("${kafka.topics[0].name}")
    String myTopic;
    @GetMapping(value = "/send")
    public String send() {
        for (int i = 0; i < 10; i++) {
            Long id = atomicLong.addAndGet(1);
            log.info(myTopic);
            bookProducerService.sendMessage(myTopic, new Book(atomicLong.addAndGet(1), "book-" + id));
        }
        return "success";
    }
}
