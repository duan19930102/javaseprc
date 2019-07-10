/**
 * FileName: Piped
 * Author:   Administrator
 * Date:     2019/7/9 13:41
 * Description: 管道的通信
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package current;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 〈一句话功能简述〉<br> 
 * 〈管道的通信〉
 *
 * @author Administrator
 * @create 2019/7/9
 * @since 1.0.0
 */
public class Piped {
    public static void main(String[] args) throws Exception {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        //输入输出流进行连接，否则使用的时候就会抛出IOException
        out.connect(in);
        int receive = 0;
        Thread print = new Thread(new printThread(in),"打印");
        print.start();
        try{
            while ((receive = System.in.read())!= -1) {
                out.write(receive);
            }
        }finally {
            out.close();
        }
    }

    static class printThread implements Runnable{
        private PipedReader in;
        public printThread(PipedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            int receive = 0;
            try {
                while ((receive = in.read() ) !=-1 ){
                    System.out.print((char) receive+"$");
                }
            } catch (Exception e) {

            }
        }
    }
}