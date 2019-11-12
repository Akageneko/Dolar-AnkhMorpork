package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    static Settings settings;

    public static void setView(String view) {
        root.getChildren().clear();
        root.getChildren().add(views.get(view));
        controllers.get(view).personalize(settings);
    }

    public static void exit(ActionEvent event) {
        Stage app = (Stage)(((Button)event.getSource()).getScene().getWindow());
        app.close();
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        root = FXMLLoader.load(getClass().getResource("anchor.fxml"));
        String[] viewnames = new String[]{"first_greeting","sign_up","login","wallet"};
        loadGivenViews(viewnames);
        primaryStage.setTitle("Trzos na Dolary");
        if(newUser()){
            setView("first_greeting");
        }
        else{
            settings = new Settings();
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
        String workingDir = (new File(getClass().getResource("Main.class").getPath())).toPath().getParent().getParent().toString();
        String configFile = workingDir + "/config/id.config";
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


    public static void main(String[] args) {
        launch(args);
    }
}
