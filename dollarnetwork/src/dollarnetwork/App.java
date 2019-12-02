package dollarnetwork;

import java.io.IOException;

public class App {

    public static void main(final String[] args) throws IOException {
        NetworkContainer netContainer = new NetworkContainer();
        netContainer.sendToAll("AAAA");
    }
}