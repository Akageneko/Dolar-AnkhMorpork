package sample;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlockChain{

  File blockchain = new File("blockchain");
  private List<Block> list = new ArrayList<Block>();


  public BlockChain()  {
    try {
      if (blockchain.exists()) {
        Scanner myReader = new Scanner(blockchain);
        while (myReader.hasNextLine()) {
          String line = myReader.nextLine();
          this.list.add(new Block(line));
        }
        myReader.close();
      } else {
        blockchain.createNewFile();
      }

    }catch (java.io.IOException e) {
      System.out.println("Coś się jebie z pilkiem blockchaina");
    }

  }

  public static void setFileContent(String blockChainContent) {
    try {
      File blockchainTEMP = new File("blockchain");
      blockchainTEMP.delete();
      blockchainTEMP.createNewFile();
      FileWriter fileWriter = new FileWriter("blockchain");
      fileWriter.write(blockChainContent);
      fileWriter.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public String toString(){
    Scanner myReader = null;
    String temp = "";
    try {
      myReader = new Scanner(blockchain);
      while (myReader.hasNextLine()) {
        temp += myReader.nextLine()+ "\n";
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return temp;
  }

  public void AddBlockToChain(Block block){
    try {
      PrintWriter out = new PrintWriter(new FileWriter(blockchain, true));
      out.println(block.toString());
      out.close();
      this.list.add(block);
    }catch (Exception e){
      System.out.println("Pieprzy przy  dopisywaniu bloku do pliku");
    }
  }

  public Boolean CheckIfTransactionCorrect(Transaction trans){
    double balance=0;
    for (Block block: list ) {
      balance += block.BlockBalanceOfUser(trans.getSenderKey());
    }

      return balance >= trans.getAmount();
  }

  public double GetUserAccountBalance(String userPublicKey){
    double balance = 0.0;
    for(Block b : list){
      for(Transaction t : b.getTransactions()){
        if(t.getReceiverKey().equals(userPublicKey)){
          balance += t.getAmount();
        }
        else if(t.getSenderKey().equals(userPublicKey)){
          balance -= t.getAmount();
        }
      }
    }
    return balance;
  }


    public String getLatestHash() throws NoSuchAlgorithmException {
      Block lastBlock = list.get(list.size()-1);
        return Main.hashPassword(lastBlock.toString());
    }
}
