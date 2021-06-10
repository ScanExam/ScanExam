package fr.istic.tools.scanexam.view.fx.editor;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.view.fx.FxSettings;
import fr.istic.tools.scanexam.view.fx.editor.Box;
import fr.istic.tools.scanexam.view.fx.editor.BoxType;
import fr.istic.tools.scanexam.view.fx.editor.QuestionListEdition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * [JavaFX] Item question dans la liste de gauche des questions
 * 
 * This component is used as an item for the QuestionListEdition class.
 * We store the name, id , worth, and page position of the question, as well as a  referece to the box on the pdf
 * The main interaction with this class is changing its details such as worth and name ect,
 * setFocus is used when this item is clicked in the list .
 * 
 * Fixes to do : completely link to model, only store names for displaying the list and id, and use those to find the items in the model when we need box dimentions or worth.
 * 
 * 
 * @author Stefan Locke, Julien Cochet
 */
@SuppressWarnings("all")
public class QuestionItemEdition extends VBox {
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
  private float scale;
  
  /**
   * the type of this zone
   */
  private BoxType type;
  
  /**
   * the container of all the question items
   */
  private QuestionListEdition list;
  
  /**
   * CONSTRUCTEURS
   */
  public QuestionItemEdition(final QuestionListEdition list, final Box zone) {
    super();
    HBox _hBox = new HBox();
    this.top = _hBox;
    HBox _hBox_1 = new HBox();
    this.middle = _hBox_1;
    HBox _hBox_2 = new HBox();
    this.bottom = _hBox_2;
    String _translate = LanguageManager.translate("question.remove");
    Button _button = new Button(_translate);
    this.remove = _button;
    this.bottom.getChildren().add(this.remove);
    this.list = list;
    this.zone = zone;
    this.zone.setQuestionItem(this);
    Label _label = new Label("New Question");
    this.name = _label;
    this.getChildren().addAll(this.top, this.middle, this.bottom);
    this.top.getChildren().addAll(this.name);
    VBox.setVgrow(this, Priority.ALWAYS);
    zone.setupEvents();
    this.scale = 1f;
    this.getStyleClass().add("ListItem");
    Insets _insets = new Insets(2);
    VBox.setMargin(this, _insets);
    this.setupContextMenu();
    this.setupEvents(this);
  }
  
  public QuestionItemEdition(final QuestionListEdition list, final Box zone, final BoxType type, final int page) {
    this(list, zone);
    this.type = type;
    this.page = page;
  }
  
  public QuestionItemEdition(final QuestionListEdition list, final Box zone, final String name, final int page, final int id) {
    this(list, zone);
    this.name.setText(name);
    this.page = page;
    this.questionId = id;
  }
  
  /**
   * Used to stetup the context menu (right click), called on the setupevent Method
   */
  public Object setupContextMenu() {
    return null;
  }
  
  /**
   * Used to setup events for this item, called at the end of the constructor.
   */
  public void setupEvents(final QuestionItemEdition item) {
    this.remove.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(final ActionEvent event) {
        QuestionItemEdition.this.list.removeQuestion(item);
        QuestionItemEdition.this.list.getController().selectQuestion(null);
      }
    });
    this.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(final MouseEvent event) {
        QuestionItemEdition.this.list.getController().selectQuestion(item);
      }
    });
  }
  
  /**
   * GETTERS
   */
  public int getWeight() {
    return 1;
  }
  
  public float getScale() {
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
  
  public BoxType getType() {
    return this.type;
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
   * Sets the focused state of the zone and the item in the list
   * @param b true if we want to highlight this item, false if not.
   */
  public void setFocus(final boolean b) {
    if (b) {
      this.setColor(FxSettings.ITEM_HIGHLIGHT_COLOR);
      this.name.getStyleClass().add("focusedText");
      this.zone.setFocus(b);
    } else {
      this.setColor(FxSettings.ITEM_NORMAL_COLOR);
      this.name.getStyleClass().remove("focusedText");
      this.zone.setFocus(b);
    }
  }
  
  /**
   * Changes the color of the item in the list.
   * @param color the color we want the background to be
   */
  public void setColor(final Color color) {
    BackgroundFill bf = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
    Background _background = new Background(bf);
    this.setBackground(_background);
  }
  
  public void setName(final String text) {
    this.name.setText(text);
  }
  
  public float setScale(final float scale) {
    return this.scale = scale;
  }
}
