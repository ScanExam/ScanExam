package fr.istic.tools.scanexam.export;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@SuppressWarnings("all")
public class TestAreaDemo extends Application {
  public void start(final Stage primaryStage) throws Exception {
    VBox root = FXMLLoader.<VBox>load(this.getClass().getResource("./resources/viewResources/text.FXML"));
    Insets _insets = new Insets(10);
    root.setPadding(_insets);
    root.setSpacing(5);
    ObservableList<Node> _children = root.getChildren();
    Label _label = new Label("Enter message:");
    _children.add(_label);
    TextArea textArea = new TextArea();
    root.getChildren().add(textArea);
    Scene scene = new Scene(root, 320, 150);
    primaryStage.setTitle("JavaFX TextArea (Sacré Qian)");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
  
  public static void main(final String[] args) {
    Application.launch(args);
  }
}
