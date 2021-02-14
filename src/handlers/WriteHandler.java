package handlers;

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
public class WriteHandler implements Handler<SelectionKey> {

    private final Map<SocketChannel, Queue<ByteBuffer>> pendingData;

    public WriteHandler(Map<SocketChannel, Queue<ByteBuffer>> pendingData) {
        this.pendingData = pendingData;
    }

    @Override
    public void handle(SelectionKey selectionKey) throws IOException {
        SocketChannel sc = (SocketChannel) selectionKey.channel();
        Queue<ByteBuffer> queue = pendingData.get(sc);
        while (!queue.isEmpty()) {
            ByteBuffer buf = queue.peek();
            int written = sc.write(buf);
            if (written == -1) {
                sc.close();
                System.out.println("Disconnected from " + sc + " (in write)");
                pendingData.remove(sc);
                return;
            }
            if (buf.hasRemaining()) return;
            queue.remove();
        }
        selectionKey.interestOps(SelectionKey.OP_READ);
    }
}
