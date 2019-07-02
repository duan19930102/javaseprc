package io;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

public class FileChannelTest {
    public static void main(String[] args) throws Exception {
        FileChannel fileChannel = new RandomAccessFile("d://atemp.txt","rw").getChannel();
        fileChannel.position(fileChannel.size()); //移动文件指针到末尾（追加写入）

        ByteBuffer byteBuffer = ByteBuffer.allocate(20);

        //数据写入buffer
        byteBuffer.put("hello world".getBytes(StandardCharsets.UTF_8));
        byteBuffer.flip();//将limit设置为position,然后position设置为0
        //byteBuffer.hasRemaining() 判断缓冲区中是否还有数据
        while (byteBuffer.hasRemaining()) {
            fileChannel.write(byteBuffer);
        }

        fileChannel.position(0);//移动文件指针到开头
        CharBuffer charBuffer = CharBuffer.allocate(10);
        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();

        //读取所有的数据
        byteBuffer.clear();
        while (fileChannel.read(byteBuffer) != -1|| byteBuffer.position() >0) {
            byteBuffer.flip();
            charBuffer.clear();
            decoder.decode(byteBuffer,charBuffer,false);
            System.out.print(charBuffer.flip().toString());
            byteBuffer.compact();
        }
        fileChannel.close();



        
    }
}
