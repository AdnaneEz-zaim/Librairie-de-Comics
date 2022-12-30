package fr.tse.prinfo3.control;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import fr.tse.prinfo3.model.Issue;
import fr.tse.prinfo3.model.OtherCredits;
import fr.tse.prinfo3.model.PersonCredits;
import fr.tse.prinfo3.model.ResultIssue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.fxml.Initializable;

public class ComicsController implements Initializable {


	private String id;
	
	 @FXML
	 private AnchorPane ComicsAnchorPane;

	 @FXML
	 private TextField descComics;

	 @FXML
	 private ListView<String> listCreateur;
	 
	 

	 @FXML
	 private ListView<String> listCharacter;
	 
	 private ArrayList<String> listOfCharacters = new ArrayList<String>();
	 
	 
	 @FXML
	 private Text nameComic;
	 
	 @FXML
	 private ImageView imgComics;
	 
	 protected MainPageController controller = null;
	 protected CharacterController controllerCharac = null;
	 
	 
	 ComicsController(String id){
		 this.id=id;
	 }


	 @FXML
	 void addComicsToBibliotheque(MouseEvent event) {
		 String hostname = "localhost";
		 String db = "comicunivers";
		 String port = "3306";
		 String username = "root";
		 String password = "";
		 DatabaseOperations dbComic = new DatabaseOperations(hostname, db, port, username, password);
		 dbComic.insertComicsUser(1, this.id);
		 dbComic.close();
	 }
	    

	 @FXML
	 void handlePersonnage(MouseEvent event) throws IOException {
		 
		 String id = "4005-"+listOfCharacters.get(listCharacter.getSelectionModel().getSelectedIndex());
		 
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Character.fxml"));
		 
		 
		 this.controllerCharac = new CharacterController(id, this.id);
		 
		 loader.setController(this.controllerCharac);
	      
	     AnchorPane CharacterAnchorPane = loader.load();
	        
	     ComicsAnchorPane.getChildren().setAll(CharacterAnchorPane);
			
	 }	 
	 
	 @FXML
	 void returnHandler(MouseEvent event) throws IOException {
		 
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainPage.fxml"));
		 
		 this.controller = new MainPageController();
			
	     loader.setController(this.controller);
	      
	     AnchorPane mainPage = loader.load();
	        
	     ComicsAnchorPane.getChildren().setAll(mainPage);

	 }
	 
	    
	public void initialize(URL location, ResourceBundle resources) {
		
		
		ComicVineService comicVineService = new ComicVineService();
		ResultIssue result2 = comicVineService.searchComics("4000-"+this.id);
        
		
        Issue comics = result2.getResults();
        
        
        nameComic.setText(comics.getName());

        descComics.setText(comics.getDescription());
        
        ObservableList<String> items =FXCollections.observableArrayList ();

        ObservableList<String> characters =FXCollections.observableArrayList ();
        
		for (PersonCredits personne : comics.getPerson_credits()) {
        	
        	if(personne.getName() !=null) {
        		items.add(personne.getName());
        		
        	}
  		}		
		 
		for (OtherCredits character : comics.getCharacter_credits()) {
        	
        	if(character.getName() !=null) {
        		characters.add(character.getName());
        		listOfCharacters.add(character.getId());
        	}
  		}	
        
		listCharacter.setItems(characters);
		listCreateur.setItems(items);
        
		listCreateur.setCellFactory(param -> new ListCell<String>() {
            private Text namePerson = new Text();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setText(null);
                } else {
                	for (PersonCredits personne : comics.getPerson_credits()) {
                    	
                    	if(personne.getName() == name) {
                    		namePerson.setText(personne.getRole());
                    	}
              		}
                    setText(name + " : "+namePerson.getText());
                }
            }
        });
		
		imgComics.setImage(new Image(comics.getImage().getIcon_url()));
        
        
		
	}
	
	
	
	

}
