package handlers;

import util.Util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Queue;

/**
 * User: martyn
 * Date: 27/10/2017
 * Time: 23:10
 */
public class ReadHandler implements Handler<SelectionKey> {

    private final Map<SocketChannel, Queue<ByteBuffer>> pendingData;

    public ReadHandler(Map<SocketChannel, Queue<ByteBuffer>> pendingData) {
        this.pendingData = pendingData;
    }

    @Override
    public void handle(SelectionKey selectionKey) throws IOException {
        SocketChannel sc = (SocketChannel) selectionKey.channel();
        ByteBuffer buf = ByteBuffer.allocate(1024);
        int read = sc.read(buf);
        if (read == -1) {
            pendingData.remove(sc);
            sc.close();
            System.out.println("Disconnected from " + sc + " (in read)");
            return;
        }
        if (read > 0) {
            Util.process(buf);
            buf.flip();
            pendingData.get(sc).add(buf);
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
    }
}
