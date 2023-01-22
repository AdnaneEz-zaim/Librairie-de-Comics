package com.example.develop.controllers;

import com.example.develop.ComicApplication;
import com.example.develop.model.Comic;
import com.example.develop.model.ObjectClicked;
import com.example.develop.service.ComicVineService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RecommendationController implements Initializable {

    @FXML
    private ListView<Comic> listOfRecommendations = new ListView<>();

    public void initPref() throws SQLException, IOException, ExecutionException, InterruptedException {
        ComicVineService comicVineService = new ComicVineService();
        ArrayList<String> prefAuthors =  DbConnection.getPrefAuthors();
        ArrayList<String> prefConcepts = DbConnection.getPrefConcepts();

        ObservableList<Comic> items = FXCollections.observableArrayList();

        for(String author:prefAuthors){
            CompletableFuture<JsonNode> future = comicVineService.GetComicByAuthor(author,2);
            JsonNode prefComics = future.get();
            initItems(items, prefComics);

        }

        for(String concept:prefConcepts){
            CompletableFuture<JsonNode> future = comicVineService.GetComicByConcept(concept,2);
            JsonNode prefComics = future.get();
            initItems(items, prefComics);
        }

        listOfRecommendations.setItems(items);
        listOfRecommendations.setCellFactory(param -> new ListCell<Comic>() {
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
    }

    private void initItems(ObservableList<Comic> items, JsonNode prefComics) {
        for (JsonNode res : prefComics) {
            if(res.get("name").isNull()){
                Comic comic = new Comic();
                comic.setName(res.get("volume").get("name").textValue()+" #"+res.get("issue_number"));
                comic.setId(String.valueOf(res.get("id")));
                comic.setImage(res.get("image").get("icon_url").textValue());
                items.add(comic);
            }
            else{
                Comic comic = new Comic();
                comic.setName(res.get("name").textValue());
                comic.setId(String.valueOf(res.get("id")));
                comic.setImage(res.get("image").get("icon_url").textValue());
                items.add(comic);
            }
        }
    }

    @FXML
    void ComicClicked(MouseEvent event) throws IOException {
        Boolean empty = false;
        if(	listOfRecommendations.getSelectionModel().getSelectedItem() != null){
            ObjectClicked objectClicked = ObjectClicked.getObjectClicked();
            objectClicked.setId(listOfRecommendations.getSelectionModel().getSelectedItem().getId());
        }
        else empty = true;

        if(!empty){
            Stage stage = (Stage) listOfRecommendations.getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/ComicView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("TSE ComicVine!");
            stage.setScene(scene);
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initPref();
        } catch (IOException | SQLException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
