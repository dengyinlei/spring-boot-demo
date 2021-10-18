package com.xkcoding.async.task;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.xkcoding.async.SpringBootDemoAsyncApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <p>
 * 测试任务
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-29 10:49
 */
@Slf4j
public class TaskFactoryTest extends SpringBootDemoAsyncApplicationTests {
    @Autowired
    private TaskFactory task;

    /**
     * 测试异步任务
     */
    @Test
    public void asyncTaskTest() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        Future<Boolean> asyncTask1 = task.asyncTask1();
        Future<Boolean> asyncTask2 = task.asyncTask2();
        Future<Boolean> asyncTask3 = task.asyncTask3();

        // 调用 get() 阻塞主线程
        asyncTask1.get();
        asyncTask2.get();
        asyncTask3.get();
        long end = System.currentTimeMillis();

        log.info("异步任务全部执行结束，总耗时：{} 毫秒", (end - start));
    }

    /**
     * 测试同步任务
     */
    @Test
    public void taskTest() throws InterruptedException {
        long start = System.currentTimeMillis();
        task.task1();
        task.task2();
        task.task3();
        long end = System.currentTimeMillis();

        log.info("同步任务全部执行结束，总耗时：{} 毫秒", (end - start));
    }

    @Test
    public void testGuavaThreadPool() throws InterruptedException {
        ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
//        ListenableFuture<String> listenableFuture = executor.submit(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                System.out.println(Thread.currentThread().getName() + "-女神：我开始化妆了，好了我叫你。");
//                TimeUnit.SECONDS.sleep(5);
//                return "化妆完毕了。";
//            }
//        });
//        Futures.addCallback(listenableFuture, new FutureCallback<String>() {
//            @Override
//            public void onSuccess(@Nullable String result) {
//                System.out.println(Thread.currentThread().getName()+"-future的内容:" + result);
//            }
//
//
//            @Override
//            public void onFailure(Throwable t) {
//                System.out.println(Thread.currentThread().getName()+"-女神放你鸽子了。");
//                t.printStackTrace();
//            }
//        });
        System.out.println(Thread.currentThread().getName()+"-等女神化妆的时候可以干点自己的事情。");
        Thread.currentThread().join();
    }

    @Test
    public void testThreadPool() {
        ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName()+"-supplyAsync。");
            System.out.println("hello world");
            int[] arr = new int[1];
            System.out.println(arr[2]);
            return "results";
        },executor).whenComplete((result, e) -> {
            System.out.println(Thread.currentThread().getName()+"-whenCompleteAsync。");
            System.out.println(result + " " + e);
        }).exceptionally((e) -> {
            System.out.println(Thread.currentThread().getName()+"-exceptionally。");
            System.out.println("exception " + e);
            return "exception";
        });

        // handle方法示例：
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("hello world");
            return "result";
        });
        CompletableFuture<Integer> f2 = f1.handle((r, e) -> {
            System.out.println(r);
            System.out.println("handle");
            return 1;
        });


        long startTime = System.currentTimeMillis();
        Integer intA = new TaskA().compute();
        try {
            CompletableFuture.supplyAsync(() -> new TaskB().compute(intA))
                .thenCombine(CompletableFuture.supplyAsync(() -> new TaskC().compute(intA)), (x, y) -> new TaskD().compute(x, y))
                .thenAccept(valD -> System.out.println("一共用时: " + (System.currentTimeMillis() - startTime) / 1000 + "s; 计算结果为：" + valD)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }


}
