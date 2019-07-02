package io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedStreamTest {
    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Sender sender = new Sender();



        PipedInputStream pipedInputStream = receiver.getInputStream();
        PipedOutputStream pipedOutputStream = sender.getOutputStream();

        try {
            pipedInputStream.connect(pipedOutputStream);

            receiver.start();
            sender.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
