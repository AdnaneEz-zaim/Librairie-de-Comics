package fr.tse.prinfo3.control;

import fr.tse.prinfo3.model.Issue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import fr.tse.prinfo3.model.ResultIssue;
import fr.tse.prinfo3.model.SearchResultDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
	private ArrayList<Issue> listOfPrivateIssue = new ArrayList<Issue>();

	private Map<Issue, String> issueId = new HashMap<Issue, String>();

    @FXML
    private TextField researchField;
    
	
	@FXML
    void handleClickListView(MouseEvent event) throws IOException {
		
		
		Issue comics = listOfIssue.get(listOfComics.getSelectionModel().getSelectedIndex());
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Comics.fxml"));

		String id = ""+comics.getId();
		this.controller = new ComicsController(id);
		
		
        loader.setController(this.controller);
        
        AnchorPane comicsView = loader.load();
        
        rootAnchorPane.getChildren().setAll(comicsView);
		
		
    }
	@FXML
	public void handleClickPrivateList(MouseEvent event) throws IOException {

		System.out.println(listOfPrivateIssue.get(myListOfComics.getSelectionModel().getSelectedIndex()));
		Issue comics = listOfPrivateIssue.get(myListOfComics.getSelectionModel().getSelectedIndex());
		
		String idComic ="";
		for (Map.Entry<Issue, String> entry : issueId.entrySet()) {
			
			
			if(entry.getValue()==Integer.toString(comics.getId())) {
				idComic = ""+comics.getId();
			}

			
		}
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Comics.fxml"));
		
		
		this.controller = new ComicsController(idComic);
		
		
        loader.setController(this.controller);
       
        AnchorPane comicsView;
		comicsView = loader.load();

        rootAnchorPane.getChildren().setAll(comicsView);
        
        
        
    }
	
    
	
    
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        ComicVineService comicVineService = new ComicVineService();
        SearchResultDto result = comicVineService.searchLastesComics(30, 0);

        ObservableList<String> items =FXCollections.observableArrayList ();
        for (Issue res : result.getResults()) {
        	
        	
        	items.add(Integer.toString(res.getId()));
        	listOfIssue.add(res);
        	
        	
  		}		
        
        
        
        listOfComics.setItems(items);
       
        
        listOfComics.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            String titleComics = "";
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                	for (Issue res : result.getResults()) {
                		
                    	if(Integer.toString(res.getId()).compareTo(name)==0) {
                    		imageView.setImage(new Image(res.getImage().getIcon_url()));
                    		if(res.getName() != null) {

                        		titleComics = res.getName();
                    		}else {

                        		titleComics = "";
                    		}
                    	}
              		}
                    setText(titleComics);
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
			
	       
			ComicVineService comicVineService2;
			ResultIssue result2;
			Issue comics;

			
			for (String idComics : allComics) {
				comicVineService2 = new ComicVineService();
				result2 = comicVineService2.searchComics("4000-"+idComics);
				comics = result2.getResults();

				issueId.put(comics, idComics);
	  		}
			
			ObservableList<String> comicsName =FXCollections.observableArrayList ();
			
			for (Map.Entry<Issue, String> entry : issueId.entrySet()) {
				comicsName.add(entry.getValue());
				listOfPrivateIssue.add(entry.getKey());
				
			}

			myListOfComics.setItems(comicsName);
	
			
			myListOfComics.setCellFactory(param -> new ListCell<String>() {
				

            	String idComi = "";
            	
            	
	            @Override
	            public void updateItem(String name, boolean empty) {
	            	
	            	
	            	Button button;
	            	String titleComics = "";

                	
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

	                	for (Map.Entry<Issue, String> entry : issueId.entrySet()) {
	                		if(entry.getValue().compareTo(name)==0) {
	                    		 
	    	                	grid.addRow(1,  new ImageView(new Image(entry.getKey().getImage().getIcon_url())));
	    	                	this.idComi = entry.getValue();
	    	                	if(entry.getKey().getName() != null) {

	                        		titleComics = entry.getKey().getName();
	                    		}else {

	                        		titleComics = "";
	                    		}
	                    	
	                		}
	        				
	        			}
	                	
	                	 String id = this.idComi;                           
	                	 button = new Button("Supprimer");            
	                	 button.setOnAction(new EventHandler<ActionEvent>() {

	                         @Override
	                         public void handle(ActionEvent arg0) {
	                            String hostname = "localhost";
	                     		String db = "comicunivers";
	                     		String port = "3306";
	                     		String username = "root";
	                     		String password = "";
	                     		DatabaseOperations dbComic = new DatabaseOperations(hostname, db, port, username, password);
	                     		
	                     		
	                     		dbComic.deleteComicsUser(1, id);
	                     		
	                     		dbComic.close();
	                     		

	                         	myListOfComics.getItems().remove(getItem());

	                         }
	                     });   
	                
	                	grid.addRow(0, new Label(titleComics));
	                	grid.addColumn(0, button);
	                	setGraphic(grid);
	                }
	            }
	            
	            
	        });
			
		
      
		}
       

      		
    }
}
