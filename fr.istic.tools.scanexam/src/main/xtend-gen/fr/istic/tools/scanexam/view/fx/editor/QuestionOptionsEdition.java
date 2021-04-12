package fr.istic.tools.scanexam.view.fx.editor;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition;
import fr.istic.tools.scanexam.view.fx.editor.QuestionItemEdition;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

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
    final Label editIcon1 = new Label("✎");
    final Label editIcon2 = new Label("✎");
    editIcon1.getStyleClass().add("unicodeLabel");
    editIcon2.getStyleClass().add("unicodeLabel");
    this.grid.add(editIcon1, 2, 0);
    this.grid.add(editIcon2, 2, 3);
    TextField _textField = new TextField();
    this.renameField = _textField;
    double _prefWidth = this.grid.getPrefWidth();
    double _multiply = (_prefWidth * 0.7);
    this.renameField.setPrefWidth(_multiply);
    TextField _textField_1 = new TextField();
    this.scaleField = _textField_1;
    NumberStringConverter _numberStringConverter = new NumberStringConverter();
    TextFormatter<Number> _textFormatter = new TextFormatter<Number>(_numberStringConverter);
    this.formatter = _textFormatter;
    this.scaleField.setTextFormatter(this.formatter);
    double _prefWidth_1 = this.grid.getPrefWidth();
    double _multiply_1 = (_prefWidth_1 * 0.7);
    this.scaleField.setPrefWidth(_multiply_1);
    Label _label = new Label();
    this.questionName = _label;
    Label _label_1 = new Label();
    this.questionId = _label_1;
    Label _label_2 = new Label();
    this.page = _label_2;
    Label _label_3 = new Label();
    this.questionScale = _label_3;
    Label _label_4 = new Label();
    this.questionCoords = _label_4;
    Label _label_5 = new Label();
    this.questionDescription = _label_5;
    String _translate_6 = LanguageManager.translate("question.remove");
    Button _button = new Button(_translate_6);
    this.remove = _button;
    this.grid.add(this.questionName, 1, 0);
    this.grid.add(this.questionId, 1, 1);
    this.grid.add(this.page, 1, 2);
    this.grid.add(this.questionScale, 1, 3);
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
    double _x = item.getZone().getX();
    String _plus_4 = ("X:" + Double.valueOf(_x));
    String _plus_5 = (_plus_4 + "\nY:");
    double _y = item.getZone().getY();
    String _plus_6 = (_plus_5 + Double.valueOf(_y));
    String _plus_7 = (_plus_6 + "\nH:");
    double _height = item.getZone().getHeight();
    String _plus_8 = (_plus_7 + Double.valueOf(_height));
    String _plus_9 = (_plus_8 + "\nW:");
    double _width = item.getZone().getWidth();
    String _plus_10 = (_plus_9 + Double.valueOf(_width));
    this.questionCoords.setText(_plus_10);
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
      final Function1<Node, Boolean> _function = new Function1<Node, Boolean>() {
        public Boolean apply(final Node n) {
          return Boolean.valueOf((((n != null) && ((GridPane.getRowIndex(n)).intValue() == 0)) && ((GridPane.getColumnIndex(n)).intValue() == 2)));
        }
      };
      Node _findFirst = IterableExtensions.<Node>findFirst(this.grid.getChildren(), _function);
      _findFirst.setVisible(false);
    } else {
      boolean _contains = this.grid.getChildren().contains(this.questionName);
      boolean _not = (!_contains);
      if (_not) {
        this.grid.getChildren().remove(this.renameField);
        this.grid.add(this.questionName, 1, 0);
        final Function1<Node, Boolean> _function_1 = new Function1<Node, Boolean>() {
          public Boolean apply(final Node n) {
            return Boolean.valueOf((((n != null) && ((GridPane.getRowIndex(n)).intValue() == 0)) && ((GridPane.getColumnIndex(n)).intValue() == 2)));
          }
        };
        Node _findFirst_1 = IterableExtensions.<Node>findFirst(this.grid.getChildren(), _function_1);
        _findFirst_1.setVisible(true);
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
      final Function1<Node, Boolean> _function = new Function1<Node, Boolean>() {
        public Boolean apply(final Node n) {
          return Boolean.valueOf((((n != null) && ((GridPane.getRowIndex(n)).intValue() == 3)) && ((GridPane.getColumnIndex(n)).intValue() == 2)));
        }
      };
      Node _findFirst = IterableExtensions.<Node>findFirst(this.grid.getChildren(), _function);
      _findFirst.setVisible(false);
    } else {
      boolean _contains = this.grid.getChildren().contains(this.questionScale);
      boolean _not = (!_contains);
      if (_not) {
        this.grid.getChildren().remove(this.scaleField);
        this.grid.add(this.questionScale, 1, 3);
        final Function1<Node, Boolean> _function_1 = new Function1<Node, Boolean>() {
          public Boolean apply(final Node n) {
            return Boolean.valueOf((((n != null) && ((GridPane.getRowIndex(n)).intValue() == 3)) && ((GridPane.getColumnIndex(n)).intValue() == 2)));
          }
        };
        Node _findFirst_1 = IterableExtensions.<Node>findFirst(this.grid.getChildren(), _function_1);
        _findFirst_1.setVisible(true);
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
      public void handle(final ActionEvent event) {
        QuestionOptionsEdition.this.controller.getQuestionList().remove(QuestionOptionsEdition.this.currentItem);
        QuestionOptionsEdition.this.controller.getMainPane().removeZone(QuestionOptionsEdition.this.currentItem.getZone());
        QuestionOptionsEdition.this.controller.selectQuestion(null);
      }
    });
    final Function1<Node, Boolean> _function = new Function1<Node, Boolean>() {
      public Boolean apply(final Node n) {
        return Boolean.valueOf((((n != null) && ((GridPane.getRowIndex(n)).intValue() == 0)) && ((GridPane.getColumnIndex(n)).intValue() == 2)));
      }
    };
    Node _findFirst = IterableExtensions.<Node>findFirst(this.grid.getChildren(), _function);
    final EventHandler<MouseEvent> _function_1 = new EventHandler<MouseEvent>() {
      public void handle(final MouseEvent e) {
        QuestionOptionsEdition.this.toggleRename(true);
      }
    };
    _findFirst.setOnMouseClicked(_function_1);
    this.questionName.setOnMouseClicked(new EventHandler<MouseEvent>() {
      public void handle(final MouseEvent event) {
        QuestionOptionsEdition.this.toggleRename(true);
      }
    });
    this.questionScale.setOnMouseClicked(new EventHandler<MouseEvent>() {
      public void handle(final MouseEvent event) {
        QuestionOptionsEdition.this.toggleRescale(true);
      }
    });
    this.renameField.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(final ActionEvent event) {
        InputOutput.<String>print("Rename actions\n");
        QuestionOptionsEdition.this.commitRename();
        QuestionOptionsEdition.this.toggleRename(false);
      }
    });
    ReadOnlyBooleanProperty _focusedProperty = this.renameField.focusedProperty();
    _focusedProperty.addListener(new ChangeListener<Boolean>() {
      public void changed(final ObservableValue<? extends Boolean> observable, final Boolean oldValue, final Boolean newValue) {
        if ((!(newValue).booleanValue())) {
          InputOutput.<String>print("Rename actions\n");
          QuestionOptionsEdition.this.commitRename();
          QuestionOptionsEdition.this.toggleRename(false);
        }
      }
    });
    final Function1<Node, Boolean> _function_2 = new Function1<Node, Boolean>() {
      public Boolean apply(final Node n) {
        return Boolean.valueOf((((n != null) && ((GridPane.getRowIndex(n)).intValue() == 3)) && ((GridPane.getColumnIndex(n)).intValue() == 2)));
      }
    };
    Node _findFirst_1 = IterableExtensions.<Node>findFirst(this.grid.getChildren(), _function_2);
    final EventHandler<MouseEvent> _function_3 = new EventHandler<MouseEvent>() {
      public void handle(final MouseEvent e) {
        QuestionOptionsEdition.this.toggleRescale(true);
      }
    };
    _findFirst_1.setOnMouseClicked(_function_3);
    this.scaleField.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(final ActionEvent event) {
        InputOutput.<String>print("Rescale actions\n");
        QuestionOptionsEdition.this.commitRescale();
        QuestionOptionsEdition.this.toggleRescale(false);
      }
    });
    ReadOnlyBooleanProperty _focusedProperty_1 = this.scaleField.focusedProperty();
    _focusedProperty_1.addListener(new ChangeListener<Boolean>() {
      public void changed(final ObservableValue<? extends Boolean> observable, final Boolean oldValue, final Boolean newValue) {
        if ((!(newValue).booleanValue())) {
          InputOutput.<String>print("Rescale actions\n");
          QuestionOptionsEdition.this.commitRescale();
          QuestionOptionsEdition.this.toggleRescale(false);
        }
      }
    });
  }
}
