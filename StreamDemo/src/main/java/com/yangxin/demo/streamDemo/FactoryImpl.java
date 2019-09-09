package com.yangxin.demo.streamDemo;

import com.yangxin.demo.Factory;
import com.yangxin.demo.model.YxUser;
import java.util.function.Consumer;

/**
 * @author yangxin
 * @time 2019/8/9  9:05
 */
public class FactoryImpl {

    private final Factory factory;

    public FactoryImpl(Factory factory) {
        this.factory = factory;
    }

    public boolean execute(String s) {
        return factory.execete(s);
    }
/*
    public YxUser processCustomer(Consumer<YxUser> makeCustomerHappy) {
        YxUser c = new YxUser();
        makeCustomerHappy.accept(c);
        return c;
    }*/
}
