package java8Demo.stream.demo01;

import java.util.Arrays;
import java.util.List;

// 认识stream 流
public class Test {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        Person one = new Person("zhansan", 12);
        Person two = new Person("lisi", 22);
        Person three = new Person("wangwu", 32);

        List<Person> personList = Arrays.asList(one, two, three);
        // 主要是认识stream 中的filter 方法
        personList.stream().filter(person -> person.getName().equals("lisi")).forEach(person -> System.out.println(person.getName()+"----"+person.getAge()));
    }
}
