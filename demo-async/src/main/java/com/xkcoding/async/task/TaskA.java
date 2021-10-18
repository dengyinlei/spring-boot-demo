package com.xkcoding.async.task;

public class TaskA {
    public Integer compute() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 5;
    }
}
