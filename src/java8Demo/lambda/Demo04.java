package java8Demo.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 初识 jdk8 的stream
public class Demo04 {

    public static void main(String[] args) {
        testStream();
    }

    public static void testStream() {
        List<String> list = Arrays.asList("wgx", "qaz", "wgxqaz");

        // 使用表达式
        list.stream().map(string -> string.toUpperCase()).forEach(str -> System.out.println(str));

        // 使用函数
        list.stream().map(String::toUpperCase).forEach(str -> System.out.println(str));
    }
}
