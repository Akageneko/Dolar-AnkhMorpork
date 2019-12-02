package sample;

import java.io.*;

public class Settings {
     private String username, hashedPassword;
     private boolean dig;
     private File publicKey, privateKey;
     //conveniece for not reading the public key everytime
    private String publicKeyString;

     public void load() throws IOException {
         String workingDir = (new File(getClass().getResource("Main.class").getPath())).toPath().getParent().getParent().getParent().toString();
         // get login credentials
         File login_file = new File(workingDir+"/AnkhMorporkDollar/config/id.config");
         BufferedReader fromLogin = new BufferedReader(new FileReader(login_file));
         username = getNextValue(fromLogin);
         hashedPassword = getNextValue(fromLogin);
         fromLogin.close();
         // get other settings
         File props_file = new File(workingDir+"/AnkhMorporkDollar/config/props.config");
         BufferedReader fromProps = new BufferedReader(new FileReader(props_file));
         publicKey = new File(getNextValue(fromProps));
         privateKey = new File(getNextValue(fromProps));
         if(getNextValue(fromProps).equals("Y")){
             dig = true;
         }
         else{
             dig = false;
         }
         publicKeyString = getFromPublicKey(publicKey);
     }

    private String getFromPublicKey(File publicKey) {
         return "aga";
    }

    public void export(){
         String workingDir = (new File(getClass().getResource("Main.class").getPath())).toPath().getParent().getParent().toString();
         try {
             //preparing login file
             File login_file = new File(workingDir+"/config/id.config");
             PrintWriter toLogin = new PrintWriter(login_file);
             toLogin.println("username:"+username);
             //hashing password
             toLogin.println("password:"+hashedPassword);
             toLogin.close();

             //preparing wallet's properties
             File props_file = new File(workingDir+"/config/props.config");
             PrintWriter toProps = new PrintWriter(props_file);
             toProps.println("public-key:"+publicKey.getAbsolutePath());
             toProps.println("private-key:"+privateKey.getAbsolutePath());
             if(isDig()) {
                 toProps.println("dig:Y");
             }
             else{
                 toProps.println("dig:N");
             }
             toProps.close();
         }
         catch(Exception exception){
             exception.printStackTrace();
             //TODO: handle that better
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

    public String getPublicKeyString() {
        return publicKeyString;
    }
}
