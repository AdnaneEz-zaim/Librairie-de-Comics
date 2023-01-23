package com.example.develop.controllers;

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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class AuthorController  implements Initializable {
    @FXML
    private Label descAuthor;
    @FXML
    private ListView<Comic> listOfIssue = new ListView<>();
    @FXML
    private ListView<Character> listOfCharacters = new ListView<>();
    @FXML
    private Text nameAuthor;
    @FXML
    private ImageView imgAuthor;
    @FXML
    private Text dateOfBirth;
    @FXML
    private Text country;
    private CompletableFuture<JsonNode> future = null;
    private final String authorId = ObjectClicked.getObjectClicked().getId();
    private ComicVineService comicVineService = ComicVineService.getComicVineService();

    /** send Author info to view : image, name , date of birth and desc  */
    public void initAuthor() throws IOException {
        future = comicVineService.searchAuthor(authorId);
        future.thenAccept(author -> Platform.runLater(()->{
            imgAuthor.setImage(new Image(author.get("image").get("thumb_url").textValue()));
            nameAuthor.setText(author.get("name").textValue());
            dateOfBirth.setText("Date of Birth : "+author.get("birth").textValue());
            country.setText("country : "+author.get("country").textValue());
            if(author.get("description").textValue() == null)
                descAuthor.setText("No Description found");
            else
                descAuthor.setText(author.get("description").textValue().replaceAll("<[^>]*>", ""));
        }));

    }

    /** send Author info to view : listView of issues created by the author  */
    public void initIssues(){
        future.thenAccept(author -> Platform.runLater(()-> {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode issues = null;
            try {
                issues = objectMapper.readTree(author.get("issues").toString());
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
            listOfIssue.setItems(items);
            listOfIssue.setCellFactory(param -> new ListCell<Comic>() {
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

    /** send Author info to view : listView of characters created by the author  */
    public void initCharacters(){
        future.thenAccept(author -> Platform.runLater(()-> {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode characters = null;
            try {
                characters = objectMapper.readTree(author.get("created_characters").toString());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            ObservableList<Character> items = FXCollections.observableArrayList();
            for (JsonNode item : characters) {
                if (item.get("name").textValue() != null) {
                    Character character = new Character();
                    character.setId(String.valueOf(item.get("id")));
                    character.setName(item.get("name").textValue());
                    items.add(character);
                }
            }
            listOfCharacters.setItems(items);
            listOfCharacters.setCellFactory(param -> new ListCell<Character>() {
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

    /** handle a click on a character : redirection to CharacterView  */
    @FXML
    void CharacterClicked(MouseEvent event) throws IOException {
        Boolean empty = false;
        if(	listOfCharacters.getSelectionModel().getSelectedItem() != null){
            ObjectClicked objectClicked = ObjectClicked.getObjectClicked();
            System.out.println(listOfCharacters.getSelectionModel().getSelectedItem().getId());
            objectClicked.setId(listOfCharacters.getSelectionModel().getSelectedItem().getId());
        }
        else empty = true;

        if(!empty){
            Stage stage = (Stage) listOfCharacters.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/CharacterView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("TSE ComicVine!");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** handle a click on an issue : redirection to ComicView  */
    @FXML
    void issueClicked(MouseEvent event) throws IOException {
        Boolean empty = false;
        if(	listOfIssue.getSelectionModel().getSelectedItem() != null){
            ObjectClicked objectClicked = ObjectClicked.getObjectClicked();
            objectClicked.setId(listOfIssue.getSelectionModel().getSelectedItem().getId());
        }
        else empty = true;

        if(!empty){
            Stage stage = (Stage) listOfIssue.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/ComicView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("TSE ComicVine!");
            stage.setScene(scene);
            stage.show();
        }
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initAuthor();
            initIssues();
            initCharacters();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
