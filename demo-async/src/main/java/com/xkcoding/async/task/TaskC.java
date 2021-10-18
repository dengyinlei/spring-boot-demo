package com.xkcoding.async.task;

public class TaskC {
    public Integer compute(Integer intA) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (intA * 3);
    }
}
