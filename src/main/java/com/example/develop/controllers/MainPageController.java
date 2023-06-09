package com.example.develop.controllers;

import com.example.develop.ComicApplication;
import com.example.develop.helper.AlertHelper;
import com.example.develop.service.ComicVineService;
import com.example.develop.model.Comic;
import com.example.develop.model.ObjectClicked;
import com.example.develop.model.UserModel;
import com.example.develop.service.DbConnection;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;


public class MainPageController implements Initializable {
	public static Boolean isInMainPage = true;
	private final Connection con;
	private final int idUser = UserModel.getUserModel().getUserid();
	int resultSize;
	Window window;
	@FXML
	private ListView<Comic> listOfComics = new ListView<Comic>();
	@FXML
	private ListView<Comic> myListOfComics = new ListView<Comic>();;
	@FXML
	private ChoiceBox stateList;
	private ObservableList<Comic> items;
	private ComicVineService comicVineService = ComicVineService.getComicVineService();


	public MainPageController() {
		DbConnection dbc = DbConnection.getDatabaseConnection();
		con = dbc.getConnection();
	}

	public CompletableFuture<JsonNode> getLatestComics() throws IOException {
		return comicVineService.searchLatestComics(10, 0)
				.thenApply(result -> {
					for (int i = 0; i < result.size(); i++) {
						JsonNode element = result.get(i);
						if (element.get("name").isNull()) {
							if(element.get("volume").get("name").isNull()){
								((ArrayNode) result).remove(i);
								i--;
							}
						}
					}
					return result;
				});
	}

	/** renders the latest comics in listView  */
	public void initLatestComics() throws IOException {

		CompletableFuture<JsonNode> future = getLatestComics();
		future.thenAccept(latestComics -> Platform.runLater(()-> {
			// Do something with the latest comics
			ObservableList<Comic> items = FXCollections.observableArrayList();
			for (JsonNode res : latestComics) {
				Comic comic = new Comic();
				if(res.get("name").isNull()){
					comic.setName(res.get("volume").get("name").textValue()+" #"+res.get("issue_number"));

				}
				else{
					comic.setName(res.get("name").textValue());
				}
				comic.setId(String.valueOf(res.get("id")));
				comic.setImage(res.get("image").get("icon_url").textValue());
				items.add(comic);
			}

			listOfComics.setItems(items);
			listOfComics.setCellFactory(param -> new ListCell<Comic>() {
				private ImageView imageView = new ImageView();

				@Override
				public void updateItem(Comic comic, boolean empty) {
					super.updateItem(comic, empty);
					if (empty) {
						setText(null);
						setGraphic(null);
					} else {
						imageView.setImage(new Image(getItem().getImage()));
						imageView.setFitHeight(90);
						imageView.setFitWidth(75);
						setText(getItem().getName());
						setGraphic(imageView);
					}
				}
			});
		}));


	}

