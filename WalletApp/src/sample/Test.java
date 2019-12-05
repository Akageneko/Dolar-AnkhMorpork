package sample;

public class Test {

    public static void main(String[] args){
        Block b = new Block("prevhash",new Transaction("sender","receiver",100.0,"signature"));
        System.out.println(b.toString());
    }
}
