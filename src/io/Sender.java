package io;

import java.io.IOException;
import java.io.PipedOutputStream;

public class Sender extends Thread {
    @Override
    public void run() {
        //writeShorMessage();
       writeLongMessage();
    }

    /**
     * 管道输出流对象。
     *      // 它和“管道输入流(PipedInputStream)”对象绑定，
     *      // 从而可以将数据发送给“管道输入流”的数据，然后用户可以从
     * “管道输入流”读取数据。
     */
    private PipedOutputStream out = new PipedOutputStream();

    /**
     *  获得“管道输出流”对象
     * @return
     */
    public PipedOutputStream getOutputStream(){
        return out;
    }

    /**
     * 向“管道输出流”中写入一则较简短的消息："this is a short message"
     */
    private void writeShorMessage() {
        String strInfo = "this is a shor message";

        try {
            out.write(strInfo.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 向“管道输出流”中写入一则较长的消息
     */
    private void writeLongMessage() {
        StringBuilder sb = new StringBuilder();

        for(int i=0;i<102;i++) {
            sb.append("0123456789");
        }
        sb.append("abcdefghijklmnopqrstuvwxyz");
        String str = sb.toString();

        try {
            out.write(str.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
