package com.yangxin.demo.observer;

/**
 * @author yangxin
 * @time 2019/8/9  13:54
 */
public interface Subject {

    void registerObserver(Observer observer);

    void notifyObservers(String sweet);

}
