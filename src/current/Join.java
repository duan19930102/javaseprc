/**
 * FileName: Join
 * Author:   Administrator
 * Date:     2019/7/9 17:45
 * Description: Join方法的应用
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package current;

import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br> 
 * 〈Join方法的应用〉
 *
 * @author Administrator
 * @create 2019/7/9
 * @since 1.0.0
 */
public class Join {
    public static void main(String[] args) throws Exception {
        Thread previous = Thread.currentThread();
        for (int i=0; i<10;i++) {
            Thread thread = new Thread(new DominThread(previous),String.valueOf(i));
            thread.start();
            previous = thread;
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println(Thread.currentThread().getName()+" terminate");
    }

    private static class DominThread implements Runnable {
        private  Thread previous;
        public DominThread(Thread previous) {
            this.previous = previous;
        }

        @Override
        public void run() {
            try {
                previous.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " terminate.");
        }
    }
}