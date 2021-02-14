package server;

import handlers.BlockingChannelHandler;
import handlers.ExecutorServiceHandler;
import handlers.Handler;
import handlers.PrintingHandler;
import handlers.TransmogrifyChannelHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executors;

/**
 * User: martyn
 * Date: 27/10/2017
 * Time: 12:11
 */
public class BlockingNIOServer {

    public static void main(String[] args) throws IOException {
        final int PORT_NUMBER = 2140;
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(PORT_NUMBER));
        Handler<SocketChannel> handler = new ExecutorServiceHandler<>(
                new PrintingHandler<>(new BlockingChannelHandler(new TransmogrifyChannelHandler())),
                Executors.newFixedThreadPool(10)
        );
        while (true) {
            SocketChannel s = ssc.accept(); // never null
            handler.handle(s);
        }
    }
}
