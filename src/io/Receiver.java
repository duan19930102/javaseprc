package io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class Receiver extends Thread{
    // 管道输入流对象。
    // 它和“管道输出流(PipedOutputStream)”对象绑定，
    // 从而可以接收“管道输出流”的数据，再让用户读取。
    private PipedInputStream in = new PipedInputStream();

    //得到管道输入流
    public PipedInputStream getInputStream() {
        return in;
    }


    @Override
    public void run() {
       // readMessageOnce() ;
        resdMessageContinued() ;
    }

    /**
     * 从管道流获取一次数据
     */
    public void readMessageOnce() {
        //虽然buf的大小是2048个字节，但最多只会从管道中获取1024个字节
        //因为管道中的缓存区的大小时1024
        byte[] buf = new byte[2048];

        try {
            int len = in.read(buf);
            System.err.println(new String(buf,0,len));
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void resdMessageContinued() {
        int total = 0;

        while (true) {
            byte[] buf = new byte[1024];

            try {
                int len = in.read(buf);
                total+=len;
                System.err.println(new String(buf,0,len));
                System.err.println(total);
                System.err.println(len);

                if(total>1024)
                   break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
