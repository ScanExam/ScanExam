package fr.istic.tools.scanexam.view.fx.editor;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.view.fx.component.RenameField;
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition;
import fr.istic.tools.scanexam.view.fx.editor.QuestionItemEdition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

/**
 * [JavaFX] Détail d'une question
 * @author Stefan Locke, Julien Cochet
 */
@SuppressWarnings("all")
public class QuestionOptionsEdition extends VBox {
  /**
   * VARIABLES
   */
  private GridPane grid;
  
  private RenameField name;
  
  /**
   * Modifiable label for the scale of the question
   */
  private RenameField scale;
  
  private final TextFormatter<Number> formatter;
  
  /**
   * Formatter to allow only number in scale field
   */
  private Label questionId;
  
  private Label page;
  
  private Label questionCoords;
  
  private Label questionDescription;
  
  private Button remove;
  
  private ControllerFxEdition controller;
  
  private QuestionItemEdition currentItem;
  
  /**
   * CONSTRUCTEURS
   */
  public QuestionOptionsEdition(final ControllerFxEdition controller) {
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
    String _translate_4 = LanguageManager.translate("question.position");
    Label l6 = new Label(_translate_4);
    String _translate_5 = LanguageManager.translate("question.description");
    Label l7 = new Label(_translate_5);
    this.grid.add(l1, 0, 0);
    this.grid.add(l2, 0, 1);
    this.grid.add(l3, 0, 2);
    this.grid.add(l4, 0, 3);
    this.grid.add(l6, 0, 4);
    this.grid.add(l7, 0, 5);
    RenameField _renameField = new RenameField();
    this.name = _renameField;
    RenameField _renameField_1 = new RenameField();
    this.scale = _renameField_1;
    NumberStringConverter _numberStringConverter = new NumberStringConverter();
    TextFormatter<Number> _textFormatter = new TextFormatter<Number>(_numberStringConverter);
    this.formatter = _textFormatter;
    this.scale.setFieldFormatter(this.formatter);
    Label _label = new Label();
    this.questionId = _label;
    Label _label_1 = new Label();
    this.page = _label_1;
    Label _label_2 = new Label();
    this.questionCoords = _label_2;
    Label _label_3 = new Label();
    this.questionDescription = _label_3;
    String _translate_6 = LanguageManager.translate("question.remove");
    Button _button = new Button(_translate_6);
    this.remove = _button;
    this.grid.add(this.name, 1, 0);
    this.grid.add(this.questionId, 1, 1);
    this.grid.add(this.page, 1, 2);
    this.grid.add(this.scale, 1, 3);
    this.grid.add(this.questionCoords, 1, 4);
    this.grid.add(this.questionDescription, 1, 5);
    this.getChildren().addAll(this.grid, this.remove);
    this.hideAll();
    this.setupEvents();
  }
  
  /**
   * Selects a question to display
   * @param item Question sélectionnée
   */
  public void select(final QuestionItemEdition item) {
    this.showAll();
    this.currentItem = item;
    this.name.setText(item.getName());
    int _page = item.getPage();
    String _plus = (Integer.valueOf(_page) + "");
    this.page.setText(_plus);
    int _questionId = item.getQuestionId();
    String _plus_1 = ("" + Integer.valueOf(_questionId));
    this.questionId.setText(_plus_1);
    float _scale = item.getScale();
    String _plus_2 = ("" + Float.valueOf(_scale));
    this.scale.setText(_plus_2);
    double _x = item.getZone().getX();
    String _plus_3 = ("X:" + Double.valueOf(_x));
    String _plus_4 = (_plus_3 + "\nY:");
    double _y = item.getZone().getY();
    String _plus_5 = (_plus_4 + Double.valueOf(_y));
    String _plus_6 = (_plus_5 + "\nH:");
    double _height = item.getZone().getHeight();
    String _plus_7 = (_plus_6 + Double.valueOf(_height));
    String _plus_8 = (_plus_7 + "\nW:");
    double _width = item.getZone().getWidth();
    String _plus_9 = (_plus_8 + Double.valueOf(_width));
    this.questionCoords.setText(_plus_9);
    this.questionDescription.setText("No description");
  }
  
  public void showAll() {
    this.setVisible(true);
  }
  
  public void hideAll() {
    this.setVisible(false);
  }
  
  /**
   * Called to commit a name change
   */
  public void commitRename() {
    this.currentItem.setName(this.name.getText());
    this.controller.getQuestionList().updateInModel(this.currentItem);
  }
  
  /**
   * Called to commit a scale change
   */
  public void commitRescale() {
    this.currentItem.setScale(this.scale.getFormatterValue());
    this.controller.getQuestionList().updateInModel(this.currentItem);
  }
  
  public void setupEvents() {
    this.remove.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(final ActionEvent event) {
        QuestionOptionsEdition.this.controller.getQuestionList().remove(QuestionOptionsEdition.this.currentItem);
        QuestionOptionsEdition.this.controller.getMainPane().removeZone(QuestionOptionsEdition.this.currentItem.getZone());
        QuestionOptionsEdition.this.controller.selectQuestion(null);
      }
    });
    final ChangeListener<String> _function = (ObservableValue<? extends String> obs, String oldVal, String newVal) -> {
      this.commitRename();
    };
    this.name.getTextProperty().addListener(_function);
    final ChangeListener<String> _function_1 = (ObservableValue<? extends String> obs, String oldVal, String newVal) -> {
      this.commitRescale();
    };
    this.scale.getTextProperty().addListener(_function_1);
  }
}
