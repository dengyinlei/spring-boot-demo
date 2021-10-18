package com.xkcoding.async.task;

public class TaskB {
    public Integer compute(Integer intA) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (intA * 2);
    }
}
