package sample;

public class Transaction {

    private String senderKey;
    private String receiverKey;
    private double amount;

    public Transaction(String sender, String receiver, double amount){
        this.senderKey = sender;
        this.receiverKey = receiver;
        this.amount = amount;
    }

    public String toString(){
        String json = "{";
        json += "\"sender\":"+"\""+senderKey+"\",";
        json += "\"receiver\":"+"\""+receiverKey+"\",";
        json += "\"amount\":"+amount+"}";
        System.out.println(json);
        return json;
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
}
