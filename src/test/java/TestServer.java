import learn.Server;
import org.junit.Test;

/**
 * Created by m on 17-1-8.
 */
public class TestServer {
    Server server = new Server(8080);

    @Test
    public void testServer() {
        server.await();
    }
}
