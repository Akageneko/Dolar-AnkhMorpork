package sample;

import java.util.ArrayList;
import java.util.List;

public class Block{

    private String nonce = "000000000";
    private String prev_block_hash = "";
    private String transactions = "";
    private List<Transaction> list = new ArrayList<Transaction>();

    private int lengthOfCrypto = 392 ;
    private int lengthOfSignature = 392 ;

    public Block(String line){ ReadBlockString(line); }

    public Block(String prevHash,Transaction transaction){
        this.prev_block_hash = prevHash;
        AddTransactionToBlock(transaction);
    }

    public String toString(){ return blockBuldier();}

    public String GetBlock(){ return blockBuldier(); }

    public List<Transaction> getTransactions(){
      return list;
    }

    public String getNonce(){
      return nonce;
    }

    public String getPrevBlockHash(){
      return prev_block_hash;
    }


    public void SetPreviousBlockHash(String hash){
      prev_block_hash = hash;
    }

    public void SetNonce(String non){
        while (non.length() <9){
            non = "0"+non;
        }
        nonce = non;
    }

    public void AddTransactionToBlock(Transaction trans){
      this.list.add(trans);
      this.transactions += trans.toString();
    }

    public void ReadBlockString(String block){
      //GET nonce
      this.nonce = block.substring(block.indexOf("<NONCE>{")+8,block.indexOf("}",block.indexOf("<NONCE>{")));
      System.out.println("nonce:"+this.nonce);
      //GET prev block hash
      this.prev_block_hash = block.substring(block.indexOf("<PREVIOUS_BLOCK_HASH>{")+22,block.indexOf("<PREVIOUS_BLOCK_HASH>{")+22+64);
      System.out.println("hash:"+this.prev_block_hash);
      //GET all transactions
      int i = 1;
      String send;
      String rec;
      String amo;
      String sig;
      while(-1 != block.indexOf("<TRANSACTION>{",i)){
        i = block.indexOf("<TRANSACTION>{",i);
        //sender
        i = block.indexOf("<SENDER_PUBLIC_KEY>{",i)+20;
        send = block.substring(i,i+lengthOfCrypto);
        System.out.println("sender:"+send);
        //receiver
        i = block.indexOf("<RECIVER_PUBLIC_KEY>{",i)+21;
        rec = block.substring(i,i+lengthOfCrypto);
        System.out.println("reciver:"+rec);
        //amount
        i = block.indexOf("<AMOUNT>{",i)+9;
        amo = block.substring(i,block.indexOf("}",i));
        System.out.println("amount:"+amo);
        //signature TO TRZEBA ZMIENIC W PRZYSZLOSCI
        i = block.indexOf("<SIGNATURE>{",i)+12;
        sig = block.substring(i,i+256);
        System.out.println("signature:"+sig);

        list.add(new Transaction(send, rec, Double.parseDouble(amo), sig));
      }
      for (Transaction temp : list) {
        transactions += temp.toString();
      }
    }

    public double BlockBalanceOfUser(String public_key){
        double balance = 0;
        for (Transaction trans : list) {
            if(trans.getReceiverKey().equals(public_key)){
                balance += trans.getAmount();
            }else{
                if(trans.getSenderKey().equals(public_key)){
                    balance -= trans.getAmount();
                }
            }
        }
        return balance;
    }

    private String blockBuldier(){
      String temp = "";
      //Add nonce
      temp += "<NONCE>{"+this.nonce+"}";
      //Add previous block hash
      temp += "<PREVIOUS_BLOCK_HASH>{" + this.prev_block_hash +"}";
      //Add Transactions
      temp += this.transactions;

      return "<BLOCK>{"+temp+"}";
    }

}
