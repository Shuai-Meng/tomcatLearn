/**
 * Created by m on 16-12-26.
 */
import org.junit.*;
import learn.*;

public class TestClient {

    @Test
    public void test2() {
        Client c = new Client("127.0.0.1", 8080);
        c.sendRequest();
        c.handleResponse();
    }
}
