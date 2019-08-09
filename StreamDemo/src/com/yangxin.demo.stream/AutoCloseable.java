/**
 *
 * 顾名思义，是自动释放资源的一个接口，
 * 说明在try声明代码块 结束的时候会自动的唤醒此接口
 *
 * */
public interface AutoCloseable {
    /**
     * 关闭这个资源，释放没有被占有不使用的资源，这个方法在对象管理器通
     * 过{@code try}的声明会自动的唤醒自己
     *
     * 当这个接口函数公开的抛出异常{@Code Exception}的时候，实现 <em>strongly</em>
     * 鼓励去声明其具体的实现，这个{@Code close}函数去抛出更详细的异常，或者关闭操作
     * 不失败不抛出异常。
     *
     * 操作的时候要非常小心，因为在关闭操作的时候很有可能失败，非常强烈的建议放弃内部资源
     * 的关闭，优先扔出异常。这个{@code close}方法 不太可能的去唤醒超过多个资源的释放。
     * 并且在资源的时候的时候减少了可能出现的问题。
     *
     * 实现这个接口有个非常强烈的建议，没有{@Code close}函数去抛出{@link InterruptedException}异常
     *
     *  这个异常和线程的中断交互，如果有{@link InterruptedException}很有可能在运行期(Runtime)发送
     *  不恰当的行为
     *
     * 一般地，如果这个会因为这个异常而造成问题，这个{@code AutoCloseable.close}函数就不要抛出异常了
     *
     * <p>Note that unlike the {@link java.io.Closeable#close close}
     * method of {@link java.io.Closeable}, this {@code close} method
     * is <em>not</em> required to be idempotent.  In other words,
     * calling this {@code close} method more than once may have some
     * visible side effect, unlike {@code Closeable.close} which is
     * required to have no effect if called more than once.
     *
     * 然后，实现这个接口，非常鼓励注意他的幂等性。
     *
     * @throws  如果资源没有关闭，抛出他的异常
     */
    void close() throws Exception;
}