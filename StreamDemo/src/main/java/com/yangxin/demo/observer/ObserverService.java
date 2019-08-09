package com.yangxin.demo.observer;

import com.yangxin.demo.model.YxUser;
import java.util.function.Supplier;

/**
 * @author yangxin
 * @time 2019/8/9  13:59
 */
public class ObserverService {

    public static void main(String[] args) {
        Subject subject = new Feed();
        subject.registerObserver((String sweet) -> {
            if (sweet != null && sweet.contains("fix")){
                System.out.println("fix");
            }
        });
        subject.notifyObservers("fix,...");

        Supplier<YxUser> supplier=YxUser::new;
        YxUser yxUser = supplier.get();
    }

}
