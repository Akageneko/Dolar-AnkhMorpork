package dollarnetwork;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class NetworkContainer {

    ArrayList<String> inList = new ArrayList<String>();
    ArrayList<String>[] outList = new ArrayList[255];

    public static List<String> checkHosts(String host) throws IOException {
        List<String> arpTable = new ArrayList<String>();
        String subnet = host.substring(0, host.lastIndexOf('.'));
        for (int i = 1; i < 255; i++) {
            final String ip = subnet + "." + i;
            if (ip.equals(host)){
                continue;
            }
            new Thread(new dollarnetwork.NetworkSearch(ip, arpTable)).start();
        }
        return arpTable;
    }

    public static String getHostIP() {
        try {
            Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
            for (; n.hasMoreElements();) {
                NetworkInterface e = n.nextElement();
                Enumeration<InetAddress> a = e.getInetAddresses();
                for (; a.hasMoreElements();) {
                    InetAddress addr = a.nextElement();
                    String ip = addr.getHostAddress();
                    if (ip.split("\\.").length > 0) {
                        if (ip.split("\\.")[0].equals("192")) {
                            return ip;
                        }
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public void sendToOne(int id, String message) throws IOException {
        outList[id].add(message);
    }

    public void sendToAll(String message) throws IOException {
        for (int id = 0; id < 255; id++) {
            if (outList[id] != null){
                outList[id].add(message);
            }
        }
    }

    public String getMessage(int id, String message) throws IOException {
        try {
            return inList.remove(0);
        } catch (Exception e) {
            return null;
        }
    }

    public NetworkContainer() {
        String hostIP = getHostIP();
        System.out.println(hostIP);

        int inPort = Integer.parseInt("1" + String.format("%04d", Integer.parseInt(hostIP.split("\\.")[3])));

        // List<String> arp = checkHosts(hostIP);
        // arp.remove(hostIP);
        // try {
        // Thread.sleep(5000);
        // } catch (Exception e) {}

        // for (String ip : arp) {
        // int inPort = Integer.parseInt("1" + String.format("%04d",
        // Integer.parseInt(ip.split("\\.")[3])));
        // new Thread(new dollarnetwork.NewBlockListener(inPort, ip)).start();
        // }

        String subnet = hostIP.substring(0, hostIP.lastIndexOf('.'));
        for (int i = 0; i < 255; i++) {
            String ip = subnet + "." + i;

            if (ip.equals(hostIP)) {
                continue;
            }

            int outPort = Integer.parseInt("1" + String.format("%04d", i));

            outList[i] = new ArrayList<String>();

            new Thread(new dollarnetwork.Sender(outPort, outList[i])).start();
            new Thread(new dollarnetwork.Listener(inPort, ip, inList)).start();
        }
    }
}
