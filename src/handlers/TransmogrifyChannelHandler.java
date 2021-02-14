package handlers;

import util.*;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

/**
 * User: martyn
 * Date: 27/10/2017
 * Time: 15:00
 */
public class TransmogrifyChannelHandler implements Handler<SocketChannel> {

    @Override
    public void handle(SocketChannel sc) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(1024);
        int read = sc.read(buf);
        if (read == -1) {
            sc.close();
            return;
        }
        if (read > 0) {
            Util.process(buf);
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.println("Writing reply");
                sc.write(buf);
            }
//            buf.clear(); needed this for blockingNIOServer
        }
    }
}
