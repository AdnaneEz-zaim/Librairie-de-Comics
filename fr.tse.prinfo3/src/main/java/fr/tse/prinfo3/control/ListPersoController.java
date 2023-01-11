package fr.tse.prinfo3.control;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import fr.tse.prinfo3.model.Issue;
import fr.tse.prinfo3.model.ResultIssue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ListPersoController implements Initializable {

	private int typeList;

	private List<Issue> issues = new ArrayList<Issue>();
	private ArrayList<Issue> listOfIssue = new ArrayList<Issue>();


	@FXML
	private Pane ListPane;

	@FXML
	private ListView<String> listPerso;

	@FXML
	private AnchorPane rootAnchorPane;

	@FXML
	private Label titleListe;


	@FXML
	void handleClickListView(MouseEvent event) throws IOException {

		String idComics = listPerso.getItems().get(listPerso.getSelectionModel().getSelectedIndex());
		Issue comics = new Issue();
		for (Issue entry : listOfIssue) {
			if(Integer.toString(entry.getId()).compareTo(idComics)==0 ) {
				comics = entry;
			}
		}
		
		
		

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Comics.fxml"));


		ComicsController controller = new ComicsController(Integer.toString(comics.getId()));


		loader.setController(controller);

		AnchorPane comicsView;
		comicsView = loader.load();

		rootAnchorPane.getChildren().setAll(comicsView);

	}

	@FXML
	void returnHandler(MouseEvent event) throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainPage.fxml"));

		MainPageController controller = new MainPageController();

		loader.setController(controller);

		AnchorPane mainPage = loader.load();

		rootAnchorPane.getChildren().setAll(mainPage);

	}


	ListPersoController(int num){
		this.typeList = num;
	}


	public void initialize(URL url, ResourceBundle resourceBundle) {
		String hostname = "localhost";
		String db = "comicunivers";
		String port = "3306";
		String username = "root";
		String password = "";
		DatabaseOperations dbComic = new DatabaseOperations(hostname, db, port, username, password);
		String bibliotheque = "";


		switch(typeList) {

		case 1:
			bibliotheque = dbComic.selectComicsLu(1);
			titleListe.setText("List of readed comics");
			break;
		case 2:
			bibliotheque = dbComic.selectComicsAlire(1);
			titleListe.setText("List of comics to read");
			break;
		case 3:
			bibliotheque = dbComic.selectComicsEnCours(1);
			titleListe.setText("List of current comics");
			break;
		}

		dbComic.close();


		if(bibliotheque != null && (bibliotheque.compareTo("")!=0)) {


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

			ObservableList<String> comicsId =FXCollections.observableArrayList ();

			for (Issue entry : issues) {
				comicsId.add(Integer.toString(entry.getId()));
				listOfIssue.add(entry);

			}

			listPerso.setItems(comicsId);


			listPerso.setCellFactory(param -> new ListCell<String>() {


				String idComi = "";
				String titleComics = "";

				@Override
				public void updateItem(String id, boolean empty) {


					Button button;



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
								ImageView imgIcon = new ImageView(new Image(entry.getImage().getIcon_url()));
								imgIcon.setFitHeight(100);
								imgIcon.setFitWidth(75);
								imgIcon.setPreserveRatio(true);
								grid.addRow(1, imgIcon);
								this.idComi = Integer.toString(entry.getId());

								if(entry.getName() != null) {
									titleComics = entry.getName();
								}else {

									titleComics = "";
								}
							}
						}




						String idCom = this.idComi;                           
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

								switch(typeList) {

								case 1:
									dbComic.deleteComicsLu(1, idCom);
								case 2:
									dbComic.deleteComicsAlire(1, idCom);
								case 3:
									dbComic.deleteComicsEnCours(1, idCom);
								}


								dbComic.close();


								listPerso.getItems().remove(getItem());
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
