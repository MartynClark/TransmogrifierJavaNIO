package server;

import handlers.Handler;
import handlers.PrintingHandler;
import handlers.TransmogrifyHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * User: martyn
 * Date: 27/10/2017
 * Time: 12:11
 */
public class SingleThreadedBlockingServer {

    public static void main(String[] args) throws IOException {
        final int PORT_NUMBER = 2140;
        ServerSocket ss = new ServerSocket(PORT_NUMBER);
        Handler<Socket> handler = new PrintingHandler<>(new TransmogrifyHandler());
        while (true) {
            Socket s = ss.accept(); // never null
            handler.handle(s);
        }
    }
}
