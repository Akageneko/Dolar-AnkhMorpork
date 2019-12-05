package sample;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.security.NoSuchAlgorithmException;

public class ControllerWallet extends PersonalizedController {


    Settings settings;
    File publicKey, privateKey;
    //Overall
    @FXML Label labelUsername;
    @FXML Button buttonLogout;

    //Tab "Moj portfel"
    @FXML Label labelWalletAddress, labelMoney, labelDiggingActive;

    //Tab "Nowa transakcja"
    @FXML TextField fieldReceiver,fieldAmount;
    @FXML Label labelNotEnoughMoney, labelIncorrectPasswordTransaction;
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
        fieldUsernameSettings.setText(settings.getUsername());
        labelPublicKey.setText(settings.getPublicKey().getAbsolutePath());
        labelPrivateKey.setText(settings.getPrivateKey().getAbsolutePath());
        labelWalletAddress.setText(settings.getPublicKeyString());
        labelMoney.setText(""+0.0);
        if(settings.isDig()){
            labelDiggingActive.setText("TAK");
        }
        else{
            labelDiggingActive.setText("NIE");
        }
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                labelMoney.setText(""+Main.economy.balance);
            }
        };
        timer.start();
    }

    @Override
    public void clear() {
        labelNotEnoughMoney.setVisible(false);
        labelIncorrectPasswordTransaction.setVisible(false);
        labelIncorrectPasswordSettings.setVisible(false);
        fieldPasswordSettings.clear();
        fieldReceiver.clear();
        fieldAmount.clear();
        fieldPasswordTransaction.clear();
    }

    public void button_confirmTransaction(ActionEvent event) throws NoSuchAlgorithmException {
        if(!settings.getHashedPassword().equals(Main.hashPassword(fieldPasswordTransaction.getText()))) {
            //set label that doesnt exist yet
            labelIncorrectPasswordTransaction.setVisible(true);
            return;
        }
        labelIncorrectPasswordTransaction.setVisible(false);
        if(Main.addTransactionToList(fieldReceiver.getText(),fieldAmount.getText())){
            labelNotEnoughMoney.setVisible(false);
            //pokaż okno dialogowe, że się udało
            fieldAmount.setText("");
            fieldReceiver.setText("");
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Trzos na Dolary");
            info.setHeaderText("Transakcję przekazano do potwierdzenia.");
            info.show();
        }
        else{
            labelNotEnoughMoney.setVisible(true);
        }
        fieldPasswordTransaction.clear();
    }

    public void button_publicKey(ActionEvent event) {
        File newPublicKey = (new FileChooser().showOpenDialog(buttonPublicKey.getScene().getWindow()));
        publicKey = newPublicKey;
        labelPublicKey.setText(newPublicKey.getAbsolutePath());
    }

    public void button_privateKey(ActionEvent event) {
        File newPrivateKey = (new FileChooser().showOpenDialog(buttonPrivateKey.getScene().getWindow()));
        privateKey = newPrivateKey;
        labelPrivateKey.setText(newPrivateKey.getAbsolutePath());
    }

    public void button_confirmSettings(ActionEvent event) throws NoSuchAlgorithmException {
        if(!settings.getHashedPassword().equals(Main.hashPassword(fieldPasswordSettings.getText()))){
            labelIncorrectPasswordSettings.setVisible(true);
            return;
        }
        labelIncorrectPasswordSettings.setVisible(false);
        settings.setUsername(fieldUsernameSettings.getText());
        if(publicKey!=null) {
            settings.setPublicKey(publicKey);
        }
        if(privateKey!=null) {
            settings.setPrivateKey(privateKey);
        }
        settings.setDig(radioDig.isSelected());
        settings.export();
        labelUsername.setText(settings.getUsername());
        fieldPasswordSettings.clear();
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Trzos na Dolary");
        info.setHeaderText("Ustawienia zostały zmienione");
        info.show();
    }

    public void button_logout(ActionEvent event) {
        Main.setView("login");
    }

    public void setMoney(double money){
        labelMoney.setText(""+money);
    }
}
