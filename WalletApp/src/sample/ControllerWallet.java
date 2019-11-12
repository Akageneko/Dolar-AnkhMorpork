package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.security.NoSuchAlgorithmException;

public class ControllerWallet extends PersonalizedController {


    Settings settings;
    //Overall
    @FXML Label labelUsername;
    @FXML Button buttonLogout;

    //Tab "Moj portfel"
    @FXML Label labelWalletAddress, labelMoney, labelDiggingActive;

    //Tab "Nowa transakcja"
    @FXML TextField fieldReceiver,fieldAmount;
    @FXML Label labelNotEnoughMoney;
    @FXML PasswordField fieldPasswordTransaction;
    @FXML Button buttonConfirmTransaction;

    //Tab "Ustawienia"
    @FXML TextField fieldUsernameSettings;
    @FXML Button buttonPublicKey, buttonPrivateKey, buttonConfirmSettings;
    @FXML RadioButton radioDig;
    @FXML PasswordField fieldPasswordSettings;
    @FXML Label labelPublicKey, labelPrivateKey;
    @FXML Label  labelIncorrectPasswordSettings;

    @Override
    public void personalize(Settings settings){
        this.settings = settings;
        labelUsername.setText(settings.getUsername());
        labelPublicKey.setText(settings.getPublicKey().getAbsolutePath());
        labelPrivateKey.setText(settings.getPrivateKey().getAbsolutePath());
    }

    public void button_confirmTransaction(ActionEvent event) throws NoSuchAlgorithmException {

    }

    public void button_publicKey(ActionEvent event) {
        File newPublicKey = (new FileChooser().showOpenDialog(buttonPublicKey.getScene().getWindow()));
        settings.setPublicKey(newPublicKey);
        labelPublicKey.setText(newPublicKey.getAbsolutePath());
    }

    public void button_privateKey(ActionEvent event) {
        File newPrivateKey = (new FileChooser().showOpenDialog(buttonPrivateKey.getScene().getWindow()));
        settings.setPrivateKey(newPrivateKey);
        labelPrivateKey.setText(newPrivateKey.getAbsolutePath());
    }

    public void button_confirmSettings(ActionEvent event) throws NoSuchAlgorithmException {
        if(!settings.getHashedPassword().equals(Main.hashPassword(fieldPasswordSettings.getText()))){
            labelIncorrectPasswordSettings.setVisible(true);
            return;
        }
        labelIncorrectPasswordSettings.setVisible(false);
        //przetwarzaj dalej...
    }

    public void button_logout(ActionEvent event) {
        Main.setView("login");
    }
}
