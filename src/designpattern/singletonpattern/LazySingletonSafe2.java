package designpattern.singletonpattern;
//双层校验锁
public class LazySingletonSafe2 {
    private static LazySingletonSafe2 singletonSafe2;

    //私有化构造方法
    private LazySingletonSafe2(){

    }

    //对外提供获取对象的方法
    public static  LazySingletonSafe2 getInstance(){
        if(singletonSafe2==null) {
            synchronized (LazySingletonSafe2.class) {
                if(singletonSafe2== null) {
                    singletonSafe2 = new LazySingletonSafe2();
                }
            }
        }
        return singletonSafe2;
    }
}
