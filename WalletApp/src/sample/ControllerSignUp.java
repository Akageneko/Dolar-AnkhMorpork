package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ControllerSignUp extends PersonalizedController{

    @FXML private Label labelPublicKey;
    @FXML private Label labelPrivateKey;
    @FXML private Button buttonPublicKey;
    @FXML private Button buttonPrivateKey;
    @FXML private PasswordField fieldPassword1;
    @FXML private PasswordField fieldPassword2;
    @FXML private Label labelPasswordsDifferent;
    @FXML private TextField fieldUsername;
    @FXML private Label labelPasswordEmpty;
    @FXML private Label labelUsernameEmpty;

    private File publicKey, privateKey;

    public void button_PublicKey(ActionEvent event) {
        publicKey = (new FileChooser()).showOpenDialog(buttonPublicKey.getScene().getWindow());
        labelPublicKey.setText(publicKey.getPath());
    }

    public void button_privateKey(ActionEvent event) {
        privateKey = (new FileChooser()).showOpenDialog(buttonPrivateKey.getScene().getWindow());
        labelPrivateKey.setText(privateKey.getPath());
    }

    public void button_signUp(ActionEvent event) {
        //Checking if all fields are properly filled
        if(fieldUsername.getText().equals("")){
            labelUsernameEmpty.setVisible(true);
            return;
        }
        labelUsernameEmpty.setVisible(false);
        if(fieldPassword1.getText().equals("")){
            labelPasswordEmpty.setVisible(true);
            return;
        }
        labelPasswordEmpty.setVisible(false);
        if(!fieldPassword1.getText().equals(fieldPassword2.getText())){
            labelPasswordsDifferent.setVisible(true);
            return;
        }
        labelPasswordsDifferent.setVisible(false);
        if(publicKey==null || privateKey==null) {
            return;
        }
        String workingDir = (new File(getClass().getResource("Main.class").getPath())).toPath().getParent().getParent().toString();

        try {
            //preparing login file
            File login_file = new File(workingDir+"/config/id.config");
            login_file.createNewFile();
            PrintWriter toLogin = new PrintWriter(login_file);
            toLogin.println("username:"+fieldUsername.getText());
            //hashing password
            String hp = Main.hashPassword(fieldPassword1.getText());
            toLogin.println("password:"+hp);
            toLogin.close();

            //preparing wallet's properties
            File props_file = new File(workingDir+"/config/props.config");
            props_file.createNewFile();
            PrintWriter toProps = new PrintWriter(props_file);
            toProps.println("public-key:"+publicKey.getAbsolutePath());
            toProps.println("private-key:"+privateKey.getAbsolutePath());
            toProps.println("dig:Y");
            toProps.close();

            Main.settings = new Settings();
            Main.setView("login");
        }
        catch(Exception exception){
            exception.printStackTrace();
            //TODO: handle that better
        }


    }

    public void button_cancel(ActionEvent event) {
        Main.setView("first_greeting");
    }

}
