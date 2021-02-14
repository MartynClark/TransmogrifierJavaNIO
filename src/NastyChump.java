import java.io.IOException;
import java.net.Socket;

/**
 * User: martyn
 * Date: 27/10/2017
 * Time: 14:39
 */
public class NastyChump {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket[] sockets = new Socket[3000];
        for (int i = 0; i < sockets.length; i++) {
            sockets[i] = new Socket("localhost", 2140);
        }
        Thread.sleep(10_000);
    }
}
