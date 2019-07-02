package io;

import java.io.*;

/**
 * FileDescriptor 是“文件描述符”。
 * FileDescriptor 可以被用来表示开放文件、开放套接字等。
 * 以FileDescriptor表示文件来说：当FileDescriptor表示某文件时，我们可以通俗的将FileDescriptor看成是该文件。
 * 但是，我们不能直接通过FileDescriptor对该文件进行操作；若需要通过FileDescriptor对该文件进行操作，
 * 则需要新创建FileDescriptor对应的FileOutputStream，再对文件进行操作
 *
 *
 * (01) in  -- 标准输入(键盘)的描述符
 * (02) out -- 标准输出(屏幕)的描述符
 * (03) err -- 标准错误输出(屏幕)的描述符
 */
public class FileDescriptorTest {
    public static String fileName = "D:/test.txt";
    public static String OutText = "FileDescriptorTest";
    public static void main(String[] args) {
        testStandFD();
        testWrite(fileName);
        testReader(fileName);
    }

    /**
     *   System.out.println()
     */
    public static void testStandFD(){
        PrintStream printStream = new PrintStream(new FileOutputStream(FileDescriptor.out));
        printStream.println(OutText);
        printStream.close();
    }

    /**
     * (01) 为了说明，"通过文件名创建FileOutputStream"与“通过文件描述符创建FileOutputStream”对象是等效的
     */
    public static void testWrite(String fileName){
        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            outputStream.write('A');
            FileDescriptor fileDescriptor = outputStream.getFD();
            FileOutputStream outputStream1 = new FileOutputStream(fileDescriptor);
            outputStream1.write('a');

            if(fileDescriptor != null)
                System.err.println("fileDescriptor" + fileDescriptor + fileDescriptor.valid());
            outputStream.close();
            outputStream1.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testReader(String fileName) {
        try {
            FileInputStream fileInputStream1 = new FileInputStream(fileName);
            System.err.println("fileInputStream1.read();" + (char)fileInputStream1.read());
            FileDescriptor fileDescriptor = fileInputStream1.getFD();
            FileInputStream fileInputStream2 = new FileInputStream(fileDescriptor);
            System.err.println("fileInputStream2.read();" + (char)fileInputStream2.read());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
