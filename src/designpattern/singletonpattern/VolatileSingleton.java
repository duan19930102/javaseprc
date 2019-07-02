package designpattern.singletonpattern;

import java.io.Serializable;

//
public class VolatileSingleton implements Serializable {
    private static volatile VolatileSingleton volatileSingleton;

    //私有化构造方法
    private VolatileSingleton(){

    }

    //对外提供获取对象的方法
    public static VolatileSingleton getInstance(){
        if(volatileSingleton==null) {
            synchronized (VolatileSingleton.class) {
                if(volatileSingleton== null) {
                    volatileSingleton = new VolatileSingleton();
                }
            }
        }
        return volatileSingleton;
    }

    private Object readResolve(){
        return volatileSingleton;
    }
}
