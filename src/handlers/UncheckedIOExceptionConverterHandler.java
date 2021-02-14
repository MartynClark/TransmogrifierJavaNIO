package handlers;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * User: martyn
 * Date: 27/10/2017
 * Time: 16:05
 */
public class UncheckedIOExceptionConverterHandler<S> extends DecoratorHandler<S> {
    public UncheckedIOExceptionConverterHandler(Handler<S> other) {
        super(other);
    }

    @Override
    public void handle(S s){
        try{
            super.handle(s);
        }catch(IOException ex){
            throw new UncheckedIOException(ex);
        }
    }
}
