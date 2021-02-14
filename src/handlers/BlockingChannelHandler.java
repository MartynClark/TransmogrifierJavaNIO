package handlers;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * User: martyn
 * Date: 27/10/2017
 * Time: 22:09
 */
public class BlockingChannelHandler extends DecoratorHandler<SocketChannel>{

    public BlockingChannelHandler(Handler<SocketChannel> other) {
        super(other);
    }

    @Override
    public void handle(SocketChannel sc) throws IOException {
        while(sc.isConnected()){
            super.handle(sc);
        }
    }
}
