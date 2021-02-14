package examples;

import java.io.*;

/**
 * User: martyn
 * Date: 27/10/2017
 * Time: 14:57
 */

// Decorator
public interface Handler<S> {
    void handle(S s) throws IOException;
}
