package designpattern.singletonpattern;

public class StarvingSingleton {
    private static StarvingSingleton starvingSingleton = new StarvingSingleton();

    //私有化构造函数
    private StarvingSingleton(){

    }

    //对外提供对象的访问
    public static StarvingSingleton getInstance(){
        return starvingSingleton;
    }

    public static void main(String[] args) {
        StarvingSingleton starvingSingleton1 = getInstance();
        StarvingSingleton starvingSingleton2 = getInstance();
        System.err.println(starvingSingleton1==starvingSingleton2);
    }
}

