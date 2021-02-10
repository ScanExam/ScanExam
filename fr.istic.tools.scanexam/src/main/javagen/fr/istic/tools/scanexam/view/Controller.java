package fr.istic.tools.scanexam.view;

import com.google.common.base.Objects;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class Controller {
  public static class CopieItem extends HBox {
    public CopieItem() {
      super();
      this.add("question something");
    }
    
    public void add(final String s) {
      ObservableList<Node> _children = this.getChildren();
      Label _label = new Label(s);
      _children.add(_label);
    }
  }
  
  private boolean topShow = false;
  
  private boolean botShow = false;
  
  public Pane topPane;
  
  public Button topButtonHidden;
  
  public Button topButtonActive;
  
  public Button botButtonHidden;
  
  public Button botButtonActive;
  
  public Pane bottomPane;
  
  public ListView leftList;
  
  public ListView rightList;
  
  /**
   * Toggles the visibility of the bottom window
   */
  public void toggleBottom() throws IOException {
    this.bottomPane.setVisible((!this.botShow));
    this.botButtonHidden.setVisible(this.botShow);
    this.botShow = (!this.botShow);
  }
  
  /**
   * Used to resize the window containing the corrected exam
   */
  public void dragBottom(final MouseEvent event) {
    EventType<? extends MouseEvent> _eventType = event.getEventType();
    boolean _equals = Objects.equal(_eventType, MouseEvent.MOUSE_DRAGGED);
    if (_equals) {
      double _height = this.bottomPane.getScene().getHeight();
      double _minus = (_height - 100);
      double _height_1 = this.bottomPane.getScene().getHeight();
      double _sceneY = event.getSceneY();
      double _minus_1 = (_height_1 - _sceneY);
      this.bottomPane.setPrefHeight(Math.max(0, Math.min(_minus, _minus_1)));
    }
  }
  
  private double mouseOriginX = 0d;
  
  private double mouseOriginY = 0d;
  
  private double objectOriginX = 0d;
  
  private double objectOriginY = 0d;
  
  public void MoveImage(final MouseEvent e) {
    EventType<? extends MouseEvent> _eventType = e.getEventType();
    boolean _equals = Objects.equal(_eventType, MouseEvent.MOUSE_PRESSED);
    if (_equals) {
      InputOutput.<String>println("Starting to move");
      this.mouseOriginX = e.getScreenX();
      this.mouseOriginY = e.getScreenY();
      Object _source = e.getSource();
      Node source = ((Node) _source);
      InputOutput.<Node>println(source);
      this.objectOriginX = source.getLayoutX();
      this.objectOriginY = source.getLayoutY();
    }
    EventType<? extends MouseEvent> _eventType_1 = e.getEventType();
    boolean _equals_1 = Objects.equal(_eventType_1, MouseEvent.MOUSE_DRAGGED);
    if (_equals_1) {
      InputOutput.<String>println("moving");
      Object _source_1 = e.getSource();
      Node source_1 = ((Node) _source_1);
      double _screenX = e.getScreenX();
      double _minus = (_screenX - this.mouseOriginX);
      double _plus = (this.objectOriginX + _minus);
      source_1.setLayoutX(_plus);
      double _screenY = e.getScreenY();
      double _minus_1 = (_screenY - this.mouseOriginY);
      double _plus_1 = (this.objectOriginY + _minus_1);
      source_1.setLayoutY(_plus_1);
      String _plus_2 = (source_1 + " ");
      double _layoutX = source_1.getLayoutX();
      String _plus_3 = (_plus_2 + Double.valueOf(_layoutX));
      String _plus_4 = (_plus_3 + " ");
      double _layoutY = source_1.getLayoutY();
      String _plus_5 = (_plus_4 + Double.valueOf(_layoutY));
      InputOutput.<String>println(_plus_5);
    }
  }
  
  public void ZoomImage(final ScrollEvent e) {
    Object _source = e.getSource();
    Node source = ((Node) _source);
    double _deltaY = e.getDeltaY();
    boolean _greaterThan = (_deltaY > 0);
    if (_greaterThan) {
      double _scaleX = source.getScaleX();
      double _multiply = (_scaleX * 0.95);
      source.setScaleX(_multiply);
      double _scaleY = source.getScaleY();
      double _multiply_1 = (_scaleY * 0.95);
      source.setScaleY(_multiply_1);
    } else {
      double _scaleX_1 = source.getScaleX();
      double _multiply_2 = (_scaleX_1 * 1.05);
      source.setScaleX(_multiply_2);
      double _scaleY_1 = source.getScaleY();
      double _multiply_3 = (_scaleY_1 * 1.05);
      source.setScaleY(_multiply_3);
    }
  }
  
  /**
   * Called when a <b>save</b> button is pressed
   */
  public void savePressed() {
    InputOutput.<String>println("Saving method");
  }
  
  /**
   * Called when a <b>save a</b> button is pressed
   */
  public void saveAsPressed() {
    InputOutput.<String>println("Saving as method");
  }
  
  /**
   * Called when a <b>load</b> button is pressed
   */
  public void loadPressed() {
    InputOutput.<String>println("Load method");
  }
  
  /**
   * Called when a <b>import</b> button is pressed
   */
  public void importPressed() {
    InputOutput.<String>println("Import method");
    ObservableList _items = this.rightList.getItems();
    Button _button = new Button("11");
    _items.add(_button);
    this.rightList.getItems().add("1");
    this.rightList.getItems().add("2");
    this.addQuestionList();
  }
  
  /**
   * Called when a <b>export</b> button is pressed
   */
  public void exportPressed() {
    InputOutput.<String>println("Export method");
  }
  
  /**
   * Called when a <b>next question</b> button is pressed
   */
  public void nextQuestionPressed() {
    InputOutput.<String>println("Next question method");
  }
  
  /**
   * Called when a <b>previous question pressed</b> button is pressed
   */
  public void prevQuestionPressed() {
    InputOutput.<String>println("Prev question method");
  }
  
  /**
   * Called when a <b>next student</b> button is pressed
   */
  public void nextStudentPressed() {
    InputOutput.<String>println("Next student method");
  }
  
  /**
   * Called when a <b>previous studend</b> button is pressed
   */
  public void prevStudentPressed() {
    InputOutput.<String>println("Prev student method");
  }
  
  public void addBaremeList() {
  }
  
  public void addQuestionList() {
  }
}
