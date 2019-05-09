package java8Demo.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

// 认识function
public class Demo05 {

    public static void main(String[] args) {
//        test01();

        // 测试四则运算 ,在调用的时候才知道具体执行的方法是啥。
        System.out.println(test02(2, value -> 2 + value));
        System.out.println(test02(2, value -> 2 - value));
        System.out.println(test02(2, value -> 2 * value));
        System.out.println(test02(2, value -> 2 / value));

        System.out.println(compose(3, value->2+value,value->value*value)); //  结果  11
        System.out.println(addThen(3, value->2+value,value->value*value)); // 结果   25
    }

    public static void test01() {
        List<String> list = Arrays.asList("qaz", "wgx", "qazwgx");

        /**
         *  认识 function 中的apply方法
         *  list 转换为stream流对象。接着就是调用map方法，map方法就是映射关系。map是接收一个function参数调用apply方法，
         *  即使输入一个param参数，给初一个返回值，再次返回一个stream 对象，再次调用foreach,foreach调用consumer 方法。调用accept
         */

        list.stream().map(str -> str.toUpperCase()).forEach(str -> System.out.println(str));
    }

    // 认识apply方法
    public static Integer test02(int a, Function<Integer, Integer> function) {
        return function.apply(a);
    }

    // 认识compose方法
    public static Integer compose(int a, Function<Integer, Integer> function1, Function<Integer, Integer> function2){
         return function1.compose(function2).apply(a);
    }

    // 认识addThen方法
    public static Integer addThen(int a, Function<Integer, Integer> function1, Function<Integer, Integer> function2){
        return function1.andThen(function2).apply(a);
    }

    // 原先是在编译的时候就先定义好
    public static Integer test03(int a) {
        return a + a;
    }

    public static Integer test04(int a) {
        return a - a;
    }

    public static Integer test05(int a) {
        return a * a;
    }

    public static Integer test06(int a) {
        return a / a;
    }
}
