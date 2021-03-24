package fr.istic.tools.scanexam.view.fX.editor;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.view.fX.editor.ControllerFXEditor;
import fr.istic.tools.scanexam.view.fX.editor.QuestionItemEditor;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;
import org.eclipse.xtext.xbase.lib.InputOutput;

/**
 * [JavaFX] Détail d'une question
 * @author Stefan Locke, Julien Cochet
 */
@SuppressWarnings("all")
public class QuestionOptionsEditor extends VBox {
  /**
   * VARIABLES
   */
  private GridPane grid;
  
  private Label questionName;
  
  private TextField renameField;
  
  /**
   * Text field for the scale of the question
   */
  private TextField scaleField;
  
  /**
   * Formatter to allow only number in scale field
   */
  private final TextFormatter<Number> formatter;
  
  private Label questionId;
  
  private Label page;
  
  private Label questionScale;
  
  private Label questionWeight;
  
  private Label questionCoords;
  
  private Label questionDescription;
  
  private Button remove;
  
  private ControllerFXEditor controller;
  
  private QuestionItemEditor currentItem;
  
  /**
   * CONSTRUCTEURS
   */
  public QuestionOptionsEditor(final ControllerFXEditor controller) {
    this.controller = controller;
    GridPane _gridPane = new GridPane();
    this.grid = _gridPane;
    String _translate = LanguageManager.translate("question.name");
    Label l1 = new Label(_translate);
    String _translate_1 = LanguageManager.translate("question.id");
    Label l2 = new Label(_translate_1);
    String _translate_2 = LanguageManager.translate("question.page");
    Label l3 = new Label(_translate_2);
    String _translate_3 = LanguageManager.translate("question.scale");
    Label l4 = new Label(_translate_3);
    String _translate_4 = LanguageManager.translate("question.coefficient");
    Label l5 = new Label(_translate_4);
    String _translate_5 = LanguageManager.translate("question.position");
    Label l6 = new Label(_translate_5);
    String _translate_6 = LanguageManager.translate("question.description");
    Label l7 = new Label(_translate_6);
    this.grid.add(l1, 0, 0);
    this.grid.add(l2, 0, 1);
    this.grid.add(l3, 0, 2);
    this.grid.add(l4, 0, 3);
    this.grid.add(l5, 0, 4);
    this.grid.add(l6, 0, 5);
    this.grid.add(l7, 0, 6);
    TextField _textField = new TextField();
    this.renameField = _textField;
    TextField _textField_1 = new TextField();
    this.scaleField = _textField_1;
    NumberStringConverter _numberStringConverter = new NumberStringConverter();
    TextFormatter<Number> _textFormatter = new TextFormatter<Number>(_numberStringConverter);
    this.formatter = _textFormatter;
    this.scaleField.setTextFormatter(this.formatter);
    Label _label = new Label();
    this.questionName = _label;
    Label _label_1 = new Label();
    this.questionId = _label_1;
    Label _label_2 = new Label();
    this.page = _label_2;
    Label _label_3 = new Label();
    this.questionScale = _label_3;
    Label _label_4 = new Label();
    this.questionWeight = _label_4;
    Label _label_5 = new Label();
    this.questionCoords = _label_5;
    Label _label_6 = new Label();
    this.questionDescription = _label_6;
    String _translate_7 = LanguageManager.translate("question.remove");
    Button _button = new Button(_translate_7);
    this.remove = _button;
    this.grid.add(this.questionName, 1, 0);
    this.grid.add(this.questionId, 1, 1);
    this.grid.add(this.page, 1, 2);
    this.grid.add(this.questionScale, 1, 3);
    this.grid.add(this.questionWeight, 1, 4);
    this.grid.add(this.questionCoords, 1, 5);
    this.grid.add(this.questionDescription, 1, 6);
    this.getChildren().addAll(this.grid, this.remove);
    this.hideAll();
    this.setupEvents();
  }
  
