package sample;

import java.io.*;

public class Settings {
     private String username, hashedPassword;
     private boolean dig;
     private File publicKey, privateKey;

     public Settings() throws IOException {
         load();
     }

     public void reload() throws IOException {
         load();
     }

     private void load() throws IOException {
         String workingDir = (new File(getClass().getResource("Main.class").getPath())).toPath().getParent().getParent().toString();
         // get login credentials
         File login_file = new File(workingDir+"/config/id.config");
         BufferedReader fromLogin = new BufferedReader(new FileReader(login_file));
         username = getNextValue(fromLogin);
         hashedPassword = getNextValue(fromLogin);
         fromLogin.close();
         // get other settings
         File props_file = new File(workingDir+"/config/props.config");
         BufferedReader fromProps = new BufferedReader(new FileReader(props_file));
         publicKey = new File(getNextValue(fromProps));
         privateKey = new File(getNextValue(fromProps));
         if(getNextValue(fromProps).equals("Y")){
             dig = true;
         }
         else{
             dig = false;
         }
     }

     private String getNextValue(BufferedReader bf) throws IOException {
         return bf.readLine().split(":",2)[1];
     }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public boolean isDig() {
        return dig;
    }

    public File getPublicKey() {
        return publicKey;
    }

    public File getPrivateKey() {
        return privateKey;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setDig(boolean dig) {
        this.dig = dig;
    }

    public void setPublicKey(File publicKey) {
        this.publicKey = publicKey;
    }

    public void setPrivateKey(File privateKey) {
        this.privateKey = privateKey;
    }
}
