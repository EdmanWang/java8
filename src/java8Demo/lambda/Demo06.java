package java8Demo.lambda;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

// 认识bifunction
public class Demo06 {

    public static void main(String[] args) {
        System.out.println(apply(1, 2, (a, b) -> a + b));
        System.out.println(addThen(3, 2, (a, b) -> a * b, value -> 2 * value));
    }

    // 认识apply
    public static Integer apply(int a, int b, BiFunction<Integer, Integer, Integer> biFunction) {
        return biFunction.apply(a, b);
    }

    // 认识addThen
    public static Integer addThen(int a, int b, BiFunction<Integer, Integer, Integer> biFunction, Function<Integer, Integer> function) {
        return biFunction.andThen(function).apply(a, b);
    }
}
