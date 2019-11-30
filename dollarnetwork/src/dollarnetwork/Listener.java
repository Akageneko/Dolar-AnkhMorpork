package dollarnetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Listener implements Runnable {
    int port = 0;
    String host = "localhost";

    public Listener(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void run() {
        while(true){
            try (Socket socket = new Socket(host, port)) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                int outPort = Integer.parseInt("1" + String.format("%04d", Integer.parseInt(host.split("\\.")[3])));
                //new Thread(new dollarnetwork.Sender(outPort)).start();
                String answer;
                while ((answer = reader.readLine()) != null) {
                    System.out.println(answer);
                }
            } catch (UnknownHostException ex) {
                System.out.println("Listener: server not found " + ex.getMessage());
            } catch (IOException ex) {
                if (ex.getMessage().contains("timed")){
                    break;
                }
                //System.out.println("Listener: I/O error " + host + " " + ex.getMessage());
            }
        }
    }
}