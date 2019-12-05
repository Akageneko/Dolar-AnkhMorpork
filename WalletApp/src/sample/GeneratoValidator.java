package sample;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GeneratoValidator {

    private String insertNonce(String block, int nonce){
        String non = String.valueOf(nonce);
        while(non.length()<9){
            non = "0"+non;
        }
        String s1 = block.substring(0,block.indexOf("<NONCE>{")+8);
        String s2 = block.substring(block.indexOf("<PREVIOUS_BLOCK_HASH>{")-1);
        return s1 + non + s2;
    }

    private boolean hashAndCheck(String block, int number_of_zeros){
        MessageDigest digest = null;
        boolean isGood = true;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(block.getBytes(StandardCharsets.UTF_8));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < encodedhash.length; i++) {
                String hex = Integer.toHexString(0xff & encodedhash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            String hashHex = hexString.toString();
            while(hashHex.length() <64){
                hashHex = "0" + hashHex;
            }
            for(int i= 0;i<number_of_zeros;i++){
                if(hashHex.charAt(i) != '0'){
                    isGood = false;
                    break;
                }
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return isGood;
    }

    public int Mine(String block, int number_of_zeros){

        int nonce;

        do {
        nonce =(int) (Math.random() * ((Integer.MAX_VALUE)));
        block = insertNonce( block, nonce);
        }while(! hashAndCheck(block, number_of_zeros));
        return  nonce;
    }



    public boolean Validate(String block, int number_of_zeros){
        return hashAndCheck(block,number_of_zeros);
    }


}
