package fr.tse.prinfo3.view;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class openMenuFXML extends Application {
    @Override
    public void start(Stage stage) throws Exception {
       
        Parent root = FXMLLoader.load(getClass().getResource("./MainScene.fxml"));
        
        Scene scene = new Scene(root, 1000, 700);
    
        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

