package fr.tse.prinfo3.control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ProfileMenuController implements Initializable {
	@FXML
	private BorderPane menuBorderPane;
	@FXML
	private AnchorPane profileAnchorPane;
	@FXML
	private AnchorPane displayAnchorPane;
	
	
	protected MainPageController controller = null;
	
	ProfileMenuController(){
	 }

	
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
	private void logout(MouseEvent event) {
		
	}
	
	 @FXML
	 void returnHandler(MouseEvent event) throws IOException {
		 
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainPage.fxml"));
		 
		 this.controller = new MainPageController();
			
	     loader.setController(this.controller);
	      
	     AnchorPane mainPage = loader.load();
	        
	     this.profileAnchorPane.getChildren().setAll(mainPage);

	 }
	 
	private void loadPage(String page) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource(page+".fxml"));
		} catch (IOException e) {
			Logger.getLogger(ProfileMenuController.class.getName()).log(Level.SEVERE, null, e);
		}
		menuBorderPane.setCenter(root);
	}
}