  /**
   * Selects a question to display
   * @param item Question sélectionnée
   */
  public void select(final QuestionItemEditor item) {
    this.showAll();
    this.currentItem = item;
    this.questionName.setText(item.getName());
    this.renameField.setText(item.getName());
    int _page = item.getPage();
    String _plus = (Integer.valueOf(_page) + "");
    this.page.setText(_plus);
    int _questionId = item.getQuestionId();
    String _plus_1 = ("" + Integer.valueOf(_questionId));
    this.questionId.setText(_plus_1);
    float _scale = item.getScale();
    String _plus_2 = ("" + Float.valueOf(_scale));
    this.questionScale.setText(_plus_2);
    float _scale_1 = item.getScale();
    String _plus_3 = ("" + Float.valueOf(_scale_1));
    this.scaleField.setText(_plus_3);
    int _weight = item.getWeight();
    String _plus_4 = ("" + Integer.valueOf(_weight));
    this.questionWeight.setText(_plus_4);
    double _x = item.getZone().getX();
    String _plus_5 = ("X:" + Double.valueOf(_x));
    String _plus_6 = (_plus_5 + "\nY:");
    double _y = item.getZone().getY();
    String _plus_7 = (_plus_6 + Double.valueOf(_y));
    String _plus_8 = (_plus_7 + "\nH:");
    double _height = item.getZone().getHeight();
    String _plus_9 = (_plus_8 + Double.valueOf(_height));
    String _plus_10 = (_plus_9 + "\nW:");
    double _width = item.getZone().getWidth();
    String _plus_11 = (_plus_10 + Double.valueOf(_width));
    this.questionCoords.setText(_plus_11);
    this.questionDescription.setText("No description");
  }
  
  public void showAll() {
    this.setVisible(true);
  }
  
  public void hideAll() {
    this.setVisible(false);
  }
  
  /**
   * Toggles if the textfield is the visible element
   */
  public void toggleRename(final boolean b) {
    if (b) {
      this.grid.getChildren().remove(this.questionName);
      this.grid.add(this.renameField, 1, 0);
      this.renameField.requestFocus();
      this.renameField.selectAll();
    } else {
      boolean _contains = this.grid.getChildren().contains(this.questionName);
      boolean _not = (!_contains);
      if (_not) {
        this.grid.getChildren().remove(this.renameField);
        this.grid.add(this.questionName, 1, 0);
      }
    }
  }
  
  /**
   * Toggles if the sclaefield is the visible element
   */
  public void toggleRescale(final boolean b) {
    if (b) {
      this.grid.getChildren().remove(this.questionScale);
      this.grid.add(this.scaleField, 1, 3);
      this.scaleField.requestFocus();
      this.scaleField.selectAll();
    } else {
      boolean _contains = this.grid.getChildren().contains(this.questionScale);
      boolean _not = (!_contains);
      if (_not) {
        this.grid.getChildren().remove(this.scaleField);
        this.grid.add(this.questionScale, 1, 3);
      }
    }
  }
  
  /**
   * Called to commit a name change
   */
  public void commitRename() {
    this.questionName.setText(this.renameField.getText());
    this.currentItem.setName(this.renameField.getText());
    this.controller.getQuestionList().updateInModel(this.currentItem);
  }
  
  /**
   * Called to commit a scale change
   */
  public void commitRescale() {
    this.questionScale.setText(this.formatter.getValue().toString());
    this.currentItem.setScale(this.formatter.getValue().floatValue());
    this.controller.getQuestionList().updateInModel(this.currentItem);
  }
  
  public void setupEvents() {
    this.remove.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(final ActionEvent event) {
        QuestionOptionsEditor.this.controller.getQuestionList().remove(QuestionOptionsEditor.this.currentItem);
        QuestionOptionsEditor.this.controller.selectQuestion(null);
      }
    });
    this.questionName.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(final MouseEvent event) {
        QuestionOptionsEditor.this.toggleRename(true);
      }
    });
    this.questionScale.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(final MouseEvent event) {
        QuestionOptionsEditor.this.toggleRescale(true);
      }
    });
    this.renameField.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(final ActionEvent event) {
        InputOutput.<String>print("rename actions\n");
        QuestionOptionsEditor.this.commitRename();
        QuestionOptionsEditor.this.toggleRename(false);
      }
    });
    ReadOnlyBooleanProperty _focusedProperty = this.renameField.focusedProperty();
    _focusedProperty.addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(final ObservableValue<? extends Boolean> observable, final Boolean oldValue, final Boolean newValue) {
        if ((!(newValue).booleanValue())) {
          InputOutput.<String>print("rename actions\n");
          QuestionOptionsEditor.this.commitRename();
          QuestionOptionsEditor.this.toggleRename(false);
        }
      }
    });
    this.scaleField.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(final ActionEvent event) {
        InputOutput.<String>print("rescale actions\n");
        QuestionOptionsEditor.this.commitRescale();
        QuestionOptionsEditor.this.toggleRescale(false);
      }
    });
    ReadOnlyBooleanProperty _focusedProperty_1 = this.scaleField.focusedProperty();
    _focusedProperty_1.addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(final ObservableValue<? extends Boolean> observable, final Boolean oldValue, final Boolean newValue) {
        if ((!(newValue).booleanValue())) {
          InputOutput.<String>print("rescale actions\n");
          QuestionOptionsEditor.this.commitRescale();
          QuestionOptionsEditor.this.toggleRescale(false);
        }
      }
    });
  }
}
