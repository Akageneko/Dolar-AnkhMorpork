package sample;

import dollarnetwork.NetworkContainer;

import java.util.ArrayList;

public class Secretary extends Thread {

    Economy economy;
    NetworkContainer netContainer = new NetworkContainer();
    boolean digging;

    public Secretary(Economy economy,boolean digging){
        this.economy = economy;
        this.digging = digging;
    }

    @Override
    public void run() {
        DiggerFred diggerFred= new DiggerFred();
        if(digging){
            diggerFred.start();
        }
        netContainer.sendToAll("Hello|" + netContainer.hostIP);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String msg = netContainer.getMessage();
        ArrayList<String> tempInList = new ArrayList<>();
        while(msg!=null){
            //załaduj swój blockchain
            if(msg.startsWith("BlockChain|")){
                String blockChainContent = msg.split("|")[1];
                BlockChain.setFileContent(blockChainContent);
                break;
            }
            else{
                tempInList.add(msg);
            }
            msg = netContainer.getMessage();
        }
        economy.initializeBlockchain();
        while(!tempInList.isEmpty()){
            msg = tempInList.get(0);
            serveMessage(msg);
            tempInList.remove(0);
        }
        while (true){
            msg = netContainer.getMessage();
            if(msg!=null) {
                serveMessage(msg);
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendTransaction(Transaction transaction){
        String msg = "Transaction|";
        msg += transaction.toString();
        netContainer.sendToAll(msg);
    }

    public void sendBlock(Block block){
        String msg = "Block|";
        msg += block.toString();
        netContainer.sendToAll(msg);
    }

    private void serveMessage(String msg) {
        String msgType = msg.split("|")[0];
        switch(msgType){
            case "Hello":{
                //wyciągnij id z IP
                int id = Integer.parseInt((msg.split("|")[1]).split(".")[3]);
                //wyślij swój blockChain
                String respond = "BlockChain|";
                respond += economy.getBlockchain().toString();
                netContainer.sendToOne(id,respond);
                break;
            }
            case "Transaction":{
                //dodaj do listy oczekujących transakcji
                economy.getTransactions().add(new Transaction(msg.split("|")[1]));
                break;
            }
            case "Block":{
                //dodaj blok do blockchaina
                Block incoming = new Block(msg.split("|")[1]);
                if(ValidatorConector.Validate(incoming.toString(),5)) {
                    economy.getBlockchain().AddBlockToChain(incoming);
                }
                // TODO zatrzymaj koparkę
                break;
            }
            default:{break;}
        }
    }
}
