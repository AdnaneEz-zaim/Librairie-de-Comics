package fr.tse.prinfo3.control;

import fr.tse.prinfo3.model.ImagePOJO;
import fr.tse.prinfo3.model.Issue;
import fr.tse.prinfo3.model.SearchResultDto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;


public class MainPageController implements Initializable {

	@FXML
    private ListView<String> listOfComics = new ListView<String>();
    
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        ComicVineService comicVineService = new ComicVineService();
        SearchResultDto result2 = comicVineService.searchLastesComics(30, 0);

        ObservableList<String> items =FXCollections.observableArrayList ();
        for (Issue res2 : result2.getResults()) {
        	
        	if(res2.getName() !=null) {
        		items.add(res2.getName());

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
                	for (Issue res2 : result2.getResults()) {
                    	
                    	if(res2.getName() == name) {
                    		imageView.setImage(new Image(res2.getImage().getIcon_url()));
                    	}
              		}
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });
      
        
       

      		
    }
}
