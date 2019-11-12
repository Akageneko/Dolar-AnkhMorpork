package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ControllerFirstGreeting extends PersonalizedController {

    @FXML
    private Button buttonExit;

    public void button_newUser(ActionEvent event){
        Main.setView("sign_up");
    }

    public void button_exit(ActionEvent event){
        Main.exit(event);
    }

}
