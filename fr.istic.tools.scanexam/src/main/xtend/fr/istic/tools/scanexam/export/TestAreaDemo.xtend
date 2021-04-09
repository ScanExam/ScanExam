package fr.istic.tools.scanexam.export

import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.layout.VBox
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.Scene
import javafx.fxml.FXMLLoader

class TestAreaDemo extends Application {
	
	override start(Stage primaryStage) throws Exception {
		
		
		
  		var VBox root = FXMLLoader.load(getClass().getResource("./resources/viewResources/text.FXML"));
        root.setPadding(new Insets(10));
        root.setSpacing(5);
        
        
        
        root.getChildren().add(new Label("Enter message:"));
        
 
        var TextArea textArea = new TextArea();
        root.getChildren().add(textArea);
 
       	var Scene scene = new Scene(root, 320, 150);
 
        primaryStage.setTitle("JavaFX TextArea (Sacré Qian)");
        primaryStage.setScene(scene);
        primaryStage.show();	
       }
       
       def static void main(String[] args){
       		launch(args)
       }
	
}