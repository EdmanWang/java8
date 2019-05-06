package java8Demo.lambda;

import java.util.Arrays;
import java.util.List;

// jdk 8 的list遍历
public class Demo02 {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);

        // jdk 4 的遍历
        for (int i = 0 ;i<list.size();i++){
            System.out.println(list.get(i));
        }

        System.out.println("-------------");

        // jdk 5 的遍历
        for(Integer i:list){
            System.out.println(i);
        }

        System.out.println("-------------");

        //jdk 8 的遍历
        /**
         * a : 表示遍历出来的每一个元素
         */
        list.forEach(a -> System.out.println(a));
    }
}
