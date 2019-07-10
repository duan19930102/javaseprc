package current;

import java.util.concurrent.TimeUnit;

public class Profiler {
    //第一次get方法的时候会调用（如果set方法没有被调用），每个线程都会调用一次
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>(){
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

    public static void begin(){
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static Long end(){
        return System.currentTimeMillis()-TIME_THREADLOCAL.get();
    }

    public static void main(String[] args) throws InterruptedException {
        Profiler.begin();
        TimeUnit.SECONDS.sleep(2);
        System.out.println(Profiler.end());
    }
}