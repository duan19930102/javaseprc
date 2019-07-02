package current;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WaitNotify {
    static boolean flag = true;
    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread waitThread = new Thread(new Wait(),"NotifyThread");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);
        Thread notifyThead = new Thread(new Notify(),"NotifyThread");
        notifyThead.start();
    }

    static class Wait implements Runnable {

        @Override
        public void run() {
            //加锁
            synchronized (lock) {
                while (flag) {
                    try {
                        System.out.println(Thread.currentThread()+ "flag is true wait" +
                                new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //条件满足，完成工作
                System.out.println(Thread.currentThread()+ "flag is false running" +
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));

            }
        }
    }


    static class Notify implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread()+ "hold lock notify" +
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));

                lock.notify();
                flag = false;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (lock) {
                    System.out.println(Thread.currentThread()+ "hold lock again" +
                            new SimpleDateFormat("HH:mm:ss").format(new Date()));
                }
            }
        }
    }
}
