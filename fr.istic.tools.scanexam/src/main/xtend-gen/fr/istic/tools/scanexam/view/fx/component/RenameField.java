package fr.istic.tools.scanexam.view.fx.component;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

@SuppressWarnings("all")
public class RenameField extends HBox {
  public RenameField() {
    Label _label = new Label("Temp");
    this.label = _label;
    TextField _textField = new TextField("Temp");
    this.field = _textField;
    Label _label_1 = new Label("✎");
    this.icon = _label_1;
    this.icon.getStyleClass().add("unicodeLabel");
    this.getChildren().addAll(this.label, this.icon);
    this.field.getStyleClass().add("mytext-field");
    this.label.getStyleClass().add("renameLabel");
    this.setAlignment(Pos.CENTER_LEFT);
    this.setupEvents();
  }
  
  private Label label;
  
  TextField field;
  
  private Label icon;
  
  /**
   * Formatter used incase we only want to allow numbers
   */
  private TextFormatter<Number> formatter;
  
  /**
   * Property used to detect renaming of a field
   */
  private StringProperty prop = new SimpleStringProperty(this, "NewText", "temp");
  
  /**
   * toggles the visibility of the label or the field
   */
  private Boolean toggleRename(final boolean b) {
    boolean _xifexpression = false;
    if (b) {
      this.getChildren().remove(this.label);
      this.getChildren().remove(this.icon);
      this.getChildren().add(this.field);
      this.field.requestFocus();
      this.field.selectAll();
    } else {
      boolean _xifexpression_1 = false;
      boolean _contains = this.getChildren().contains(this.label);
      boolean _not = (!_contains);
      if (_not) {
        boolean _xblockexpression = false;
        {
          this.label.setText(this.field.getText());
          this.prop.setValue(this.field.getText());
          this.getChildren().remove(this.field);
          _xblockexpression = this.getChildren().addAll(this.label, this.icon);
        }
        _xifexpression_1 = _xblockexpression;
      }
      _xifexpression = _xifexpression_1;
    }
    return Boolean.valueOf(_xifexpression);
  }
  
  /**
   * Returns the text contained by this component
   */
  public String getText() {
    return this.label.getText();
  }
  
  /**
   * Sets the text contained by this component
   */
  public void setText(final String text) {
    this.label.setText(text);
  }
  
  /**
   * Returns the TextProperty of the component
   */
  public StringProperty getTextProperty() {
    return this.prop;
  }
  
  public TextFormatter<Number> setFieldFormatter(final TextFormatter<Number> formatter) {
    TextFormatter<Number> _xblockexpression = null;
    {
      this.field.setTextFormatter(formatter);
      _xblockexpression = this.formatter = formatter;
    }
    return _xblockexpression;
  }
  
  public float getFormatterValue() {
    return this.formatter.getValue().floatValue();
  }
  
  /**
   * Sets up the events for this component, called in the constructor
   */
  private void setupEvents() {
    final EventHandler<ActionEvent> _function = (ActionEvent e) -> {
      this.toggleRename(false);
    };
    this.field.setOnAction(_function);
    final ChangeListener<Boolean> _function_1 = (ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) -> {
      if ((!(newVal).booleanValue())) {
        this.toggleRename(false);
      } else {
        this.field.setText(this.label.getText());
      }
    };
    this.field.focusedProperty().addListener(_function_1);
    final EventHandler<MouseEvent> _function_2 = (MouseEvent e) -> {
      this.toggleRename(true);
    };
    this.label.setOnMouseClicked(_function_2);
    final EventHandler<MouseEvent> _function_3 = (MouseEvent e) -> {
      this.toggleRename(true);
    };
    this.icon.setOnMouseClicked(_function_3);
  }
}
