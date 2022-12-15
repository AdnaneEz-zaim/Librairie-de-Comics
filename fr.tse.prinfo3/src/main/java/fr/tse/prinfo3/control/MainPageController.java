package fr.tse.prinfo3.control;

import fr.tse.prinfo3.model.ImagePOJO;
import fr.tse.prinfo3.model.Issue;
import fr.tse.prinfo3.model.SearchResultDto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class MainPageController implements Initializable {
	
	

    @FXML
    private GridPane rootGrid;
	  
	@FXML
    private ListView<String> listOfComics = new ListView<String>();
	
	protected ComicsController controller = null;
	
	private ArrayList<Issue> listOfIssue = new ArrayList<Issue>();
	
	@FXML
    void handleClickListView(MouseEvent event) throws IOException {
		
		
		Issue comics = listOfIssue.get(listOfComics.getSelectionModel().getSelectedIndex());
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Comics.fxml"));

		String id = "4000-"+comics.getId();
		this.controller = new ComicsController(id);
		
		
        loader.setController(this.controller);
        
        GridPane comicsView = loader.load();
        
        rootGrid.getChildren().setAll(comicsView);
		
		
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
