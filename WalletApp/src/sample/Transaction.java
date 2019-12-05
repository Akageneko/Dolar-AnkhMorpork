package sample;

import java.security.NoSuchAlgorithmException;

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
        String privateKey = "MIIEoQIBAAKCAQBJc4vMO4CELVjjUB7QmuTnp/7t298VT4nVHPy7q/DuMJR36lyW\n" +
                "rQnD5G4F/Uirb3tAYyYTKlwub6PqJrOIWrmuoP7oA56knT/tG+V49JjpzRrCMs6A\n" +
                "x6u3OQhDsZOTCjedIv83IsJaok6D3FnoOYAc4VPl3z0qpE8ox6s2OphPKF3TFpRF\n" +
                "1oaL4I3dBprDSjbkr8EqW6cNuU8E/p/t+BY+tvSaUswfdE7R4ZPTUXfpZ9zrvMMu\n" +
                "UJ1LkLISoMMHouZsgBVFBZhayXevMcnAIRR54fsXZWhiaSUqxrjC6eS6zEeNFonX\n" +
                "AxePuuV+BZ39MZ02MPRwpiR87VRWbTd5i0YnAgMBAAECggEASQljNlboaQZqf8X4\n" +
                "jjenbA6puL1Ma3VmugoT6U2EymR2DXN2U8GGl0p6eOwtqYK2DQVKI/6YIPiUBk4r\n" +
                "cWe9gT7Hmc+XnVpb6Zs2p/qYXsu7UuWQX4+sWQmWBTH7a1faix70pk+i8Nq5tfaT\n" +
                "VA5xLMaGVWqQ+Ojfac55mffT4clSGCGjDR23tuwxV1YSiO8o4efFwoTXAVjGynDC\n" +
                "8dqpVeSY9JCpQL9tcEuo7AoVMB1naLZ8sjncSN+2yIRZhPGz3JRrK+3/dxhI9DJJ\n" +
                "jpxW0WJjbGIIt/LQo11U6Khvbjz3ikGn4Bt8ZCf0EfLFcv8YbJl86KHH15TuKz5i\n" +
                "27uWkQKBgQCL0RgkVIOLKoA0mBctgkaJv3sOaeCGGUSQ9viv1hCVL/caII1skSUt\n" +
                "b0ewuVoFRchbs6IA1XXWikbVnzgHAbIExv0RRaW7WG29DeaOOBB+Rf6/TWDXkXtp\n" +
                "b1rUg6HpJz/CglGY71oxMrvplZ5BJuAxC4sY64tCubaxiy1sD5OMiQKBgQCGfK+O\n" +
                "Dvr1rIK9k/33Vwu88uuPqp4Dtx+OILlXTQ2BpQqNTF1OvKhBqmhKZSfSIxOgEnWZ\n" +
                "4M8viZaTXtbz2x/xrVYDYSKbUZQq7q+LXj5o0sEEqcmmy6CO56CkAbHeHGdivRnL\n" +
                "FF/9xhkiX/LVeW//0cfaAs9id12JHQ8pNgxxLwKBgGPbJBjeW0yLQCFP9k3EW0iJ\n" +
                "/wMeTwK3DSdTm+1JP3KtXRfF4/uD9j9q2JK47jgRzLThqHRb/9sHyutK2aL3bQLI\n" +
                "c3lgeqtAzq9VPDKai/0DVJugUefjuXZl10Mr9/ki1QDAPjKxz4KPr3F2MB5r+3ZL\n" +
                "vj5AC6fOJ1LKCdjBjymxAoGABiGxL1znfEP9Qf95WMmTM24v50D0K18yBRzg1mYg\n" +
                "84Q+0upkZq6bTUMjtiCejColPusYiYeCarxvLRDp4/xdnHs2rLjFByfVBZAUWNpX\n" +
                "JhhVzIyhMQWfsGRHIrMhFOtem1Xb0zJ+nedmRfbx72G2BYffAMWYBvlCPnogstRQ\n" +
                "BOkCgYAp+qv7VLfCgF4+5d5SMSu23l4SIjklzxRRuXiqE3VeV/wTzMQJ5OkehNO4\n" +
                "Nl0vIlmQz+JAK9u6kA2J5tjdEEiLJlL4r8G41JvSH8RoL/uW15rKO3WoFV+bABVi\n" +
                "+i6D7RtUbwjuIGOjXc2iPisZCsnb0W/cGDVJ3T4RpHwgKuaEQQ==";

        return "MIIBITANBgkqhkiG9w0BAQEFAAOCAQ4AMIIBCQKCAQBJc4vMO4CELVjjUB7QmuTnp/7t298VT4nVHPy7q/DuMJR36lyWrQnD5G4F/Uirb3tAYyYTKlwub6PqJrOIWrmuoP7oA56knT/tG+V49JjpzRrCMs6Ax6u3OQhDsZOTCjedIv83IsJaok6D3FnoOYAc4VPl3z0qpE8ox6s2OphPKF3TFpRF1oaL4I3dBprDSjbkr8EqW6cNuU8E/p/t+BY+tvSaUswfdE7R4ZPTUXfpZ9zrvMMuUJ1LkLISoMMHouZsgBVFBZhayXevMcnAIRR54fsXZWhiaSUqxrjC6eS6zEeNFonXAxePuuV+BZ39MZ02MPRwpiR87VRWbTd5i0YnAgMBAAE=";
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
