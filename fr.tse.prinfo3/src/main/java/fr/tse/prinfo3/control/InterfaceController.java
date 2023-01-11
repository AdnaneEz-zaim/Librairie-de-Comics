package fr.tse.prinfo3.control;

import java.net.URL;
import java.util.ResourceBundle;

import fr.tse.prinfo3.view.Interface;
import fr.tse.prinfo3.view.openMenuFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;

public class InterfaceController implements Initializable {

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	 @FXML
	 void lancerRecherche(ActionEvent event) 
	 {
		 openMenuFXML.main(null);
	 }

}
