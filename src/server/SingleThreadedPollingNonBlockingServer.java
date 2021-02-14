package server;

import handlers.Handler;
import handlers.TransmogrifyChannelHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;

/**
 * User: martyn
 * Date: 27/10/2017
 * Time: 12:11
 */
public class SingleThreadedPollingNonBlockingServer {

    public static void main(String[] args) throws IOException {
        final int PORT_NUMBER = 2140;
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(PORT_NUMBER));
        ssc.configureBlocking(false);
        Handler<SocketChannel> handler = new TransmogrifyChannelHandler();
        Collection<SocketChannel> sockets = new ArrayList<>();
        while (true) {
            SocketChannel sc = ssc.accept(); // never null
            if (sc != null) {
                sockets.add(sc);
                System.out.println("Connected to " + sc);
                sc.configureBlocking(false);
            }
            for (SocketChannel socket : sockets) {
                if (socket.isConnected()) {
                    handler.handle(socket);
                }
            }
            sockets.removeIf(socket -> !socket.isConnected());
        }
    }
}
