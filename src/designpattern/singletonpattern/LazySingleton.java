package designpattern.singletonpattern;

/**
 * 懒汉单例模式 线程不安全
 */
public class LazySingleton {
    private static LazySingleton lazySingleton;

    //私有化构造方法
    private LazySingleton(){

    }

    //对外提供对象访问方式
    public static LazySingleton getInstance(){
        if(lazySingleton==null) {
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }
}
