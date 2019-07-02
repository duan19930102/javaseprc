package io.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CopyFileNio {
    public static void main(String[] args) {
        try {
            CopyFileUseNio("d://1.txt","d://2.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void CopyFileUseNio(String src,String dst) throws IOException {
        FileInputStream fi = new FileInputStream(new File(src));
        FileOutputStream out = new FileOutputStream(new File(dst));
        FileChannel fileChannelout = out.getChannel();
        FileChannel fileChannelin = fi.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (true) {
            //读数据
            int eof = fileChannelin.read(byteBuffer);
            if(eof==-1) {
                break;
            }

            byteBuffer.flip();
            fileChannelout.write(byteBuffer);
            byteBuffer.clear();
        }

        fileChannelin.close();
        fileChannelout.close();
        fi.close();
        out.close();
    }
}
