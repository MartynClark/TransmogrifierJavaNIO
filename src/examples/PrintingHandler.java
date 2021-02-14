package examples;

import handlers.Handler;

import java.io.*;

/**
 * User: martyn
 * Date: 27/10/2017
 * Time: 15:53
 */
public class PrintingHandler<S> extends DecoratorHandler<S> {

    public PrintingHandler(Handler<S> other) {
        super(other);
    }

    @Override
    public void handle(S s) throws IOException {
        System.out.println("Connected to " + s);
        try {
            super.handle(s);
        } finally {
            System.out.println("Disconnected from " + s);
        }
    }
}
