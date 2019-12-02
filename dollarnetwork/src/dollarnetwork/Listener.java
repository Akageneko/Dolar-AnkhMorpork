package dollarnetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class Listener implements Runnable {
    int port = 0;
    String host = "localhost";
    List<String> listenList;

    public Listener(int port, String host, List<String> listenList) {
        this.port = port;
        this.host = host;
        this.listenList = listenList;
    }

    public void run() {
        while(true){
            try (Socket socket = new Socket(host, port)) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String answer;
                while ((answer = reader.readLine()) != null) {
                    listenList.add(answer);
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