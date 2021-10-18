package com.xkcoding.async.task;

public class TaskD {
    public Integer compute(Integer intB, Integer intC) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (intB + intC);
    }
}
