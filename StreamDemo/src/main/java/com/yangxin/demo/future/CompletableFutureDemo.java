package com.yangxin.demo.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author yangxin
 * @time 2019/8/13  18:33
 * <p>
 * ①：创建异步计算，获取计算结果
 * ②：使用非阻塞操作提升吞吐量
 * ③：设计实现异步ApI
 * ④：如何以异步的方式使用同步的API
 * ⑤：如何对两个或者多个异步操作进行流水线和合并操作
 * ⑥：如何处理异步操作的完成状态。
 * <p>
 * 一、提升程序处理速度最有效的就是编写能发挥多核的软件
 * 二、由于网络应用使用的是“混聚”的方式来完成，他有多个来源，比如某公司的地图服务、google的翻译服务
 * 但是我们在开发的过程中，不可能因为等待这些第三方服务而阻塞自己程序的运行。
 */
public class CompletableFutureDemo {
    /**
     * Future接口：创建一个异步计算，返回一个执行运算结果的应用，运算结束的时候再返回给调用方。
     * 在Future中触发那些潜在的耗时任务中解放出来。
     */

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // futureDemo();
        // 同步
/*        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            System.out.println("price" + i +": "+getPrice("s"+i));
        }
        long end=System.currentTimeMillis();
        System.out.println("同步花销："+(end-start));

        long start1 = System.currentTimeMillis();
        Future<Double> priceAsync = null;
        for (int i = 0; i < 10; i++) {
            priceAsync = getPriceAsync("s" + i);
            System.out.println("price" + i +": "+priceAsync);
        }
        long end1=System.currentTimeMillis();
        priceAsync.get();
        System.out.println("异步花销："+(end1-start1));*/

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        long start = System.currentTimeMillis();
        list.stream().filter(e -> e != null)
                .forEach(e -> e = e + 1);
        long end = System.currentTimeMillis();
        System.out.println("streamTime: " + (end - start));

        list.parallelStream().filter(e -> e != null)
                .forEach(e -> e = e + 1);
        long end1 = System.currentTimeMillis();
        System.out.println("parallelStreamTime: " + (end1 - end));


    }

    /**
     * Future创建的Callable 的异步计算，他们能够同步执行，但是调用 submit.get()后，那么程序就会被阻塞掉，
     * 等待 callable中的执行完毕后，才能执行后续的代码。不过在新的api中，get()方法，可以添加一个超时时间
     * 限制
     * 但是Future方法提供 isDone来检测异步运行是否结束。
     * <p>
     * 无论怎么样，Future有这样一个致命的缺陷，当异步计算一个长时间的计算任务，那么与此关联的操作会被因为
     * 等待这个异步任务的结果而被阻塞。
     */
    public static void futureDemo() throws InterruptedException, ExecutionException {
        ExecutorService execute = Executors.newCachedThreadPool();
        Future<String> submit = execute.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("zz");
                Thread.sleep(1000);
                System.out.println("start");
                return "Future";
            }
        });

        Thread.sleep(200);
        System.out.println("end");

        String s = submit.get();
        System.out.println("string: " + s);

        System.out.println("ending");
    }

    /**
     * CompletableFuture 是实现了Future的接口(使用Lambda表达式，流水线)
     * CompletableFuture和Future 和 stream和Collection的关系一样。
     * <p>
     * 使用CompletableFuture构建异步应用：
     * ①：为客户提供异步接口。
     * ②：使用同步api的代码(阻塞式)变为非阻塞代码。
     * ③：响应式的方式处理异步操作。
     * <p>
     * 模拟一个最佳价格查询器，
     * 查询多个在线的商店，依据给定的产品和服务找出最低的价格。
     * <p>
     * delay()函数式一个耗时操作，耗时1s
     * getPrice() 每次获取价格前都要执行耗时。
     * 如果是使用同步的方式访问价格，那么客户端会非常的慢。
     * 将同步方法 getPrice() 转成异步方法 getPriceAsync()
     */
    public static void completableFutureDemo() {

    }

    /**
     * 模拟进行查询的耗时操作
     */
    public static void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟获取价格
     */
    public static double getPrice(String name) {
        delay();
        return 1000 * name.charAt(0) + name.charAt(1);
    }

    /**
     * 在线程中为什么添加一个异常，假如在try中的执行代码块出现异常，系统会kill掉这个
     * 线程，那么在主程序中等待该条get 会被一直被阻塞下去，添加抛出异常，让CompletableFuture
     * 的completeExceptionally 来处理异常，完成这个操作。
     */
    public static Future<Double> getPriceAsync(String name) {
        CompletableFuture<Double> completableFuture = new CompletableFuture<>();
        new Thread(() -> {
            try {
                double price = calculatePrice(name);
                completableFuture.complete(price);
            } catch (Exception e) {
                completableFuture.completeExceptionally(e);
            }

        }).start();
        return completableFuture;
    }

    /**
     * 使用工厂方法来创建
     */
    public static Future<Double> getPriceAsyncByFactory(String name) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(name));
    }

    public static double calculatePrice(String name) {
        delay();
        return 1000 * name.charAt(0) + name.charAt(1);
    }
}
