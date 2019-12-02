package dollarnetwork;

import java.io.IOException;

public class App {

    public static void main(final String[] args) throws IOException {
        NetworkContainer netContainer = new NetworkContainer();
        netContainer.sendToAll("AAAA");
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (Exception e) {}
            
            String message = netContainer.getMessage();
            if (message != null){
                System.out.println(message);
            }
        }).start();
    }
}