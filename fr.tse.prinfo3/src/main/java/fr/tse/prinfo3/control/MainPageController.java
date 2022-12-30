package fr.tse.prinfo3.control;

import fr.tse.prinfo3.model.Issue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import fr.tse.prinfo3.model.ResultIssue;
import fr.tse.prinfo3.model.SearchResultDto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class MainPageController implements Initializable {
	
	

    @FXML
    private AnchorPane rootAnchorPane;
	  
	@FXML
    private ListView<String> listOfComics = new ListView<String>();
	

    @FXML
    private ListView<String> myListOfComics;
	
	protected ComicsController controller = null;
	
	private ArrayList<Issue> listOfIssue = new ArrayList<Issue>();

    @FXML
    private TextField researchField;
    
    @FXML
    private Button deleteComics;
	
	@FXML
    void handleClickListView(MouseEvent event) throws IOException {
		
		
		Issue comics = listOfIssue.get(listOfComics.getSelectionModel().getSelectedIndex());
		
		System.out.println(comics.getId());
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Comics.fxml"));

		String id = ""+comics.getId();
		this.controller = new ComicsController(id);
		
		
        loader.setController(this.controller);
        
        AnchorPane comicsView = loader.load();
        
        rootAnchorPane.getChildren().setAll(comicsView);
		
		
    }
	
    @FXML
    void removeComics(MouseEvent event) {

    	int selectedComics = myListOfComics.getSelectionModel().getSelectedIndex();
    	
    	String idComicsToRemove = "";
    	String nameComics = myListOfComics.getItems().get(selectedComics);
    	nameComics = nameComics.replace("[", "");
    	nameComics = nameComics.replace("]", "");
    	for (Issue res : listOfIssue) {
        	if(res.getName().compareTo(nameComics)==0) {
        		idComicsToRemove = ""+res.getId();
        	}
  		}
        
    	
        
        String hostname = "localhost";
		String db = "comicunivers";
		String port = "3306";
		String username = "root";
		String password = "";
		DatabaseOperations dbComic = new DatabaseOperations(hostname, db, port, username, password);
		
		
		dbComic.deleteComicsUser(1, idComicsToRemove);
		
		dbComic.close();
		

    	myListOfComics.getItems().remove(selectedComics);
    }
	
	
	
	
    
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        ComicVineService comicVineService = new ComicVineService();
        SearchResultDto result = comicVineService.searchLastesComics(30, 0);

        ObservableList<String> items =FXCollections.observableArrayList ();
        for (Issue res : result.getResults()) {
        	
        	if(res.getName() !=null) {
        		items.add(res.getName());
        		listOfIssue.add(res);
        	}
        	
  		}		
        
        
        
        listOfComics.setItems(items);
        
        listOfComics.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                	for (Issue res : result.getResults()) {
                    	
                    	if(res.getName() == name) {
                    		imageView.setImage(new Image(res.getImage().getIcon_url()));
                    	}
              		}
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });
        
		String hostname = "localhost";
		String db = "comicunivers";
		String port = "3306";
		String username = "root";
		String password = "";
		DatabaseOperations dbComic = new DatabaseOperations(hostname, db, port, username, password);
		
		String bibliotheque = dbComic.selectBibliotheque(1);
		
		
		
		//dbComic.insertComicsUser(0, "960026");
		
		dbComic.close();
		
		if(bibliotheque.compareTo("")!=0) {
			
			
			
			String[] allComics = bibliotheque.split(",");
			
			ArrayList<Issue> Issues = new ArrayList<Issue>();
	       
			ComicVineService comicVineService2;
			ResultIssue result2;
			Issue comics;
			
			for (String idComics : allComics) {
				comicVineService2 = new ComicVineService();
				result2 = comicVineService2.searchComics("4000-"+idComics);
				comics = result2.getResults();
				Issues.add(comics);
				
	  		}

			
        	
			ObservableList<String> nameIssues =FXCollections.observableArrayList ();
			

			for (Issue Issue : Issues) {
	        	
				nameIssues.add(Issue.getName());
	  		}
			
			
			myListOfComics.setItems(nameIssues);
	
			
			myListOfComics.setCellFactory(param -> new ListCell<String>() {
				
				
				
				

	            @Override
	            public void updateItem(String name, boolean empty) {
	            	
	            	
					
	            	
	            	final GridPane grid;
	                {
	                  setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	                  grid = new GridPane();
	                  
	                  setText(null);
	                }
	                
	               
	                super.updateItem(name, empty);
	                if (empty) {
	                    setText(null);
	                    setGraphic(null);
	                } else {
	                	grid.getChildren().clear();
	                	for (Issue res : Issues) {
	                    	if(res.getName() == name) {
	                    		
	    	                	grid.addRow(1,  new ImageView(new Image(res.getImage().getIcon_url())));
	                    	}
	              		}
	                	grid.addRow(0, new Label(name));
	                	setGraphic(grid);
	                }
	            }
	            
	            
	        });
			
		
      
		}
       

      		
    }
}
