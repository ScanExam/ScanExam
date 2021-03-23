package fr.istic.tools.scanexam.view.fX.editor;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.view.fX.FXSettings;
import fr.istic.tools.scanexam.view.fX.editor.Box;
import fr.istic.tools.scanexam.view.fX.editor.BoxType;
import fr.istic.tools.scanexam.view.fX.editor.QuestionListEditor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.converter.NumberStringConverter;

/**
 * [JavaFX] Item question dans la liste de gauche des questions
 * @author Stefan Locke, Julien Cochet
 */
@SuppressWarnings("all")
public class QuestionItemEditor extends VBox {
  /**
   * the top container of the questionItem
   */
  private HBox top;
  
  /**
   * the middle container of the questionItem
   */
  private HBox middle;
  
  /**
   * the bottom container of the questionItem
   */
  private HBox bottom;
  
  /**
   * button remote
   */
  private Button remove;
  
  /**
   * Text field for the scale of the question
   */
  private TextField scaleField;
  
  /**
   * Formatter to allow only number in scale field
   */
  private final TextFormatter<Number> formatter;
  
  /**
   * Zone of the question, a rectangle
   */
  private Box zone;
  
  /**
   * textfield used to rename the question
   */
  private Label name;
  
  /**
   * Id of this question
   */
  private int questionId;
  
  /**
   * page location of this question
   */
  private int page;
  
  /**
   * Scale of the question
   */
  private int scale;
  
  /**
   * the type of this zone
   */
  private BoxType type;
  
  /**
   * the container of all the question items
   */
  private QuestionListEditor list;
  
  /**
   * CONSTRUCTEURS
   */
  public QuestionItemEditor(final QuestionListEditor list, final Box zone) {
    super();
    HBox _hBox = new HBox();
    this.top = _hBox;
    HBox _hBox_1 = new HBox();
    this.middle = _hBox_1;
    ObservableList<Node> _children = this.middle.getChildren();
    String _translate = LanguageManager.translate("question.scale");
    Label _label = new Label(_translate);
    _children.add(_label);
    TextField _textField = new TextField();
    this.scaleField = _textField;
    NumberStringConverter _numberStringConverter = new NumberStringConverter();
    TextFormatter<Number> _textFormatter = new TextFormatter<Number>(_numberStringConverter);
    this.formatter = _textFormatter;
    this.scaleField.setTextFormatter(this.formatter);
    this.middle.getChildren().add(this.scaleField);
    HBox _hBox_2 = new HBox();
    this.bottom = _hBox_2;
    String _translate_1 = LanguageManager.translate("question.remove");
    Button _button = new Button(_translate_1);
    this.remove = _button;
    this.bottom.getChildren().add(this.remove);
    this.list = list;
    this.zone = zone;
    this.zone.setQuestionItem(this);
    Label _label_1 = new Label("New Question");
    this.name = _label_1;
    this.getChildren().addAll(this.top, this.middle, this.bottom);
    this.top.getChildren().addAll(this.name);
    VBox.setVgrow(this, Priority.ALWAYS);
    zone.setupEvents();
    this.getStyleClass().add("QuestionItem");
    Insets _insets = new Insets(2);
    VBox.setMargin(this, _insets);
    this.setupContextMenu();
    this.setupEvents(this);
  }
  
  public QuestionItemEditor(final QuestionListEditor list, final Box zone, final BoxType type, final int page) {
    this(list, zone);
    this.type = type;
    this.page = page;
  }
  
  public QuestionItemEditor(final QuestionListEditor list, final Box zone, final String name, final int page, final int id) {
    this(list, zone);
    this.name.setText(name);
    this.page = page;
    this.questionId = id;
  }
  
  /**
   * def setNameEditable(){
   * 	name.editable = true
   * 	name.selectAll
   * }
   * 
   * def commitNameChange(){
   * 	name.editable = false
   * 	list.updateInModel(this)
   * }
   */
  public Object setupContextMenu() {
    return null;
  }
  
  public void setupEvents(final QuestionItemEditor item) {
    this.remove.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(final ActionEvent event) {
        QuestionItemEditor.this.list.removeQuestion(item);
        QuestionItemEditor.this.list.getController().selectQuestion(null);
      }
    });
    final ChangeListener<Number> _function = (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
      this.scale = newValue.intValue();
    };
    this.formatter.valueProperty().addListener(_function);
    this.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(final MouseEvent event) {
        QuestionItemEditor.this.list.getController().selectQuestion(item);
      }
    });
  }
  
  /**
   * GETTERS
   */
  public int getWeight() {
    return 1;
  }
  
  public int getScale() {
    return this.scale;
  }
  
  public Box getZone() {
    return this.zone;
  }
  
  public int getQuestionId() {
    return this.questionId;
  }
  
  public int getPage() {
    return this.page;
  }
  
  public String getName() {
    return this.name.getText();
  }
  
  /**
   * SETTERS
   */
  public int setQuestionId(final int id) {
    return this.questionId = id;
  }
  
  public int setPage(final int page) {
    return this.page = page;
  }
  
  /**
   * Sets the color of the zone and the item in the list
   * @param b True for highlight color
   */
  public void setFocus(final boolean b) {
    if (b) {
      this.setColor(FXSettings.ITEM_HIGHLIGHT_COLOR);
      this.zone.setFocus(b);
    } else {
      this.setColor(FXSettings.ITEM_NORMAL_COLOR);
      this.zone.setFocus(b);
    }
  }
  
  public void setColor(final Color color) {
    BackgroundFill bf = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
    Background _background = new Background(bf);
    this.setBackground(_background);
  }
  
  public void setName(final String text) {
    this.name.setText(text);
  }
}
