package com.example.develop.controllers;

import com.example.develop.ComicApplication;
import com.example.develop.model.ObjectSearch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HeaderController implements Initializable {
    @FXML
    private ChoiceBox domainList;
    @FXML
    private Label errorResearch;
    @FXML
    private TextField searchInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> observableList2 = FXCollections.observableArrayList("Comics", "Characters", "Creators");
        domainList.setItems(observableList2);
    }

    @FXML
    void handleClickProfileImage(MouseEvent event) throws IOException {
        Stage stage = (Stage) domainList.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/ProfileMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("TSE ComicVine!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void recommendationHandler(MouseEvent event) throws IOException {
        Stage stage = (Stage) domainList.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/recommendationView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("TSE ComicVine!");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void searchButtonHandler(MouseEvent event) throws IOException {
        if(domainList.getValue() == null){
            errorResearch.setText("Please Enter a domain research !");
        }else{
            if(searchInput.getText().compareTo("")==0){
                errorResearch.setText("No input");
            }else{
                errorResearch.setText("");
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

                Stage stage = (Stage) domainList.getScene().getWindow();
                stage.close();

                FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/SearchComics.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("TSE ComicVine!");
                stage.setScene(scene);
                stage.show();

            }
        }
    }

    @FXML
    void returnHandler(MouseEvent event) throws IOException, SQLException {
        Stage stage = (Stage) searchInput.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/MainPage.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("TSE ComicVine!");
        stage.setScene(scene);
        stage.show();

    }

}
