package fr.tse.prinfo3.control;

import java.net.URL;
import java.util.ResourceBundle;

import fr.tse.prinfo3.model.Issue;
import fr.tse.prinfo3.model.SearchResultDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import javafx.fxml.Initializable;

public class ComicsController implements Initializable {



	 @FXML
	 private Text descComics;

	 @FXML
	 private ListView<String> listCreateur;

	 @FXML
	 private Text nameComic;

	    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		ComicVineService comicVineService = new ComicVineService();
        SearchResultDto result2 = comicVineService.searchComics("4000-959663");
/*
        Issue comics = new Issue();
        
        ObservableList<String> items =FXCollections.observableArrayList ();
        for (Issue res2 : result2.getResults()) {
        	
        	comics = res2;
        	System.out.println(res2);
        	
  		}
        
        nameComic.setText(comics.getName());
        descComics.setText(comics.getDescription());
        
		*/
	}
	
	
	
	

}
