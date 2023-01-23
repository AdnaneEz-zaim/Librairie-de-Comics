package com.example.develop.controllers;

import com.example.develop.ComicApplication;
import com.example.develop.helper.AlertHelper;
import com.example.develop.model.UserModel;
import com.example.develop.service.DbConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private final Connection con;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    Window window;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public LoginController() {
        DbConnection dbc = DbConnection.getDatabaseConnection();
        con = dbc.getConnection();
    }

    /** check if user exists in DB if true then save it as current user */
    @FXML
    private void login() throws Exception {
        if (this.isValidated()) {
            ResultSet rs;
            try {
                rs = DbConnection.login(username.getText(),password.getText());

                if (rs.next()) {
                    SaveUser(rs);
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.close();

                    FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/MainPage.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setTitle("ComicsRead");
                    stage.setScene(scene);
                    stage.show();

                } else {
                    AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                            "Invalid username and password.");
                    username.requestFocus();
                }
                rs.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

    /** check if all inputs are correctly filled   */
    private boolean isValidated() {

        window = loginButton.getScene().getWindow();
        if (username.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Username text field cannot be blank.");
            username.requestFocus();
        } else if (username.getText().length() < 5 || username.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Username text field cannot be less than 5 and greator than 25 characters.");
            username.requestFocus();
        } else if (password.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Password text field cannot be blank.");
            password.requestFocus();
        } else if (password.getText().length() < 5 || password.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Password text field cannot be less than 5 and greator than 25 characters.");
            password.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    /** redirection to SignUp   */
    @FXML
    private void showRegisterStage() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/RegisterView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("TSE ComicVine!");
        stage.setScene(scene);
        stage.show();
    }

    /** save current user (Singleton)  */
    private void SaveUser(ResultSet rs) throws SQLException {
        UserModel userModel = UserModel.getUserModel();
        userModel.setUserid(rs.getInt(1));
        userModel.setEmail(rs.getString(2));
        userModel.setPassword(rs.getString(3));
        userModel.setUsername(rs.getString(4));
        userModel.setFirstname(rs.getString(5));
        userModel.setLastname(rs.getString(6));
    }
}