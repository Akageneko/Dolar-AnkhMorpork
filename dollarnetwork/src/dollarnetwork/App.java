package dollarnetwork;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class App {

    public static List<String> checkHosts(final String host) throws IOException {
        List<String> arpTable = new ArrayList<String>();
        String subnet = host.substring(0, host.lastIndexOf('.'));
        for (int i = 1; i < 255; i++) {
            final String ip = subnet + "." + i;
            new Thread(new dollarnetwork.NetworkSearch(ip, arpTable)).start();
        }
        return arpTable;
    }    

    public static void main(final String[] args) throws IOException {
        String hostIP = "";
        Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
        for (; n.hasMoreElements();) {
            NetworkInterface e = n.nextElement();
            Enumeration<InetAddress> a = e.getInetAddresses();
            for (; a.hasMoreElements();) {
                InetAddress addr = a.nextElement();
                String ip = addr.getHostAddress();
                if (ip.split("\\.").length > 0) {
                    if (ip.split("\\.")[0].equals("192")) {
                        hostIP = ip;
                    }
                }
            }
        }

        // InetAddress inetAddress = InetAddress.getLocalHost();
        // String ip = inetAddress.getHostAddress();

        //List<String> arp = checkHosts(hostIP);
        // try {
        //     Thread.sleep(5000);
        // } catch (Exception e) {
        //     //TODO: handle exception
        // }

        //arp.remove(hostIP);
        // System.out.println(arp);

        int inPort = Integer.parseInt("1" + String.format("%04d", Integer.parseInt(hostIP.split("\\.")[3])));

        // for (String ip : arp) {
        //     int inPort = Integer.parseInt("1" + String.format("%04d", Integer.parseInt(ip.split("\\.")[3])));
        //     new Thread(new dollarnetwork.NewBlockListener(inPort, ip)).start();
        // }
        
        ArrayList<String> inList = new ArrayList<String>();
        ArrayList<String>[] outList = new ArrayList[255];

        String[] splitHost = hostIP.split("\\.");
        for (int i = 0; i<255; i++){

            String ip = splitHost[0] + "." + splitHost[1] + "." + splitHost[2] + "." + i;
            if (ip.equals(hostIP)){
                continue;
            }
            int outPort = Integer.parseInt("1" + String.format("%04d", i));

            outList[i] = new ArrayList<String>();

            final int ii = i;
            new Thread(new dollarnetwork.Sender(outPort,outList[i])).start();
            new Thread(new dollarnetwork.Listener(inPort, ip, inList)).start();
            //new Thread(() -> {for(int x=0; x<5; x++) outList[ii].add("TEST");}).start();
        }
        
    }
}
