package fr.istic.tools.scanexam.view.fX.corrector;

import fr.istic.tools.scanexam.view.fX.corrector.ControllerFXCorrector;
import fr.istic.tools.scanexam.view.fX.corrector.QuestionItemCorrector;
import fr.istic.tools.scanexam.view.fX.corrector.StudentItemCorrector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

@SuppressWarnings("all")
public class Grader extends VBox {
  public static class GradeItem extends VBox {
    public GradeItem() {
      HBox topRow = new HBox();
      Label _label = new Label("This is a test Grate entry name, here you will be able to add a description of this entry, and eventually have some latec/html");
      this.text = _label;
      CheckBox _checkBox = new CheckBox();
      this.check = _checkBox;
      Label _label_1 = new Label("5.0");
      this.worth = _label_1;
      this.text.setWrapText(true);
      this.text.setMaxWidth(130);
      Insets _insets = new Insets(0, 0, 0, 10);
      this.worth.setPadding(_insets);
      Insets _insets_1 = new Insets(0, 0, 0, 10);
      VBox.setMargin(this.text, _insets_1);
      Insets _insets_2 = new Insets(0, 0, 10, 0);
      VBox.setMargin(this, _insets_2);
      topRow.getChildren().addAll(this.check, this.worth);
      this.getChildren().addAll(topRow, this.text);
    }
    
    private Label text;
    
    private Label worth;
    
    private CheckBox check;
  }
  
  public Grader(final ControllerFXCorrector controller) {
    this.controller = controller;
    ScrollPane scrollp = new ScrollPane();
    scrollp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollp.getStyleClass().add("GradeList");
    VBox _vBox = new VBox();
    this.itemContainer = _vBox;
    Button _button = new Button("Add new Grade Entry");
    this.add = _button;
    this.add.getStyleClass().add("InfinityButton");
    scrollp.setContent(this.itemContainer);
    this.getChildren().addAll(scrollp, this.add);
    this.setPrefWidth(170);
    this.setMaxHeight(500);
    this.setPrefHeight(Region.USE_COMPUTED_SIZE);
    this.getStyleClass().add("Grader");
    this.setupEvents();
  }
  
  private Button add;
  
  private VBox itemContainer;
  
  private ControllerFXCorrector controller;
  
  public void displayQuestion(final QuestionItemCorrector qItem, final StudentItemCorrector sItem) {
    this.clearDisplay();
  }
  
  public Object createNewGradeEntry() {
    Object _xblockexpression = null;
    {
      Grader.GradeItem entry = new Grader.GradeItem();
      this.itemContainer.getChildren().add(entry);
      _xblockexpression = this.addEntryToModel(entry);
    }
    return _xblockexpression;
  }
  
  public Object addEntryToModel(final Grader.GradeItem item) {
    return null;
  }
  
  public void clearDisplay() {
    this.itemContainer.getChildren().clear();
  }
  
  public void setupEvents() {
    this.add.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(final ActionEvent event) {
        Grader.this.createNewGradeEntry();
      }
    });
  }
}
