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

public class ProfileMenuController implements Initializable {
	@FXML
	private BorderPane bp;
	
	@FXML
	private AnchorPane ap;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	@FXML
	private void summary(MouseEvent event) {
		bp.setCenter(ap);
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
	
	private void loadPage(String page) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource(page+".fxml"));
		} catch (IOException e) {
			Logger.getLogger(ProfileMenuController.class.getName()).log(Level.SEVERE, null, e);
		}
		bp.setCenter(root);
	}
}
