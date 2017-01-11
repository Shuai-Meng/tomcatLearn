import learn.Server;
import org.junit.Test;

/**
 * Created by m on 17-1-8.
 */
public class TestServer {
    private Server server;

    @Test
    public void testServer() {
        server = new Server(8080);
        server.await();
    }
}
