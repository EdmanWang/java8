package com.yangxin.demo.stream;


import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author yangxin
 * @time 2019/8/6  11:28
 */
public interface  Collector<T, A, R> {
    /**
     * 创建一个新的结果容器.
     * 创建一个空的结果容器Supplier也就是一个无参数函数，调用它时会创建一个空的
     *      累加器实例。供数据收集使用。
     *
     * @return 返回一个结果容器
     */
    Supplier<A> supplier();

    /**
     * 这个函数就是：把一个值塞进结果容器中
     *
     * 该方法将会返回一个执行规约操作的函数。当遍历到第 n 个元素的时候，这个函数
     * 执行会有两个参数：
     *      保存归约结果的累加器(已经收集流的前n-1个)，还有第n个元素本身
     * @return 将一个值放在结果容器中
     */
    BiConsumer<A, T> accumulator();

    /**
     * 合并两个结果容器
     * 这个函数接受两个局部结果参数并且合并他们，这个combiner函数有可能将一个参数的状态
     * 转成另外一个状态，或者直接返回一个新的结果
     *
     * 在supplier、accumulator、finisher后再加上这个combiner函数，那么就可以进行并行规约了。
     * 这有点像 fork/join，
     * 1、原始流以递归的方式拆分，直到拆分到不能拆为止(不可过分拆分，不是越小的单位效率越高)
     * 2、所有子流都并行处理，
     * 3、然后使用combiner方法把结果两两合并。
     * 以上像极了fork/join框架
     *
     * @return 返回这个函数将合并两个局部结果成一个总的结果
     */
    BinaryOperator<A> combiner();

    /**
     * 对结果容器的最终转换，
     * 将A转换成R
     * 在遍历完流后，finisher方法必须返回在累积过程的最后要调用的一个函数
     * 以便将累加器对象转换为整个集合操作的最终结果。
     * 其实仅仅是 supplier函数、accumulator函数、finisher函数就可以对流进行顺序规约了。
     *
     * supplier首先创建一个空的结果容器，然后判断流中是否有多个项目，如果有，将结构累加
     * 器和下一个项目作为参数给 accumulator，再判断流中是否有多个项目，没有就将 累加器给
     * finisher完成最后的结果转换。
     *
     * 如果特性设置的是{@code IDENTITY_TRANSFORM}，这个函数的主要功能是A->R
     * from {@code A} to {@code R}.
     *
     * @return 返回一个最终的转换结果
     */
    Function<A, R> finisher();

    /**
     * 返回一系列的行为参数{@code Collector.Characteristics}的枚举，并且这些行为参数不可变。
     *
     * @return 返回一套不可变的行为参数。
     */
    Set<Characteristics> characteristics();

    /**
     * 通过{@code supplier}给定的收集器返回一个新的收集器 {@code Collector}
     *
     * {@code accumulator}和{@code combiner}两个函数.这个结果容器{@code Collector}有一个
     * 特性 {@code Collector.Characteristics.IDENTITY_FINISH}
     *
     * @param supplier supplier函数提供的一个新的收集器
     * @param accumulator accumulator函数提供一个新的累加器收集器
     * @param combiner 组合函数的新收集器
     * @param characteristics 新收集器的特性/行为
     * @param <T> 泛型T表示新收集器的输入元素类型
     * @param <R> 中间结果其和最终结果的类型
     * @throws NullPointerException 参数为空会有一个空指针异常
     * @return 返回新的收集器 {@code Collector}
     */
    public static<T, R> Collector<T, R, R> of(Supplier<R> supplier,
                                              BiConsumer<R, T> accumulator,
                                              BinaryOperator<R> combiner,
                                              Characteristics... characteristics) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(accumulator);
        Objects.requireNonNull(combiner);
        Objects.requireNonNull(characteristics);
        Set<Characteristics> cs = (characteristics.length == 0)
                ? Collectors.CH_ID
                : Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH,
                characteristics));
        return new Collectors.CollectorImpl<>(supplier, accumulator, combiner, cs);
    }

    /**
     * Returns a new {@code Collector} described by the given {@code supplier},
     * {@code accumulator}, {@code combiner}, and {@code finisher} functions.
     *
     * @param supplier The supplier function for the new collector
     * @param accumulator The accumulator function for the new collector
     * @param combiner The combiner function for the new collector
     * @param finisher The finisher function for the new collector
     * @param characteristics The collector characteristics for the new
     *                        collector
     * @param <T> The type of input elements for the new collector
     * @param <A> The intermediate accumulation type of the new collector
     * @param <R> The final result type of the new collector
     * @throws NullPointerException if any argument is null
     * @return the new {@code Collector}
     */
    public static<T, A, R> Collector<T, A, R> of(Supplier<A> supplier,
                                                 BiConsumer<A, T> accumulator,
                                                 BinaryOperator<A> combiner,
                                                 Function<A, R> finisher,
                                                 Characteristics... characteristics) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(accumulator);
        Objects.requireNonNull(combiner);
        Objects.requireNonNull(finisher);
        Objects.requireNonNull(characteristics);
        Set<Characteristics> cs = Collectors.CH_NOID;
        if (characteristics.length > 0) {
            cs = EnumSet.noneOf(Characteristics.class);
            Collections.addAll(cs, characteristics);
            cs = Collections.unmodifiableSet(cs);
        }
        return new Collectors.CollectorImpl<>(supplier, accumulator, combiner, finisher, cs);
    }

    /**
     * Characteristics indicating properties of a {@code Collector}, which can
     * be used to optimize reduction implementations.
     */
    enum Characteristics {
        /**
         * accumulator函数可以从多个线程同时调用，且该收集器可以并行归约流。如
         * 果收集器没有标为UNORDERED，那它仅在用于无序数据源时才可以并行归约
         */
        CONCURRENT,

        /**
         * 归约结果不受流中项目的遍历和累积顺序的影响
         */
        UNORDERED,

        /**
         * 这表明完成器方法返回的函数是一个恒等函数，可以跳过。这种情况下，
         * 累加器对象将会直接用作归约过程的最终结果。这也意味着，将累加器A
         * 不加检查地转换为结果R是安全的
         */
        IDENTITY_FINISH
    }

}
