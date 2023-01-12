package fr.tse.prinfo3.control;

import java.awt.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.controlsfx.control.Rating;

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
import javafx.scene.control.TextArea;
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
	 private Button addToBiblio;
	 
	 @FXML
	 private ListView<String> listCreateur;

	 
	 @FXML
	 private TextArea descComics;

	 @FXML
	 private ListView<String> listCharacter;
	 
	 private ArrayList<String> listOfCharacters = new ArrayList<String>();
	 

	
	 @FXML
	 private Text nameComic;
	 
	 @FXML
	 private ImageView imgComics;
	 
	 protected MainPageController controller = null;
	 protected CharacterController controllerCharac = null;
	 protected ProfileMenuController controllerProf = null;

	 
	 @FXML
	 private TextArea commentText;
	 
	 @FXML
	 private ListView<Text> listComment;
	 
	 private ArrayList<List<String>> comicsComments = new ArrayList<List<String>>();
	 
	 @FXML
	 private Rating rateComics;
	 
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
	 void addComicsToEnCours(MouseEvent event) {
		 String hostname = "localhost";
		 String db = "comicunivers";
		 String port = "3306";
		 String username = "root";
		 String password = "";
		 DatabaseOperations dbComic = new DatabaseOperations(hostname, db, port, username, password);
		 dbComic.insertComicsEnCours(1, this.id);
		 dbComic.close();
	 }

	 @FXML
	 void addComicsToLu(MouseEvent event) {
		 String hostname = "localhost";
		 String db = "comicunivers";
		 String port = "3306";
		 String username = "root";
		 String password = "";
		 DatabaseOperations dbComic = new DatabaseOperations(hostname, db, port, username, password);
		 dbComic.insertComicsLu(1, this.id);
		 dbComic.close();
	 }
	 
	 @FXML
	 void addComicsToAlire(MouseEvent event) {
		 String hostname = "localhost";
		 String db = "comicunivers";
		 String port = "3306";
		 String username = "root";
		 String password = "";
		 DatabaseOperations dbComic = new DatabaseOperations(hostname, db, port, username, password);
		 dbComic.insertComicsAlire(1, this.id);
		 dbComic.close();
	 }
	 
	 
	    

	 @FXML
	 void handlePersonnage(MouseEvent event) throws IOException {
		 
		 if(listCharacter.getSelectionModel().getSelectedIndex()>=0) {
		 
			 String id = "4005-"+listOfCharacters.get(listCharacter.getSelectionModel().getSelectedIndex());
			 
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Character.fxml"));
			 
			 
			 this.controllerCharac = new CharacterController(id, this.id);
			 
			 loader.setController(this.controllerCharac);
		      
		     AnchorPane CharacterAnchorPane = loader.load();
		        
		     ComicsAnchorPane.getChildren().setAll(CharacterAnchorPane);
		 }
	 }	 
	 

	 
	 @FXML
	    void handleClickProfileImage(MouseEvent event) throws IOException {
					
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ProfileMenu.fxml"));

			this.controllerProf = new ProfileMenuController();
			
	        loader.setController(this.controllerProf);
	        
	        AnchorPane comicsView = loader.load();
	        
	        ComicsAnchorPane.getChildren().setAll(comicsView);	
	    }
	 
	 @FXML
	 void returnHandler(MouseEvent event) throws IOException {
		 
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainPage.fxml"));
		 
		 this.controller = new MainPageController();
			
	     loader.setController(this.controller);
	      
	     AnchorPane mainPage = loader.load();
	        
	     ComicsAnchorPane.getChildren().setAll(mainPage);

	 }
	 
	 @FXML
	 void publishComment(MouseEvent event) throws IOException {
		 
		 String comment = commentText.getText();
		 String id_user = "1";	

		 
		 String hostname = "localhost";
		 String db = "comicunivers";
		 String port = "3306";
		 String username = "root";
		 String password = "";
		 DatabaseOperations dbComic = new DatabaseOperations(hostname, db, port, username, password);

		ObservableList<Text> comments =FXCollections.observableArrayList ();
		 dbComic.insertComment(comment, this.id, dbComic.getUsernameId(id_user));
		 comicsComments = dbComic.selectAllComments(this.id);
			for (List<String> com : comicsComments) {
					Text c = new Text(com.get(0)+":\n"+com.get(1));
					comments.add(c);
				
		        	
	  		}
			

			listComment.setItems(comments);
		 dbComic.close();
		 
		 

		 
	 }
	 
	 @FXML
	 void doRating(MouseEvent event) throws IOException {
		 double note = rateComics.getRating();
		 String id_user = "1";
		 
		 String hostname = "localhost";
		 String db = "comicunivers";
		 String port = "3306";
		 String username = "root";
		 String password = "";
		 DatabaseOperations dbComic = new DatabaseOperations(hostname, db, port, username, password);
		 dbComic.insertNotation(note, this.id, dbComic.getUsernameId(id_user));
		 dbComic.close();
		 
		 ComicsAnchorPane.getScene().getWindow().setWidth(ComicsAnchorPane.getScene().getWidth() + 0.001);
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
		
		listCharacter.setCellFactory(param -> new ListCell<String>() {
            private Text namePerson = new Text();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setText(null);
                } else {
                	for (OtherCredits personne : comics.getCharacter_credits()) {
                    	
                    	if(personne.getName() == name) {
                    		namePerson.setText(name);
                    	}
              		}
                    setText(namePerson.getText());
                }
            }
        });
		
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
		
		imgComics.setImage(new Image(comics.getImage().getMedium_url()));
        
		
		ObservableList<Text> comments =FXCollections.observableArrayList ();
		String hostname = "localhost";
		String db = "comicunivers";
		String port = "3306";
		String username = "root";
		String password = "";
		DatabaseOperations dbComic = new DatabaseOperations(hostname, db, port, username, password);
		
		comicsComments = dbComic.selectAllComments(this.id);
		for (List<String> com : comicsComments) {
				Text c = new Text(com.get(0)+":\n"+com.get(1));
				comments.add(c);
			
	        	
  		}

		listComment.setItems(comments);
		//rajouter le fait que si un commentaire est publié il puisse être affiché directement dans la ListView
		
		rateComics.setRating(dbComic.getNotation(this.id));
		
		
		
	}
	
	
	
	
	
	
	
	

}
