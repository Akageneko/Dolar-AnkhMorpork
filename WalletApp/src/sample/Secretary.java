package sample;

import dollarnetwork.NetworkContainer;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Secretary extends Thread {

    Economy economy;
    NetworkContainer netContainer = new NetworkContainer();
    boolean digging;
    DiggerFred diggerFred;

    public Secretary(Economy economy,boolean digging){
        this.economy = economy;
        this.digging = digging;
        this.diggerFred = new DiggerFred(this);
    }

    @Override
    public void run() {
        System.out.println("DEBUG: Secretaru running");
        try {
            waitForBlockChain();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println("DEBUG: Starting listening");
        while (true){
           String msg = netContainer.getMessage();
           System.out.println("MESSAGE PASSED TO SECRETARY: "+msg);
            if(msg!=null) {
                try {
                    serveMessage(msg);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000);
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
        System.out.println("DEBUG: in send block");
        String msg = "Block|";
        msg += block.toString();
        netContainer.sendToAll(msg);
        for(Transaction t : block.getTransactions()){
            economy.transactions.remove(t);
        }
        try {
            initializeDigging();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void serveMessage(String msg) throws NoSuchAlgorithmException {
        String msgType = msg.split("\\|")[0];
        System.out.println("DEBUG: Received type - " + msgType);
        switch(msgType){
            case "Hello":{
                //wyciągnij id z IP
                int id = Integer.parseInt((msg.split("\\|")[1]).split("\\.")[3]);
                //wyślij swój blockChain
                String respond = "BlockChain|";
                respond += economy.getBlockchain().toString();
                netContainer.sendToOne(id,respond);
                break;
            }
            case "Transaction":{
                //dodaj do listy oczekujących transakcji
                economy.getTransactions().add(new Transaction(msg.split("\\|")[1]));
                break;
            }
            case "Block":{
                //zweryfikuj blok
                //dodaj blok do blockchaina
                Block incoming = new Block(msg.split("\\|")[1]);
                if(ValidatorConector.Validate(incoming.toString(),5)) {
                    economy.getBlockchain().AddBlockToChain(incoming);
                    for(Transaction t : incoming.getTransactions()){
                        economy.transactions.remove(t);
                    }
                    initializeDigging();
                    economy.balance = economy.getBlockchain().GetUserAccountBalance(economy.settings.getPublicKeyString());
                }
                else {
                    try {
                        netContainer.sendToAll("LiberumVeto|"+Main.hashPassword(incoming.toString()));
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case "LiberumVeto":{
                String suspectedHash = msg.split("\\|")[1];
                if (economy.getBlockchain().getLatestHash().equals(suspectedHash)) {
                    diggerFred.stopDigging();
                    waitForBlockChain();
                }
                break;
            }
            default:{break;}
        }
    }

    private void waitForBlockChain() throws NoSuchAlgorithmException {
        System.out.println("DEBUG: sending hello...");
        netContainer.sendToAll("Hello|" + netContainer.hostIP);
        System.out.println("DEBUG: sent");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String msg = netContainer.getMessage();
        ArrayList<String> tempInList = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis()-startTime < 5000){
            System.out.println("DEBUG: Waiting for blockchain... "+(System.currentTimeMillis()-startTime)+"ms");
            //załaduj swój blockchain
            if(msg!=null) {
                if (msg.startsWith("BlockChain|")) {
                    System.out.println("DEBUG: received blockchain");
                    String blockChainContent = msg.split("\\|")[1];
                    BlockChain.setFileContent(blockChainContent);
                    break;
                } else {
                    tempInList.add(msg);
                    System.out.println("MESSAGE SENT TO TEMP INBOX: "+msg);
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            msg = netContainer.getMessage();
        }
        System.out.println("DEBUG: Ready or not, loading blockchain");
        economy.initializeBlockchain();
        economy.balance = economy.getBlockchain().GetUserAccountBalance(economy.settings.getPublicKeyString());
        if(economy.settings.isDig()) {
            initializeDigging();
        }
        while(!tempInList.isEmpty()){
            msg = tempInList.get(0);
            System.out.println("FROM TEMP BOX PARSING MESSAGE: "+msg);
            try {
                serveMessage(msg);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            tempInList.remove(0);
        }
    }

    private void initializeDigging() throws NoSuchAlgorithmException {
        System.out.println("DEBUG: in initialize Digging");
        String senderKey = Transaction.GetInfiniteWellKey();
        String receiverKey = economy.settings.getPublicKeyString();
        double amount = economy.settings.getMiningPrize();
        Transaction prize = new Transaction(senderKey,receiverKey,amount,economy.getSignature(senderKey+receiverKey+amount));
        System.out.println("DEBUG: Added prize transaction: "+prize.toString());
        Block block = null;
        try {
            block = new Block(economy.getBlockchain().getLatestHash(),prize);
            for(Transaction t : economy.transactions){
                block.AddTransactionToBlock(t);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            diggerFred.startDigging(block,economy.settings.getNumberOfZeros());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
