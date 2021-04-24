package fr.istic.tools.scanexam.view.fx.editor;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.view.fx.component.RenameField;
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition;
import fr.istic.tools.scanexam.view.fx.editor.QuestionItemEdition;
import java.util.function.UnaryOperator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

/**
 * [JavaFX] Détail d'une question
 * @author Stefan Locke, Julien Cochet
 */
@SuppressWarnings("all")
public class QuestionOptionsEdition extends VBox {
  /**
   * Controlleur de l'édition
   */
  private ControllerFxEdition controller;
  
  /**
   * Item de la question
   */
  private QuestionItemEdition currentItem;
  
  /**
   * Grille contenant toutes les éléments
   */
  private GridPane grid;
  
  /**
   * Champ du nom de la question
   */
  private RenameField name;
  
  /**
   * Label contenant l'identifiant de la question
   */
  private Label questionId;
  
  /**
   * Label contenant le barème de la question
   */
  private RenameField scale;
  
  /**
   * Label contenant la apge de la question
   */
  private Label page;
  
  /**
   * Fommateur pour n'autorisé que des chiffres dans un champ de texte
   */
  private final TextFormatter<Number> formatter;
  
  /**
   * Bouton de suppression de la question
   */
  private Button remove;
  
  /**
   * CONSTRUCTEUR
   */
  public QuestionOptionsEdition(final ControllerFxEdition controller) {
    this.controller = controller;
    GridPane _gridPane = new GridPane();
    this.grid = _gridPane;
    String _translate = LanguageManager.translate("question.name");
    Label l1 = new Label(_translate);
    String _translate_1 = LanguageManager.translate("question.id");
    Label l2 = new Label(_translate_1);
    String _translate_2 = LanguageManager.translate("question.scale");
    Label l3 = new Label(_translate_2);
    String _translate_3 = LanguageManager.translate("question.page");
    Label l4 = new Label(_translate_3);
    this.grid.add(l1, 0, 0);
    this.grid.add(l2, 0, 1);
    this.grid.add(l3, 0, 2);
    this.grid.add(l4, 0, 3);
    RenameField _renameField = new RenameField();
    this.name = _renameField;
    Label _label = new Label();
    this.questionId = _label;
    RenameField _renameField_1 = new RenameField();
    this.scale = _renameField_1;
    final UnaryOperator<TextFormatter.Change> _function = (TextFormatter.Change change) -> {
      String newText = change.getControlNewText();
      boolean _matches = newText.matches("([0-9]*([.][0-9]*)?)?");
      if (_matches) {
        return change;
      }
      return null;
    };
    UnaryOperator<TextFormatter.Change> integerFilter = _function;
    NumberStringConverter _numberStringConverter = new NumberStringConverter();
    TextFormatter<Number> _textFormatter = new TextFormatter<Number>(_numberStringConverter, Integer.valueOf(0), integerFilter);
    this.formatter = _textFormatter;
    this.scale.setFieldFormatter(this.formatter);
    Label _label_1 = new Label();
    this.page = _label_1;
    String _translate_4 = LanguageManager.translate("question.remove");
    Button _button = new Button(_translate_4);
    this.remove = _button;
    this.grid.add(this.name, 1, 0);
    this.grid.add(this.questionId, 1, 1);
    this.grid.add(this.scale, 1, 2);
    this.grid.add(this.page, 1, 3);
    final HBox hbox = new HBox(this.remove);
    hbox.setAlignment(Pos.CENTER);
    this.getChildren().addAll(this.grid, hbox);
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
    int _questionId = item.getQuestionId();
    String _plus = ("" + Integer.valueOf(_questionId));
    this.questionId.setText(_plus);
    float _scale = item.getScale();
    String _plus_1 = ("" + Float.valueOf(_scale));
    this.scale.setText(_plus_1);
    int _page = item.getPage();
    String _plus_2 = (Integer.valueOf(_page) + "");
    this.page.setText(_plus_2);
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
