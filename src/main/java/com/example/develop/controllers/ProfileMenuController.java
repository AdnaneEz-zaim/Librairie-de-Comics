package com.example.develop.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.develop.ComicApplication;
import com.example.develop.model.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ProfileMenuController implements Initializable {
	@FXML
	private BorderPane menuBorderPane;
	@FXML
	private AnchorPane displayAnchorPane;


	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	@FXML
	private void summary(MouseEvent event) {
		menuBorderPane.setCenter(displayAnchorPane);
	}
	@FXML
	private void profileManagement(MouseEvent event) {
		loadPage("ProfileManagement");
	}
	@FXML
	private void settings(MouseEvent event) {
		loadPage("Settings");

	}
	@FXML
	private void logout(MouseEvent event) throws IOException {
		UserModel.logout();
		Stage stage = (Stage) menuBorderPane.getScene().getWindow();
		stage.close();

		FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/RegisterView.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("TSE ComicVine!");
		stage.setScene(scene);
		stage.show();
	}
	 @FXML
	 void returnHandler(MouseEvent event) throws IOException {
		 Stage stage = (Stage) menuBorderPane.getScene().getWindow();
		 stage.close();

		 FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/MainPage.fxml"));
		 Scene scene = new Scene(fxmlLoader.load());
		 stage.setTitle("Hello!");
		 stage.setScene(scene);
		 stage.show();

	 }

	private void loadPage(String page) {
		Parent root = null;
		try {
            System.out.println(ComicApplication.class.getResource("Views/"+page+".fxml"));
			root = FXMLLoader.load(ComicApplication.class.getResource("Views/"+page+".fxml"));
		} catch (IOException e) {
			Logger.getLogger(ProfileMenuController.class.getName()).log(Level.SEVERE, null, e);
		}
		menuBorderPane.setCenter(root);
	}
}
