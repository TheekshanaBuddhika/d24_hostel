package lk.ijse.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import lk.ijse.bo.BoFactory;
import lk.ijse.bo.custom.UserBo;
import lk.ijse.controllers.util.Navigation;
import lk.ijse.controllers.util.Rout;
import lk.ijse.controllers.util.Validation;
import lk.ijse.dto.UserDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInFormController implements Initializable {

    @FXML
    private Label hidelbl;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private Line pwLine;

    @FXML
    private PasswordField pwTxt;

    @FXML
    private ToggleButton pwViewBtn;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton signUpBtn;

    @FXML
    private TextField userNameTxt;

    @FXML
    private Line usrNameLine;

    @FXML
    private ImageView viewImg;


    private final UserBo userBo = BoFactory.getInstance().getBo(BoFactory.BOTypes.USER);
    boolean pw, usr;
    public static String Gl0bUsrName;
    @FXML
    void loginBtnOnAction(ActionEvent event) {
        validation();
        if (usr && pw ){
            UserDTO isUser=userBo.getUser(new UserDTO(userNameTxt.getText(),pwTxt.getText()));
            if (isUser!=null){
                Gl0bUsrName=userNameTxt.getText();

                if (pwTxt.getText().equals(isUser.getPassword())){
                    try {
                        Navigation.navigation(Rout.DASH_BOARD,root);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    Validation.shakeLine(pwLine);
                }
            }else {
                Validation.shakeLine(usrNameLine);
                Validation.shakeLine(pwLine);
            }
        }
    }

    @FXML
    void pwTxtOnAction(ActionEvent event) {
        loginBtn.fire();
    }



    @FXML
    void signUpBtnOnAction(ActionEvent event)  {
        try {
            Navigation.navigation(Rout.SIGN_UP, root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void userNameTxtOnAction(ActionEvent event) {
        pwTxt.requestFocus();
    }

    @FXML
    void pwViewBtnOnAction(ActionEvent event) {

        if(pwViewBtn.isSelected()){
            hidelbl.setVisible(true);
            hidelbl.textProperty().bind(Bindings.concat(pwTxt.getText()));
        }else{
            hidelbl.setVisible(false);
        }

    }

    @FXML
    void pwtyped(KeyEvent event) {
        hidelbl.textProperty().bind(Bindings.concat(pwTxt.getText()));
    }

    private void validation() {
        pw = false;
        usr = false;
        usr = Validation.txtValidation(userNameTxt, usrNameLine);
        pw = Validation.pwValidation(pwTxt, pwLine);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hidelbl.setVisible(false);
    }
}
