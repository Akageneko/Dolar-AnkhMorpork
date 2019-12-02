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

            if (System.getProperty("os.name").contains("Win")) {
                p = Runtime.getRuntime().exec("ping -n 1 " + host);
            } else {
                p = Runtime.getRuntime().exec("ping -c 1 " + host);
            }

            int returnVal = p.waitFor();

            if (returnVal == 0) {
                System.out.println(host + " is reachable");
                arpTable.add(host);
            }
        } catch (Exception e) {}
    }
}
