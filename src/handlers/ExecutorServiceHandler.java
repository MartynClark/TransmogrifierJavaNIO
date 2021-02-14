package handlers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

/**
 * User: martyn
 * Date: 27/10/2017
 * Time: 17:06
 */
public class ExecutorServiceHandler<S> extends UncheckedIOExceptionConverterHandler<S> {

    private final ExecutorService pool;
    private final Thread.UncaughtExceptionHandler exceptionHandler;

    public ExecutorServiceHandler(Handler<S> other, ExecutorService pool,
                                  Thread.UncaughtExceptionHandler exceptionHandler) {
        super(other);
        this.pool = pool;
        this.exceptionHandler = exceptionHandler;
    }

    public ExecutorServiceHandler(Handler<S> other, ExecutorService pool) {
        this(other, pool, (t, e) -> System.out.println("Uncaught: " + t + " error " + e));
    }
    @Override
    public void handle(S s) {
        pool.submit(new FutureTask<>(() -> {
            super.handle(s);
            return null;
        }){
            @Override
            protected void setException(Throwable t) {
                exceptionHandler.uncaughtException(Thread.currentThread(), t);
            }

            @Override
            protected void done() {
                super.done();
            }
        });
    }
}
