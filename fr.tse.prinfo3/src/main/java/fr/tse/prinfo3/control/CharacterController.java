package fr.tse.prinfo3.control;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fr.tse.prinfo3.model.Issue;
import fr.tse.prinfo3.model.OtherCredits;
import fr.tse.prinfo3.model.ResultCharacter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class CharacterController implements Initializable {
	
	private String id;
	private String lastComics;
	
	@FXML
    private AnchorPane CharacterAnchorPane;

    @FXML
    private ImageView imgCharacter;

    @FXML
    private Text nameCharacter;

    @FXML
    private Button returnButton;

    @FXML
    private TextField textDescription;

    @FXML
    private TextField textOrigine;
    
    @FXML
    private ListView<String> listComics;

    protected ComicsController controller = null;
    

	private ArrayList<String> listOfIssue = new ArrayList<String>();
	
    @FXML
    void handleClickListView(MouseEvent event) throws IOException {

    	String id = "4000-"+listOfIssue.get(listComics.getSelectionModel().getSelectedIndex());
		 
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Comics.fxml"));
		 
		 
		 this.controller = new ComicsController(id);
		 
		 loader.setController(this.controller);
	      
	     AnchorPane ComicsAnchorPane = loader.load();
	        
	     CharacterAnchorPane.getChildren().setAll(ComicsAnchorPane);
	     
    }

    
    
	CharacterController(String id, String lastComics){
		 this.id=id;
		 this.lastComics = lastComics;
	 }
	
    @FXML
    void returnHandler(MouseEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Comics.fxml"));
		 
		 this.controller = new ComicsController(this.lastComics);
			
	     loader.setController(this.controller);
	      
	     AnchorPane comicsPage = loader.load();
	        
	     CharacterAnchorPane.getChildren().setAll(comicsPage);

    	
    }
	
	
	public void initialize(URL location, ResourceBundle resources) {
		
		ComicVineService comicVineService = new ComicVineService();
		ResultCharacter result = comicVineService.searchCharacter(this.id);
		
		fr.tse.prinfo3.model.Character character = result.getResults();
		
		nameCharacter.setText(character.getName());

		textDescription.setText(character.getDescription());
		textOrigine.setText(character.getDeck());
		imgCharacter.setImage(new Image(character.getImage().getIcon_url()));
		
		
		ObservableList<String> items =FXCollections.observableArrayList ();
        for (OtherCredits res : character.getIssue_credits()) {
        	
        	if(res.getName() !=null) {
        		items.add(res.getName());
        		listOfIssue.add(res.getId());
        	}
        	
  		}		
        
        
        
        listComics.setItems(items);
        
    
		
	}

}
