package sample;

import java.io.File;
import java.util.ArrayList;

public class Economy {

    public ArrayList<Transaction> transactions;
    public double balance;
    private File blockchain;
    private Settings settings;

    public Economy(Settings settings){
        this.transactions = new ArrayList<>();
        balance = loadBalanceFromBlockchain();
        String workingDir = (new File(getClass().getResource("Main.class").getPath())).toPath().getParent().getParent().toString();
        blockchain = new File(workingDir+"/core/blockchain");
        this.settings = settings;
    }

    public boolean addTransaction(String receiver,double amount){
        if(amount > balance ){
            return false;
        }
        else{
            transactions.add(new Transaction(settings.getPublicKeyString(),receiver,amount));
            balance -= amount;
            return true;
        }
    }

    private double loadBalanceFromBlockchain(){
        return 100.0;
    }

}
