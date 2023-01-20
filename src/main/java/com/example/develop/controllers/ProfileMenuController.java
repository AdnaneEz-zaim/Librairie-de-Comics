package com.example.develop.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
	private AnchorPane anchorPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadPage("LibraryView");
	}

	@FXML
	private void profileManagement(MouseEvent event) {
		loadPage("ProfileManagement");
	}
	@FXML
	private void Library(MouseEvent event){
		loadPage("LibraryView");
	}
	@FXML
	private void logout(MouseEvent event) throws IOException, SQLException {
		UserModel.logout();
		DbConnection.getDatabaseConnection().closeConnection();

		Stage stage = (Stage) anchorPane.getScene().getWindow();
		stage.close();

		FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/RegisterView.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("TSE ComicVine!");
		stage.setScene(scene);
		stage.show();
	}
	 @FXML
	 void returnHandler(MouseEvent event) throws IOException {
		 Stage stage = (Stage) anchorPane.getScene().getWindow();
		 stage.close();

		 FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/MainPage.fxml"));
		 Scene scene = new Scene(fxmlLoader.load());
		 stage.setTitle("Hello!");
		 stage.setScene(scene);
		 stage.show();

	 }

	private void loadPage(String page) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ComicApplication.class.getResource("Views/"+page+".fxml"));
			Parent root = (Parent) loader.load();
			anchorPane.getChildren().add(root);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
