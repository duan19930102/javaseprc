package io.nio;

import org.omg.CORBA.TIMEOUT;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server {
    private static final int TIMEOUT = 3000;
    public static void servernio(){
        Selector selector = null;
        ServerSocketChannel serverSocketChannel = null;

        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1",9999));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                if(selector.select(TIMEOUT)==0) {
                    System.out.println("===");
                    continue;
                }

                Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
                while (selectionKeyIterator.hasNext()) {
                    SelectionKey selectionKey = selectionKeyIterator.next();
                    if(selectionKey.isAcceptable()) {
                        handleAccept(selectionKey);
                    }

                    if(selectionKey.isReadable()) {

                    }

/*                    if(selectionKey.isWritable() && shenselectionKey.isValid()) {

                    }*/

                    if(selectionKey.isConnectable()) {
                        System.out.println("isConnectable = true");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void handleAccept(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selectionKey.selector(),selectionKey.OP_READ, ByteBuffer.allocate())
    }
}