	/** renders current user library */
	public void initLibrary() throws SQLException {
		ResultSet resultSet = DbConnection.getUserLibrary();

		if (resultSet != null) {
			resultSet.last();    // moves cursor to the last row
			resultSize = resultSet.getRow(); // get row id
			resultSet.beforeFirst();
		}

		items = FXCollections.observableArrayList();
		while (resultSet.next()) {
			if (resultSet.getString("nameComic") != null) {
				Comic comic = new Comic();
				comic.setName(resultSet.getString("nameComic"));
				comic.setId(resultSet.getString("idComic") );
				comic.setState(resultSet.getString("comicState"));
				items.add(comic);
			}
		}

		myListOfComics.setItems(items);
		myListOfComics.setCellFactory(param -> new ListCell<Comic>() {
			@Override
			public void updateItem(Comic comic, boolean empty) {
				super.updateItem(comic, empty);
				if (empty) {
					setText(null);
					setGraphic(null);
				} else {
					for (int j=0;j<resultSize;j++) {
						try {
							resultSet.absolute(j+1);
							if (resultSet.getString("nameComic").equals(comic.getName())) {
								FXMLLoader loader = new FXMLLoader(ComicApplication.class.getResource("Views/LibraryLayout.fxml"));
								Node node = loader.load();
								Label comicName = (Label) loader.getNamespace().get("comicName");
								ImageView imageComic = (ImageView) loader.getNamespace().get("imageComic");
								Button removeButton = (Button) loader.getNamespace().get("removeButton");
								ChoiceBox comicState = (ChoiceBox) loader.getNamespace().get("comicState");
								ObservableList<String> items = FXCollections.observableArrayList("To read", "current", "finished");

								comicState.setItems(items);
								comicName.setText(resultSet.getString("nameComic"));
								comicState.setValue(resultSet.getString("comicState"));
								imageComic.setImage(new Image(resultSet.getString("imageComic")));
								imageComic.setFitHeight(100);
								imageComic.setFitWidth(75);
								comicState.setDisable(false);

								{
									removeButton.setOnAction(event -> {
										if (!isEmpty()) {
											Comic selectedcomic = getItem();
											String comicId = selectedcomic.getId();
											try {
												DbConnection.removeComicFromLibraryById(comicId);
												DbConnection.removeAuthorsAsPref(comicId);
												DbConnection.removeConceptsAsPref(comicId);
												myListOfComics.getItems().remove(selectedcomic);
											} catch (SQLException e) {
												AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Something went wrong.");
												System.out.println(e);
											}
										}
									});
									comicState.setOnAction(event -> {
										try {
											String selectedItem = (String) comicState.getSelectionModel().getSelectedItem();
											DbConnection.changeComicState(getItem().getId(), idUser,selectedItem);
											getItem().setState(selectedItem);
											comicState.setValue(selectedItem);
											comicState.getSelectionModel().select(selectedItem);
										} catch (SQLException ex) {
											AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Something went wrong.");
											System.out.println(ex);
										}
									});
								}

								setGraphic(node);
							}
						} catch (SQLException | IOException e) {
							throw new RuntimeException(e);
						}
					}
				}
			}
		});
	}

	/**  if comic is clicked then redirection to comicView  */
	@FXML
	void ComicClicked(MouseEvent event) throws IOException {
		Boolean empty = false;
		if(	listOfComics.getSelectionModel().getSelectedItem() != null){
			ObjectClicked objectClicked = ObjectClicked.getObjectClicked();
			objectClicked.setId(listOfComics.getSelectionModel().getSelectedItem().getId());
		}
		else if (myListOfComics.getSelectionModel().getSelectedItem() != null){
			ObjectClicked objectClicked = ObjectClicked.getObjectClicked();
			objectClicked.setId(myListOfComics.getSelectionModel().getSelectedItem().getId());
		}
		else empty = true;

		if(!empty){
			isInMainPage = false;
			Stage stage = (Stage) listOfComics.getScene().getWindow();
			stage.close();

			FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/ComicView.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			stage.setTitle("TSE ComicVine!");
			stage.setScene(scene);
			stage.show();
		}
	}

	/** shows the items (comics) by their state in the listView  */
	@FXML
	void stateSelected(ActionEvent event){
		window = stateList.getScene().getWindow();
		try{
			initLibrary();
			String selectedState = (String) stateList.getValue();
			if(selectedState.equals("all")){
				myListOfComics.setItems(items);
			}else{
				// filter the items in the listView based on the selected state
				myListOfComics.setItems(items.filtered(comic -> comic.getState().equals(selectedState)));
			}
		}catch (SQLException e){
			AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
					"Something went wrong.");
			System.out.println(e);
		}

	}
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			ObservableList<String> observableList = FXCollections.observableArrayList("To read", "current", "finished","all");
			stateList.setItems(observableList);

			initLibrary();
			initLatestComics();
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		}
	}


}
