package examples;

import handlers.Handler;
import handlers.PrintingHandler;
import handlers.ThreadedHandler;
import handlers.TransmogrifyHandler;

import java.io.*;
import java.net.*;

/**
 * User: martyn
 * Date: 21/01/2018
 * Time: 15:25
 */
public class SingleThreadedBlockingServer {

    public static void main(String[] args) throws IOException {
        final int PORT_NUMBER = 2140;
        ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
        Handler<Socket> handler = new ThreadedHandler<>(new PrintingHandler<>(new TransmogrifyHandler()));
        while (true) {
            Socket s = serverSocket.accept(); // never null
            handler.handle(s);
        }
    }
}
