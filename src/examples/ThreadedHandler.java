package examples;

import handlers.Handler;
import handlers.UncheckedIOExceptionConverterHandler;

/**
 * User: martyn
 * Date: 27/10/2017
 * Time: 17:06
 */
public class ThreadedHandler<S> extends UncheckedIOExceptionConverterHandler<S> {

    public ThreadedHandler(Handler<S> other) {
        super(other);
    }

    @Override
    public void handle(S s) {
        new Thread(() -> super.handle(s)).start();
    }
}
