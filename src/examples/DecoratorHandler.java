package examples;

import handlers.Handler;

import java.io.*;

/**
 * User: martyn
 * Date: 27/10/2017
 * Time: 17:20
 */
// Decorator common abstract super class
public abstract class DecoratorHandler<S> implements Handler<S> {

    private final Handler<S> other;

    protected DecoratorHandler(Handler<S> other) {
        this.other = other;
    }

    @Override
    public void handle(S s) throws IOException {
        other.handle(s);
    }
}
