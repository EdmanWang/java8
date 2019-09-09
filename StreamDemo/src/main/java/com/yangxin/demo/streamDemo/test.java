package com.yangxin.demo.streamDemo;

import com.yangxin.demo.model.YxUser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;

/**
 * @author yangxin
 * @类描述
 * @time 2019/9/9  9:06
 */
public class test {

    public static void main(String[] args) {
        List<YxUser> list = Arrays.asList(
                new YxUser(1, "yanxgin", 12, "8237251670@qq.com", 1, true),
                new YxUser(2, "caoxindi", 16, "2737827527@qq.com", 1, false),
                new YxUser(3, "zhangsan", 18, "334899245@qq.com", 0, true),
                new YxUser(4, "lisi", 23, "774892034@qq.com", 0, false),
                new YxUser(5, "wangwu", 66, "43892475266@qq.com", 1, false),
                new YxUser(6, "zhaoliu", 46, "54654742@qq.com", 0, false),
                new YxUser(7, "liuqi", 30, "54375396@qq.com", 1, true)
        );

        Long start = System.currentTimeMillis();
        List<YxUser> ageList = new ArrayList<>();
        for (YxUser y : list) {
            if (y.getAge() <= 30 && y.getAge() >= 18) {
                ageList.add(y);
            }
        }
        Collections.sort(ageList, new Comparator<YxUser>() {
            @Override
            public int compare(YxUser d1, YxUser d2) {
                return Integer.compare(d1.getAge(), d2.getAge());
            }
        });
        List<String> name = new ArrayList<>();
        for (YxUser d : ageList) {
            name.add(d.getUsername());
        }
        System.out.println("list: " + name);
        Long end = System.currentTimeMillis();
        System.out.println("time: " + (end - start) + "ms");

        List<String> collect = list.parallelStream()
                .filter(e -> e.getAge() <= 30 && e.getAge() >= 18)
                .sorted(comparing(YxUser::getAge))
                .map(YxUser::getUsername)
                .collect(Collectors.toList());

        System.out.println("collect: " + collect);
        Long end2 = System.currentTimeMillis();
        System.out.println("time: " + (end2 - end) + "ms");


        System.out.println("collect: " + collect);

        List<Integer> integerList = Arrays.asList(1, 2, 2, 2, 2, 2, 4, 5, 6, 7, 8);

        List<String> strings = Arrays.asList("Hello", "World");

        List<String> collect2 = strings.stream()
                .map(e -> e.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        System.out.println("collect2 : " + collect2);

        List<String[]> collect1 = strings.stream()
                .map(e -> e.split(""))
                .distinct()
                .collect(Collectors.toList());

        System.out.println("collect1 : " + collect1);

        integerList.stream()
                .sorted()
                .skip(2)
                .limit(2)
                .forEach(System.out::println);
        integerList.stream()
                .map(x -> x * x)
                .filter(x -> x % 3 == 0)
                .findFirst();

        Optional<Integer> reduce = integerList.stream()
                .reduce(Integer::max);
        System.out.println("reduce: "+ reduce.get());

        Map<String, List<YxUser>> collect3 = list.stream()
                .collect(groupingBy(YxUser::getUsername));
        System.out.println("collect3: " + collect3);

        Map<String, Map<String, List<YxUser>>> collect4 = list.stream()
                .collect(groupingBy(YxUser::getUsername, // 一级分类函数
                        groupingBy(e -> { // 二级函数
                            if (e.getId() > 5) return "hight";
                            else if (e.getId() < 4) return "small";
                            else return "midle";
                        })));
        System.out.println("collect4: " + collect4);


    }


}

