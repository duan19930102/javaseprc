package io.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class Client {
    public static void main(String[] args) throws IOException {
        clientNio();
    }

    public static void clientNio(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        SocketChannel socketChannel = null;

        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("127.0.0.1",9999));
            //判断连接是否完成
            /**
             * isConnected是判断是否连接成功，如果连接成功则是true 否则是false
             * finishConnect是判断连接是否完成 已经连接true 不处于已连接并且不是
             * 正在连接就会抛出异常，其他情况就是false
             */
            if(socketChannel.finishConnect()) {
                int i = 0;
                while (true) {
                    TimeUnit.SECONDS.sleep(1);
                    String info = "I,am" + i + "infomation from client";
                    byteBuffer.clear();
                    byteBuffer.put(info.getBytes());
                    byteBuffer.flip();
                    //判断当前位置是否在限制范围内
                    while (byteBuffer.hasRemaining()) {
                        System.out.println(byteBuffer);
                        socketChannel.write(byteBuffer);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

                try {
                    if(socketChannel!=null) {
                        socketChannel.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }




}
