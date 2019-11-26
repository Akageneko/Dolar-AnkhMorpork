
// File Name GreetingClient.java
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class DollarNetwork {

    public static class NetworkSearch implements Runnable {
        String host;
        List<String> arpTable;

        public NetworkSearch(String host, List<String> arpTable) {
            this.host = host;
            this.arpTable = arpTable;
        }

        public void run() {
            // System.out.println(host);
            try {
                if (InetAddress.getByName(host).isReachable(1000)) {
                    System.out.println(host + " is reachable");
                    arpTable.add(host);
                } else {
                    // System.out.println();
                }
            } catch (Exception e) {
                System.out.println("something wrong with " + host + "...");
            }
        }
    }


    public static class BlockchainSender implements Runnable {
        byte[] message;
        List<String> arpTable;

        public NetworkSearch(byte[] message, List<String> arpTable) {
            this.arpTable = arpTable;
            this.message = message;
        }
        public void run() {
            serverSocket = new ServerSocket(1000);  //TODO: change port!
        }
    }


    public static class NewBlockListener implements Runnable {
        public void run() {
            DatagramSocket datagramSocket = new DatagramSocket(1001);

            byte[] byteResponse = "OK".getBytes("utf8");

            while (true){

                DatagramPacket recievedPacket = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);

                datagramSocket.receive(recievedPacket);

                int length = recievedPacket.getLength();
                String message = new String(recievedPacket.getData(), 0, length, "utf8");

                // Port i host, ktory wyslal nam zapytanie
                InetAddress address = recievedPacket.getAddress();
                int port = recievedPacket.getPort();

                System.out.println(message);
                Thread.sleep(1000); //oczekiwanie nie jest niezbedne

                DatagramPacket response = new DatagramPacket(byteResponse, byteResponse.length, address, port);

                datagramSocket.send(response);

            }
        }
    }

    public static class NewBlockSender implements Runnable {
        public void run() {
            String message = "tekst";
            InetAddress serverAddress = InetAddress.getByName("1.1.1.1");
            System.out.println(serverAddress);
    
            DatagramSocket socket = new DatagramSocket(); //Otwarcie gniazda
            byte[] stringContents = message.getBytes("utf8"); //Pobranie strumienia bajtow z wiadomosci
    
            DatagramPacket sentPacket = new DatagramPacket(stringContents, stringContents.length);
            sentPacket.setAddress(serverAddress);
            sentPacket.setPort(Config.PORT);
            socket.send(sentPacket);
    
            DatagramPacket receivedPacket = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);
            socket.setSoTimeout(1010);
    
            try{
                socket.receive(receivedPacket);
                int length = receivedPacket.getLength();
                String receivedMessage = new String(receivedPacket.getData(), 0, length, "utf8");
                System.out.println("Serwer otrzymal wiadomosc: "+receivedMessage);
            }catch (SocketTimeoutException ste){
                System.out.println("Serwer nie odpowiedzial, byc moze dostal wiadomosc albo nie...");
            }
        }
    }

    public static void checkHosts(final String subnet) throws IOException {
        List<String> arpTable = new ArrayList<String>();

        for (int i = 1; i < 255; i++) {
            final String host = subnet + "." + i;
            Runnable searchRunnable = new NetworkSearch(host, arpTable);
            Thread searchThread = new Thread(searchRunnable);
            searchThread.start();
        }
    }

    public static void main(final String[] args) throws IOException {
        checkHosts("192.168.0");
    }

}
