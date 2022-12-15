package fr.tse.prinfo3.control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fr.tse.prinfo3.model.Issue;
import fr.tse.prinfo3.model.PersonCredits;
import fr.tse.prinfo3.model.ResultIssue;
import fr.tse.prinfo3.model.SearchResultDto;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
	 private Text nameComic;
	 
	 @FXML
	 private ImageView imgComics;
	 
	 protected MainPageController controller = null;
	 
	 ComicsController(String id){
		 this.id=id;
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
		ResultIssue result2 = comicVineService.searchComics(this.id);
        
		
        Issue comics = result2.getResults();
        
        
        nameComic.setText(comics.getName());

        descComics.setText(comics.getDescription());
        
        ObservableList<String> items =FXCollections.observableArrayList ();
        
		for (PersonCredits personne : comics.getPerson_credits()) {
        	
        	if(personne.getName() !=null) {
        		items.add(personne.getName());

        	}
  		}		
        
        
        
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
