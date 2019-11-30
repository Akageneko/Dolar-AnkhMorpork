package dollarnetwork;

import java.util.List;

public class NetworkSearch implements Runnable {
    String host;
    List<String> arpTable;

    public NetworkSearch(String host, List<String> arpTable) {
        this.host = host;
        this.arpTable = arpTable;
    }

    public void run() {
        // System.out.println(host);
        try {
            Process p;
            
            if (System.getProperty("os.name").contains("Win")){
                p = java.lang.Runtime.getRuntime().exec("ping -n 1 " + host);
            } else{
                p = java.lang.Runtime.getRuntime().exec("ping -c 1 " + host);
            }

            int returnVal = p.waitFor();

            if (returnVal == 0) {
                System.out.println(host + " is reachable");
                arpTable.add(host);
            }
        } catch (Exception e) {
            //TODO: handle exception
        }

        // try {
        // if (InetAddress.getByName(host).isReachable(5000)) {
        // System.out.println(host + " is reachable");
        // arpTable.add(host);
        // } else {
        // // System.out.println();
        // }
        // } catch (Exception e) {
        // System.out.println("something wrong with " + host + "...");
        // }
    }
}
