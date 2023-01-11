package fr.tse.prinfo3.control;

import fr.tse.prinfo3.model.Issue;
import fr.tse.prinfo3.model.SearchResultDto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class MainPageController implements Initializable {
	
	

    @FXML
    private AnchorPane rootAnchorPane;
	  
	@FXML
    private ListView<String> listOfComics = new ListView<String>();
	
	protected ComicsController controller = null;
	
	protected ProfileMenuController controllerProf = null;
	
	private ArrayList<Issue> listOfIssue = new ArrayList<Issue>();
	
	@FXML
    void handleClickListView(MouseEvent event) throws IOException {
		
		
		Issue comics = listOfIssue.get(listOfComics.getSelectionModel().getSelectedIndex());
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Comics.fxml"));

		String id = "4000-"+comics.getId();
		this.controller = new ComicsController(id);
		
		
        loader.setController(this.controller);
        
        AnchorPane comicsView = loader.load();
        
        rootAnchorPane.getChildren().setAll(comicsView);
		
		
    }
	
	@FXML
    void handleClickProfileImage(MouseEvent event) throws IOException {
				
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ProfileMenu.fxml"));

		this.controllerProf = new ProfileMenuController();
		
        loader.setController(this.controllerProf);
        
        AnchorPane profileAnchorPane = loader.load();
        
        rootAnchorPane.getChildren().setAll(profileAnchorPane);	
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
      
        
       

      		
    }
}
