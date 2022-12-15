package fr.tse.prinfo3.view;


import fr.tse.prinfo3.control.CharacterController;
import fr.tse.prinfo3.control.MainPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class openMenuFXML extends Application {
	
	
	//JavaFX window controller (MVC principle)
    protected MainPageController controller = null;
	//protected CharacterController controller = null;
    
    @Override
    public void start(Stage stage) throws Exception {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
    	//FXMLLoader loader = new FXMLLoader(getClass().getResource("Character.fxml"));
        
        this.controller = new MainPageController();
       //this.controller = new CharacterController();
        
        loader.setController(this.controller);
        
        Parent root = loader.load();
        
        
        
        Scene scene = new Scene(root);
        
        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

