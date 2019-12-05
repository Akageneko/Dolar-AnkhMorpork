package sample;

import java.io.File;
import java.security.NoSuchAlgorithmException;
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

    public boolean addTransaction(String receiver,double amount) throws NoSuchAlgorithmException {
        if(amount > balance ){
            return false;
        }
        else{
            transactions.add(new Transaction(settings.getPublicKeyString(),receiver,amount,getSignature(settings.getPublicKeyString()+receiver+amount)));
            balance -= amount;
            return true;
        }
    }

    public String getSignature(String s) throws NoSuchAlgorithmException {
        return Main.hashPassword(s);
    }

    private boolean equal(Transaction t1, Transaction t2){
        if(t1.getSenderKey().compareTo(t2.getSenderKey()) != 0) return false;
        if(t1.getReceiverKey().compareTo(t2.getReceiverKey()) != 0) return false;
        if(t1.getAmount() != t2.getAmount()) return false;
        if(t1.getSignature().compareTo(t2.getSignature()) != 0) return false;
        return true;
    }

    public void RemoveTransaction(Transaction trans){
        for (int i = 0; i < transactions.size(); i++){
            if(equal(trans, transactions.get(i))){
                transactions.remove(i);
                System.out.println("DEBUG: Transaction removed");
                break;
            }
        }
    }
}
