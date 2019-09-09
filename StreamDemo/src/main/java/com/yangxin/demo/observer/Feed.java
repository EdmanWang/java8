package com.yangxin.demo.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangxin
 * @time 2019/8/9  13:55
 */
public class Feed implements Subject {

    private final List<Observer> list = new ArrayList<>();

    @Override
    public void registerObserver(Observer observer) {
        this.list.add(observer);
    }

    @Override
    public void notifyObservers(String sweet) {
        list.forEach(o -> o.notify(sweet));
    }
}
