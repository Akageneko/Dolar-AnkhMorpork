package dollarnetwork;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Sender implements Runnable {
    int port = 0;

    public Sender(int port) {
        this.port = port;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket socket = serverSocket.accept();
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                writer.println(new Date().toString());
            }
            // try {
            //     socket.close();
            //     System.out.println("The server is shut down!");
            // } catch (IOException e) { /* failed */ }
            
        } catch (IOException ex) {
            System.out.println("Server: exception " + ex.getMessage());
        }
    }
}