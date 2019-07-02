package designpattern.singletonpattern;

public class StarvingSingletonStatic {
    private static StarvingSingletonStatic starvingSingletonStatic;

    static {
        starvingSingletonStatic = new StarvingSingletonStatic();
    }

    //私有化构造函数
    private StarvingSingletonStatic(){

    }

    //对外提供访问对象的函数
    public static StarvingSingletonStatic getInstance(){
        return starvingSingletonStatic;
    }
}
