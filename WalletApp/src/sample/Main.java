package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class Main extends Application {

    static AnchorPane root;
    static HashMap<String, GridPane> views = new HashMap<>();
    static HashMap<String, PersonalizedController> controllers = new HashMap<>();
    static String currentView;
    static Settings settings;
    static Economy economy;
    public static Secretary secretary;


    public static void setView(String view) {
        controllers.get(currentView).clear();
        root.getChildren().clear();
        root.getChildren().add(views.get(view));
        controllers.get(view).personalize(settings);
    }

    public static void exit(ActionEvent event) {
        Stage app = (Stage)(((Button)event.getSource()).getScene().getWindow());
        app.close();
    }

    public static boolean addTransactionToList(String receiver, String amountInString) {
        try {
            double amount = Double.parseDouble(amountInString);
            return economy.addTransaction(receiver,amount);
        }
        catch (NumberFormatException | NoSuchAlgorithmException e){
            return false;
        }

    }

    public static String refreshMoneyLabel() {
        Double balance = economy.balance;
        return balance.toString();
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        root = FXMLLoader.load(getClass().getResource("anchor.fxml"));
        String[] viewnames = new String[]{"first_greeting","sign_up","login","wallet"};
        loadGivenViews(viewnames);
        primaryStage.setTitle("Trzos na Dolary");
        currentView = "login";
        if(newUser()){
            setView("first_greeting");
        }
        else{
            settings = new Settings();
            settings.load();
            economy = new Economy(settings);
            setView("login");
        }
        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.show();

    }
    public static String hashPassword(String plaintext) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(plaintext.getBytes());
        BigInteger numerical = new BigInteger(1,hashedBytes);
        StringBuilder hexString = new StringBuilder(numerical.toString(16));
        while(hexString.length()<32){
            hexString.insert(0,'0');
        }
        return hexString.toString();
    }

    private boolean newUser() {
        String workingDir = (new File(getClass().getResource("Main.class").getPath())).toPath().getParent().getParent().getParent().toString();
        String configFile = workingDir + "/AnkhMorporkDollar/config/id.config";
        System.out.println(configFile);
        if((new File(configFile)).exists()){
            return false;
        }
        return true;
    }

    private void loadGivenViews(String[] filenames) throws IOException {
        for (String filename : filenames){
            FXMLLoader viewLoader = new FXMLLoader(this.getClass().getResource(filename+".fxml"));
            views.put(filename,(GridPane)viewLoader.load());
            controllers.put(filename,viewLoader.getController());
        }
    }

    public static void buildAndRunSecretary(){
        secretary = new Secretary(economy,settings.isDig());
        secretary.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

   /* public static void main(String[] args) {
        File f = new File("blokckch");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String temp = "";
        String s = "";
        String ss = "";
        for(int i =0;i<392;i++) s+="0";
        for(int i =0;i<256;i++) ss+="0";
        String me = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiyw/ltfbXJW+DHm5oRCq/xCHtAbQppWdWLV/g4fKnREbX54hpgsgkcYO+MUknDrL3ErRxSiqKOwefwZoWHhNWHC1qYkrY2aIJuhK97KJicTw8JHnZLrJDkXsVaJ5VoL+dX63tYjfzoPCIeiAnR87Ir/PvmIpT9ngPojVqZ1S7YkSAZ9REY0T6x/EuhS+C9WgIHZbku+WzOSrlTcqFIt48f4yKJdMdOLDqjmrd3iqw1oC2Bdn4RZuIhLc7FUXm04A+jHq8OTax1LTKODuidxuGIqoKcsjNVRKePMehGXN6foMmAHjS1Zsl+2qHXISnBQdtNwCITPiYmNCdI2yBFtpWQIDAQAB";        Transaction trans = new Transaction(s,me,100.0,s);
        Block block = new Block(ss,trans);
        //GeneratorConector gen = new GeneratorConector();
        System.out.println("Zaczynam kopać");
        GeneratorConector gen = new GeneratorConector();
        long n =1;
        try {
             n = gen.Mine(block.toString(), 5);System.out.println("mined"+n);
            BlockChain blockChain = new BlockChain();
            blockChain.AddBlockToChain(block);
            System.out.println("added");

        }catch (UnsatisfiedLinkError e){
            e.printStackTrace();
            System.out.println("wali się wywołanie");
        }

    }*/

}
