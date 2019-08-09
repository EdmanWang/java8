package com.yangxin.demo.streamDemo;

import com.yangxin.demo.Factory;
import com.yangxin.demo.model.YxUser;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;


import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.toList;

/**
 * @author yangxin
 * @time 2019/8/6  15:12
 */
public class StreamDemo {
    public static void main(String[] args) {
        List<YxUser> list = Arrays.asList(
                new YxUser(1, "yanxgin", "222", "8237251670@qq.com", 1, true),
                new YxUser(2, "12", "222", "8237216670@qq.com", 1, false),
                new YxUser(3, "yan34xgin", "222", "823721670@qq.com", 0, true),
                new YxUser(4, "56", "222", "823721670@qq.com", 0, false),
                new YxUser(5, "78", "222", "82372163@qq.com", 1, false),
                new YxUser(6, "90", "222", "8237216470@qq.com", 0, false),
                new YxUser(7, "666", "222", "823721670@qq.com", 1, true)
        );


        /*new Factory((String s)->s.matches("[a-z]+"));*/
        FactoryImpl factory = new FactoryImpl((String s) -> s.matches("[a-z]+"));
        System.out.println("boolean: " + factory.execute("ddd"));

        YxUser yx = new FactoryImpl((String s) -> s.matches("[a-z]+")).processCustomer(e -> e.setUsername("杨鑫"));
        System.out.println("yx: " + yx);


        // 自定义的收集器
        List<YxUser> collect = list.stream()
                .collect(new ToListCollector<YxUser>());
        System.out.println("collect: " + collect);

        // 使用toCollection 用新的集合去收集原来的数据
        LinkedList<YxUser> collect1 = list.stream().collect(Collectors.toCollection(LinkedList::new));
        System.out.println("collect1: " + collect1);

        // StringJoiner的使用
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (YxUser x : list) {
            joiner.add(x.getUsername());
        }
        joiner.merge(joiner);
        //joiner: [yanxgin,12,yan34xgin,56,78,90,666]
        System.out.println("joiner: " + joiner);
        // joining分割字符
        list.stream().map(YxUser::getUsername).collect(Collectors.joining(","));

        // mapping 和 map的区别
        Map<String, List<Integer>> collect2 = list.stream().collect(groupingBy(YxUser::getEmail, mapping(YxUser::getSex, toList())));
        System.out.println("collect2: " + collect2);
        list.stream().map(YxUser::getUsername).forEach(System.out::println);
        // collectionAndThen
        Map<Integer, YxUser> collect3 = list.stream()
                .collect(groupingBy(YxUser::getSex, collectingAndThen(maxBy(Comparator.comparingInt(YxUser::getId)), Optional::get)));
        System.out.println("collect3: " + collect3);
        /**
         * andThen 相当于是 g(f(x))
         * compose 相当于是 f(g(x))
         * */
        List<Integer> integerList = Arrays.asList(1, 2, 2, 2, 2, 2, 4, 5, 6, 7, 8);
        Integer reduce = integerList.stream()
                .reduce(0, Integer::max);

        System.out.println("reduce" + reduce);
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> 2 * x;
        Function<Integer, Integer> h = f.andThen(g);
        Integer apply = h.apply(1);
        System.out.println("apply:" + apply);
        // counting
        long count = list.stream().count();
        System.out.println("count: " + count);
        Long collect4 = list.stream().collect(counting());
        System.out.println("collect4: " + collect4);
        // minBy
        Comparator<YxUser> comparator = Comparator.comparingInt(YxUser::getId);
        list.stream().collect(groupingBy(YxUser::getUsername, minBy(comparator)));
        // summingInt
        IntSummaryStatistics collect5 = list.stream().collect(summarizingInt(YxUser::getId));
        collect5.getAverage();
    }
}
