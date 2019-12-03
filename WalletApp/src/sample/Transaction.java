package sample;

public class Transaction {

    private String senderKey;
    private String receiverKey;
    private double amount;
    private String signature;

    public Transaction(String trans){
        this.senderKey = trans.substring(trans.indexOf("<SENDER_PUBLIC_KEY>{")+20,trans.indexOf("}",trans.indexOf("<SENDER_PUBLIC_KEY>{")));
        this.receiverKey = trans.substring(trans.indexOf("<RECIVER_PUBLIC_KEY>{")+21,trans.indexOf("}",trans.indexOf("<RECIVER_PUBLIC_KEY>{")));
        this.amount = Double.parseDouble(trans.substring(trans.indexOf("<AMOUNT>{")+9,trans.indexOf("}",trans.indexOf("<AMOUNT>{"))));
        this.signature = trans.substring(trans.indexOf("<SIGNATURE>{")+12,trans.indexOf("}",trans.indexOf("<SIGNATURE>{")));
    }

    public Transaction(String sender, String receiver, double amount, String signat){
        this.senderKey = sender;
        this.receiverKey = receiver;
        this.amount = amount;
        this.signature = signat;
    }

    public static String GetInfiniteWellKey() {
        return "";
    }

    public String toString(){
        String temp = "";
        temp = "<TRANSACTION>{";
        temp += "<SENDER_PUBLIC_KEY>{"+senderKey+"}";
        temp += "<RECIVER_PUBLIC_KEY>{"+receiverKey+"}";
        temp += "<AMOUNT>{"+ amount +"}";
        temp += "<SIGNATURE>{"+signature+"}";
        temp +="}";
        return temp;
    }

    public String getSenderKey() {
        return senderKey;
    }

    public void setSenderKey(String senderKey) {
        this.senderKey = senderKey;
    }

    public String getReceiverKey() {
        return receiverKey;
    }

    public void setReceiverKey(String receiverKey) {
        this.receiverKey = receiverKey;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setSignature(String sig){
      this.signature = sig;
    }

    public String getSignature(){
      return this.signature;
    }
}
