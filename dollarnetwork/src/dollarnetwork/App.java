package dollarnetwork;

import java.io.IOException;

public class App {

    public static void main(final String[] args) throws IOException {
        NetworkContainer netContainer = new NetworkContainer();
        netContainer.sendToAll("Hello from " + netContainer.hostIP + "!");
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }

                String message = netContainer.getMessage();
                if (message != null) {
                    System.out.println(message);
                }
            }
        }).start();
    }
}