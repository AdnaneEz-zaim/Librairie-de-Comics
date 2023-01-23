package com.example.develop.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import com.example.develop.ComicApplication;
import com.example.develop.service.ComicVineService;
import com.example.develop.model.Character;
import com.example.develop.model.Comic;
import com.example.develop.model.ObjectClicked;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CharacterController implements Initializable {
    @FXML
    private Label descCharacter;
    @FXML
    private Text nameCharacter;
    @FXML
    private Text date_added;
    @FXML
    private Text countOfAppearances;
    @FXML
    private ImageView imgCharacter;
    private CompletableFuture<JsonNode> future ;
    private final String characterId = ObjectClicked.getObjectClicked().getId();
    private ComicVineService comicVineService = ComicVineService.getComicVineService();

    @FXML
    private ListView<Comic> listOfIssues = new ListView<>();
    @FXML
    private ListView<Character> listOfCFriends = new ListView<>();
    @FXML
    private ListView<Character> listOfCEnemies = new ListView<>();


    public void initCharacter() throws IOException {
        future = comicVineService.searchCharacter(characterId);
        future.thenAccept(character -> Platform.runLater(()-> {

            imgCharacter.setImage(new Image(character.get("image").get("thumb_url").textValue()));
            nameCharacter.setText(character.get("name").textValue());
            date_added.setText("date added : " + character.get("date_added").textValue());
            countOfAppearances.setText("count of appearances : " + character.get("count_of_issue_appearances").textValue());

            if (character.get("description").textValue() == null)
                descCharacter.setText("No Description found");
            else
                descCharacter.setText(character.get("description").textValue().replaceAll("<[^>]*>", ""));
        }));
    }
    public void initIssues(){
        future.thenAccept(character -> Platform.runLater(()-> {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode issues = null;
            try {
                issues = objectMapper.readTree(character.get("issue_credits").toString());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            ObservableList<Comic> items = FXCollections.observableArrayList();
            for (JsonNode item : issues) {
                if (item.get("name").textValue() != null) {
                    Comic comic = new Comic();
                    comic.setId(String.valueOf(item.get("id")));
                    comic.setName(item.get("name").textValue());
                    items.add(comic);
                }
            }
            listOfIssues.setItems(items);
            listOfIssues.setCellFactory(param -> new ListCell<Comic>() {
                @Override
                public void updateItem(Comic comic, boolean empty) {
                    super.updateItem(comic, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(getItem().getName());
                    }
                }
            });
        }));
    }
    public void initCEnemies_Friends(ListView l,String field){
        future.thenAccept(character -> Platform.runLater(()-> {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode issues = null;
            try {
                issues = objectMapper.readTree(character.get(field).toString());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            ObservableList<Character> items = FXCollections.observableArrayList();
            for (JsonNode item : issues) {
                if (item.get("name").textValue() != null) {
                    Character c = new Character();
                    c.setId(String.valueOf(item.get("id")));
                    c.setName(item.get("name").textValue());
                    items.add(c);
                }
            }
            l.setItems(items);
            l.setCellFactory(param -> new ListCell<Character>() {
                @Override
                public void updateItem(Character character, boolean empty) {
                    super.updateItem(character, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(getItem().getName());
                    }
                }
            });
        }));
    }


    @FXML
    void issueClicked(MouseEvent event) throws IOException {
        Boolean empty = false;
        if(	listOfIssues.getSelectionModel().getSelectedItem() != null){
            ObjectClicked objectClicked = ObjectClicked.getObjectClicked();
            objectClicked.setId(listOfIssues.getSelectionModel().getSelectedItem().getId());
        }
        else empty = true;

        if(!empty){
            Stage stage = (Stage) listOfIssues.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/ComicView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("TSE ComicVine!");
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    void characterClicked(MouseEvent event) throws IOException {
        Boolean empty = false;
        if(	listOfCEnemies.getSelectionModel().getSelectedItem() != null){
            ObjectClicked objectClicked = ObjectClicked.getObjectClicked();
            objectClicked.setId(listOfCEnemies.getSelectionModel().getSelectedItem().getId());
        } else if (listOfCFriends.getSelectionModel().getSelectedItem() != null) {
            ObjectClicked objectClicked = ObjectClicked.getObjectClicked();
            objectClicked.setId(listOfCFriends.getSelectionModel().getSelectedItem().getId());
        } else empty = true;

        if(!empty){
            Stage stage = (Stage) listOfCFriends.getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/CharacterView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("TSE ComicVine!");
            stage.setScene(scene);
            stage.show();
        }
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(characterId);

        try {
            initCharacter();
            initIssues();
            initCEnemies_Friends(listOfCEnemies,"character_enemies");
            initCEnemies_Friends(listOfCFriends,"character_friends");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        
    
		


}
