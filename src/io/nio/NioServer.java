package io.nio;

import com.sun.org.apache.bcel.internal.generic.Select;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioServer {
    public static void main(String[] args) throws IOException {
        //创建一个selector
        Selector selector = Selector.open();

        //初始化TCP连接监听
        ServerSocketChannel listenChannel = ServerSocketChannel.open();
        listenChannel.bind(new InetSocketAddress(9999));
        listenChannel.configureBlocking(false);
        //注册selector
        listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        while (true) {
            selector.select();//阻塞,直到有监听事件发生
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();//事件的迭代器
            //通过迭代器访问select出来的channel事件
            while(keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if(key.isAcceptable()) {//有连接可以接受
                    SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
                    channel.configureBlocking(false);
                    channel.register(selector,SelectionKey.OP_READ);
                    System.out.println("与【"+channel.getRemoteAddress()+"】建立了连接");
                } else if (key.isReadable()) {//有连接可接受
                    byteBuffer.clear();

                    //读取到流末尾说明TCP连接已断开
                    //因此需要关闭通道或者取消监听READ事件
                    //否则会无限循环
                    if(((SocketChannel)key.channel()).read(byteBuffer) == -1) {
                        key.channel().close();
                        continue;
                    }

                    //按字节遍历数据
                    byteBuffer.flip();
                    while (byteBuffer.hasRemaining()) {
                        byte b = byteBuffer.get();

                        if(b==0) {
                            System.out.println();
                            byteBuffer.clear();
                            byteBuffer.put("Hello,Client!\0".getBytes());
                            byteBuffer.flip();
                            while (byteBuffer.hasRemaining()) {
                                ((SocketChannel)key.channel()).write(byteBuffer);
                            }
                        } else {
                            System.out.println((char) b);
                        }
                    }
                }
                keyIterator.remove();
            }
        }
    }


}
