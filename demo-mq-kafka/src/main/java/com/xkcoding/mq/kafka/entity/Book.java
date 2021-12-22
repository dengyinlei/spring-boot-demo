package com.xkcoding.mq.kafka.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Administrator
 */

@Data
@ToString
public class Book implements Serializable {
    private Long id;
    private String name;
}
