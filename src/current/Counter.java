package current;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS 中有三个值，内存值，旧期望值，更新值
 * 当内存值和旧期望值相等就设置更新值，否则不改变
 */
public class Counter {
    private int i=0;
    private AtomicInteger atomicI = new AtomicInteger();

    public static void main(String[] args) {
        final Counter cas = new Counter();
        List<Thread> ts  = new ArrayList<Thread>();
        long start= System.currentTimeMillis();
        //创建100个线程
        for (int j=0;j<100;j++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    cas.count();
                    cas.safeCount();
                }
            });

            ts.add(t);
        }
        for(Thread t:ts) {
            t.start();
        }

        //等待所有线程执行完成
        for(Thread t:ts) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(cas.i);
            System.out.println(cas.atomicI.get());
            System.out.println(System.currentTimeMillis()-start);
        }
    }

    private void safeCount() {
        for (;;) {
            int i = atomicI.get();
            boolean suc = atomicI.compareAndSet(i,++i);
            if(suc) {
                break;
            }
        }
    }

    /**
     * 非线程安全计算器
     */
    private void count() {
        i++;
    }


}
