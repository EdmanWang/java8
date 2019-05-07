package java8Demo.lambda;

import java.util.Arrays;
import java.util.List;

// lambda 表达式深入学习
public class Demo03 {

    public static void main(String[] args) {
        /**
         * 1:传统的方式就是实例化初对象
         */
        test(new MyInterfaceOne() {
            @Override
            public int testOne(int a) {
                System.out.println(a + "qaz");
                return 0;
            }
        });

        System.out.println("----------------");

        /**
         * 2：使用lambda表达式
         */
        test(a -> {
            System.out.println(a + "wgx");
            return a;
        });

        System.out.println("----------------");

        // 构造函数
        test(Integer::new);

    }

    public static void test(MyInterfaceOne myInterfaceOne) {
        System.out.println(myInterfaceOne.testOne(132));
    }

    @FunctionalInterface
    private interface MyInterfaceOne {
        int testOne(int a);

        // myinterfaceOne 的实现类会继承toString方法，所以不能算是函数式接口的方法
        String toString();
    }

    @FunctionalInterface
    private interface MyInterfaceTwo {
        void testTwo();
    }
}




