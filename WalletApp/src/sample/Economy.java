package sample;

import java.io.File;
import java.util.ArrayList;

public class Economy {

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public ArrayList<Transaction> transactions;
    public double balance = 0.0;

    public BlockChain getBlockchain() {
        return blockchain;
    }

    private BlockChain blockchain;
    public Settings settings;

    public Economy(Settings settings){
        this.transactions = new ArrayList<>();
        this.settings = settings;
    }

    public void initializeBlockchain(){
        this.blockchain = new BlockChain();
        this.balance = blockchain.GetUserAccountBalance(settings.getPublicKeyString());
    }

    public boolean addTransaction(String receiver,double amount){
        if(amount > balance ){
            return false;
        }
        else{
            transactions.add(new Transaction(settings.getPublicKeyString(),receiver,amount,getSignature(settings.getPublicKeyString()+receiver+amount)));
            balance -= amount;
            return true;
        }
    }

    public String getSignature(String s) {
        String signature = new String();
        for(int i=0;i<256;i++){
            signature += "0";
        }
        return signature;
    }
}
