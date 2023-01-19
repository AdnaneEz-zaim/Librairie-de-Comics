package fr.tse.prinfo3.control;

import fr.tse.prinfo3.model.DatabaseOperations;
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
import java.util.List;
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
	
	protected ProfileMenuController controllerProf = null;
	

	protected ListPersoController controllerListPerso = null;

	private ArrayList<Issue> listOfIssue = new ArrayList<Issue>();
	private ArrayList<Issue> listOfPrivateIssue = new ArrayList<Issue>();

	private List<Issue> issues = new ArrayList<Issue>();

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

		
	String idComics = myListOfComics.getItems().get(myListOfComics.getSelectionModel().getSelectedIndex());
		Issue comics = new Issue();
		for (Issue entry : listOfIssue) {
			if(Integer.toString(entry.getId()).compareTo(idComics)==0 ) {
				comics = entry;
			}
		}


		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Comics.fxml"));


		this.controller = new ComicsController(Integer.toString(comics.getId()));


		loader.setController(this.controller);

		AnchorPane comicsView;
		comicsView = loader.load();

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
	
    

	@FXML
	void openComicsAlire(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/listPerso.fxml"));
		ListPersoController controllerListPerso = new ListPersoController(2);

		loader.setController(controllerListPerso);

		AnchorPane comicsView;
		comicsView = loader.load();

		rootAnchorPane.getChildren().setAll(comicsView);

	}

	@FXML
	void openComicsEnCours(MouseEvent event) throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/listPerso.fxml"));
		ListPersoController controllerListPerso = new ListPersoController(3);

		loader.setController(controllerListPerso);

		AnchorPane comicsView;
		comicsView = loader.load();

		rootAnchorPane.getChildren().setAll(comicsView);

	}

	@FXML
	void openComicsLu(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/listPerso.fxml"));
		this.controllerListPerso = new ListPersoController(1);

		loader.setController(controllerListPerso);

		AnchorPane comicsView;
		comicsView = loader.load();

		rootAnchorPane.getChildren().setAll(comicsView);

	}


	public void initialize(URL url, ResourceBundle resourceBundle) {

		ComicVineService comicVineService = new ComicVineService();
		SearchResultDto result = comicVineService.searchLastesComics(15, 0);

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
							Image imgIcon = new Image(res.getImage().getIcon_url());

							imageView.setImage(imgIcon);
							imageView.setFitHeight(100);
							imageView.setFitWidth(75);
							imageView.setPreserveRatio(true);
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

				issues.add(comics);
			}

			ObservableList<String> privateComicsId =FXCollections.observableArrayList ();

			for (Issue entry : issues) {
				privateComicsId.add(Integer.toString(entry.getId()));
				listOfPrivateIssue.add(entry);

			}

			myListOfComics.setItems(privateComicsId);


			myListOfComics.setCellFactory(param -> new ListCell<String>() {


				String idComi = "";


				@Override
				public void updateItem(String id, boolean empty) {


					Button button;
					String titleComics = "";


					final GridPane grid;
					{
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						grid = new GridPane();

						setText(null);
					}




					super.updateItem(id, empty);
					if (empty) {
						setText(null);
						setGraphic(null);
					} else {
						grid.getChildren().clear();
						for (Issue entry : issues) {

							if(Integer.toString(entry.getId()).compareTo(id)==0) {
								ImageView imageView = new ImageView(new Image(entry.getImage().getIcon_url()));
								imageView.setFitHeight(100);
								imageView.setFitWidth(75);
								imageView.setPreserveRatio(true);

								grid.addRow(1, imageView);
								this.idComi = Integer.toString(entry.getId());

								if(entry.getName() != null) {
									titleComics = entry.getName();
								}else {

									titleComics = "";
								}
							}
						}

						String idCom = this.idComi;                           
						button = new Button("Remove");            
						button.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent arg0) {
								String hostname = "localhost";
								String db = "comicunivers";
								String port = "3306";
								String username = "root";
								String password = "";
								DatabaseOperations dbComic = new DatabaseOperations(hostname, db, port, username, password);


								dbComic.deleteComicsUser(1, idCom);

								dbComic.close();


								myListOfComics.getItems().remove(getItem());

							}
						});   

						grid.addRow(0, new Label(titleComics));
						grid.addColumn(1, button);
						setGraphic(grid);
					}
				}


			});



		}



	}

}
