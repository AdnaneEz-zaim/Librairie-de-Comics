package com.example.develop.controllers;

import com.example.develop.helper.AlertHelper;
import com.example.develop.model.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class profileManagementController implements Initializable {
    Window window;
    @FXML
    private TextField usernameInput;
    @FXML
    private TextField firstnameInput;
    @FXML
    private TextField lastnameInput;
    @FXML
    private TextField emailInput;
    @FXML
    private TextField passwordInput;
    @FXML
    private TextField changePasswordInput;
    @FXML
    private TextField confirmPasswordInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameInput.setText(UserModel.getUserModel().getUsername());
        firstnameInput.setText(UserModel.getUserModel().getFirstname());
        lastnameInput.setText(UserModel.getUserModel().getLastname());
        emailInput.setText(UserModel.getUserModel().getEmail());
    }

    public Boolean isPasswordCorrect(){
        if(UserModel.getUserModel().getPassword() != passwordInput.getText()){
            return false;
        }
        return true;
    }

    private boolean isValidated() {

        window = passwordInput.getScene().getWindow();
        if (firstnameInput.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "First name text field cannot be blank.");
            firstnameInput.requestFocus();
        } else if (firstnameInput.getText().length() < 2 || firstnameInput.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "First name text field cannot be less than 2 and greator than 25 characters.");
            firstnameInput.requestFocus();
        } else if (lastnameInput.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Last name text field cannot be blank.");
            lastnameInput.requestFocus();
        } else if (lastnameInput.getText().length() < 2 || lastnameInput.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Last name text field cannot be less than 2 and greator than 25 characters.");
            lastnameInput.requestFocus();
        } else if (emailInput.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Email text field cannot be blank.");
            emailInput.requestFocus();
        } else if (emailInput.getText().length() < 5 || emailInput.getText().length() > 45) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Email text field cannot be less than 5 and greator than 45 characters.");
            emailInput.requestFocus();
        } else if (usernameInput.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Username text field cannot be blank.");
            usernameInput.requestFocus();
        } else if (usernameInput.getText().length() < 5 || usernameInput.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Username text field cannot be less than 5 and greator than 25 characters.");
            usernameInput.requestFocus();
        } else if (passwordInput.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Password text field cannot be blank.");
            passwordInput.requestFocus();
        } else if (passwordInput.getText().length() < 5 || passwordInput.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Password text field cannot be less than 5 and greator than 25 characters.");
            passwordInput.requestFocus();
        } else if (isPasswordCorrect()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "The password is Incorrect.");
            passwordInput.requestFocus();
        } else {
            return true;
        }
        return false;
    }
    private boolean isPasswordChangeValidated(){
        if(!changePasswordInput.getText().equals(""))
            if(confirmPasswordInput.getText().equals(changePasswordInput.getText()))
                return true;
        return false;

    }

    @FXML
    public void saveUserInfo() {
        if(isValidated()){
            UserModel.getUserModel().setEmail(emailInput.getText());
            UserModel.getUserModel().setFirstname(firstnameInput.getText());
            UserModel.getUserModel().setLastname(lastnameInput.getText());
            UserModel.getUserModel().setUsername(usernameInput.getText());
            if(isPasswordChangeValidated()){
                UserModel.getUserModel().setPassword(changePasswordInput.getText());
            }
            try {
                if(DbConnection.changeUserInfo()){
                    AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Information",
                            "Data changed successfully.");
                }else{
                    AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Information",
                            "Error changing data.");
                }
            }catch (SQLException ex) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                        "Something went wrong.");
            }

        }


    }
}
