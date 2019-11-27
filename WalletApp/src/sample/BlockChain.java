package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

  public void AddBlockToChain(Block block){
    try {
      PrintWriter out = new PrintWriter(new FileWriter(blockchain, true));
      out.append(block.toString());
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


}
