package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.security.NoSuchAlgorithmException;

public class ControllerLogin extends PersonalizedController {

    @FXML Label labelHelloUser;
    @FXML PasswordField fieldPassword;
    @FXML Label labelPasswordIncorrect;

    Settings settings;

    @Override
    public void personalize(Settings settings) {
        labelHelloUser.setText("Witaj, "+settings.getUsername()+" !");
        this.settings = settings;
    }

    public void button_signIn(ActionEvent event) throws NoSuchAlgorithmException {
        if(Main.hashPassword(fieldPassword.getText()).equals(settings.getHashedPassword())){
            Main.setView("wallet");
        }
        else{
            labelPasswordIncorrect.setVisible(true);
        }
    }
}
