package designpattern.singletonpattern;

public class LazySingletonSafe1 {
    private static LazySingletonSafe1 singletonSafe1;

    //私有化构造方法
    private LazySingletonSafe1(){

    }

    //对外提供获取对象的方法
    public static synchronized LazySingletonSafe1 getInstance(){
        if(singletonSafe1== null) {
            singletonSafe1 = new LazySingletonSafe1();
        }
        return singletonSafe1;
    }
}
