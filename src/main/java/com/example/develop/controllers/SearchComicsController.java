package com.example.develop.controllers;

import com.example.develop.ComicApplication;
import com.example.develop.service.ComicVineService;
import com.example.develop.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;


public class SearchComicsController implements Initializable {

	private ObjectSearch objectSearch = ObjectSearch.getObjectSearch();

	@FXML
	private ListView<Research> listOfResearch = new ListView<Research>();
	@FXML
	private ChoiceBox domainList;
	@FXML
	private Label errorResearch;
	@FXML
	private TextField searchInput;


	@FXML
	void searchButtonHandler(MouseEvent event) throws IOException {
		if(domainList.getValue() == null){
			System.out.print("No Value");
			errorResearch.setText("Please Enter a domain research !");
		}else{
			if(searchInput.getText().compareTo("")==0){
				errorResearch.setText("No input");
			}else{
				errorResearch.setText("");
				Stage stage = (Stage) listOfResearch.getScene().getWindow();
				stage.close();
				String domain = "";
				if(domainList.getValue() == "Comics"){
					domain = "issues";
				} else if (domainList.getValue() == "Creators") {
					domain = "people";
				} else{
					domain = ((String)domainList.getValue()).toLowerCase();
				}
				ObjectSearch objectSearch = ObjectSearch.getObjectSearch();
				objectSearch.setDomain(domain);
				objectSearch.setSearch(searchInput.getText());

				FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/SearchComics.fxml"));
				Scene scene = new Scene(fxmlLoader.load());
				stage.setTitle("TSE ComicVine!");
				stage.setScene(scene);
				stage.show();

			}

		}
	}

	@FXML
	void returnHandler(MouseEvent event) throws IOException {
		Stage stage = (Stage) listOfResearch.getScene().getWindow();
		stage.close();

		FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/MainPage.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("TSE ComicVine!");
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void ComicClicked(MouseEvent event) throws IOException {
		Boolean empty = false;
		if(	listOfResearch.getSelectionModel().getSelectedItem() != null){
			ObjectClicked objectClicked = ObjectClicked.getObjectClicked();
			objectClicked.setId(listOfResearch.getSelectionModel().getSelectedItem().getId());
		}
		else empty = true;

		if(!empty){
			Stage stage = (Stage) listOfResearch.getScene().getWindow();
			stage.close();
			FXMLLoader fxmlLoader;
			System.out.println(objectSearch.getDomain());
			if (objectSearch.getDomain().equals("people")){
				fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/AuthorView.fxml"));
				System.out.println("creators");
			} else if (objectSearch.getDomain().equals("characters")) {
				fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/CharacterView.fxml"));
				System.out.println("characters");
			}else {
				fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/ComicView.fxml"));
				System.out.println("comics");
			}
			Scene scene = new Scene(fxmlLoader.load());
			stage.setTitle("TSE ComicVine!");
			stage.setScene(scene);
			stage.show();
		}
	}

	public CompletableFuture<JsonNode> getSearch() throws IOException {
		ComicVineService comicVineService = new ComicVineService();
		return comicVineService.search(objectSearch.getDomain(), objectSearch.getSearch(), 10, 0)
				.thenApply(result -> {
					for (int i = 0; i < result.size(); i++) {
						JsonNode element = result.get(i);
						if (element.get("name").isNull()) {
							((ArrayNode) result).remove(i);
							i--;
						}
					}
					return result;
				});
	}

	public void initSearch() throws IOException {

		CompletableFuture<JsonNode> future = getSearch();
		future.thenAccept(search -> Platform.runLater(()-> {

			// Do something with the latest comics
			ObservableList<Research> items = FXCollections.observableArrayList();
			for (JsonNode res : search) {
				if (res.get("name").isNull()) {
					Research output = new Research();
					output.setName(res.get("volume").get("name").textValue() + " #" + res.get("issue_number"));
					output.setId(String.valueOf(res.get("id")));
					output.setImage(res.get("image").get("icon_url").textValue());
					items.add(output);
				} else {
					Research output = new Research();
					output.setName(res.get("name").textValue());
					output.setId(String.valueOf(res.get("id")));
					output.setImage(res.get("image").get("icon_url").textValue());
					items.add(output);

				}
			}

			listOfResearch.setItems(items);
			listOfResearch.setCellFactory(param -> new ListCell<Research>() {
				private ImageView imageView = new ImageView();

				@Override
				public void updateItem(Research research, boolean empty) {
					super.updateItem(research, empty);
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

	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			ObservableList<String> observableList2 = FXCollections.observableArrayList("Comics", "Characters", "Creators");
			domainList.setItems(observableList2);
			initSearch();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@FXML
	void handleClickProfileImage(MouseEvent event) throws IOException {
		//TODO
		/*FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../../../resources/com/example/develop/ProfileMenu.fxml"));
		this.controllerProf = new ProfileMenuController();
		loader.setController(this.controllerProf);
		AnchorPane profileAnchorPane = loader.load();
		rootAnchorPane.getChildren().setAll(profileAnchorPane);*/
	}




}
