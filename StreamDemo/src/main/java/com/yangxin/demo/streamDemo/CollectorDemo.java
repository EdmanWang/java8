package com.yangxin.demo.streamDemo;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


import static java.util.stream.Collectors.partitioningBy;

/**
 * @author yangxin
 * @time 2019/8/8  10:55
 */
public class CollectorDemo {

    public Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(partitioningBy(e -> isPrim(e)));
    }

    public boolean isPrim(int n) {
        int candidateRoot = (int) Math.sqrt((double) n);
        return IntStream.rangeClosed(2, candidateRoot).noneMatch(i -> n % i == 0);
    }

    public static void main(String[] args) {




        Runnable runnable2 = () -> {
            int n = 2;
            System.out.println("n: " + n);
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int n = 2;
                System.out.println("word" + n);
            }
        };
        runnable.run();
        runnable2.run();


      /*  Runnable runnable=new Runnable() {
            @Override
            public void run() {
                System.out.println("word");
            }
        };
        Runnable runnable1 = () -> System.out.println("hello");
        runnable.run();*/
    }

}
