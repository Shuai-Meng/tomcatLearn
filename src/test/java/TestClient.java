/**
 * Created by m on 16-12-26.
 */
import org.junit.*;
import core.*;

public class TestClient {

    @Test
    public void test2() {
        Client c = new Client("localhost", 8080);
        c.setUri("/log.txt");
        c.sendRequest();

        c.setUri("/log1.txt");
        c.sendRequest();

        c.setUri("/SHUT_DOWN");
        c.sendRequest();
    }
}
