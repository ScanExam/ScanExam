package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.view.fX.Box;
import fr.istic.tools.scanexam.view.fX.FXSettings;
import java.io.InputStream;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

@SuppressWarnings("all")
public class ListViewBox extends VBox {
  public ListViewBox(final String text, final Box parent) {
    super();
    HBox _hBox = new HBox();
    this.top = _hBox;
    HBox _hBox_1 = new HBox();
    this.bottom = _hBox_1;
    this.id = parent.getBoxId();
    this.parent = parent;
    TextField _textField = new TextField(text);
    this.field = _textField;
    this.field.setMaxWidth(75);
    this.getChildren().add(this.top);
    this.getChildren().add(this.bottom);
    this.top.getChildren().add(this.field);
    this.makeButtons();
  }
  
  private TextField field;
  
  private HBox top;
  
  private HBox bottom;
  
  private int id;
  
  private Box parent;
  
  private Button up;
  
  private Button down;
  
  private Button rm;
  
  private Button resize;
  
  private Button move;
  
  public Box getParentBox() {
    return this.parent;
  }
  
  public int getBoxId() {
    return this.id;
  }
  
  public boolean makeButtons() {
    boolean _xblockexpression = false;
    {
      Button _button = new Button("");
      this.up = _button;
      Button _button_1 = new Button("");
      this.down = _button_1;
      Button _button_2 = new Button("remove");
      this.rm = _button_2;
      Button _button_3 = new Button("resize");
      this.resize = _button_3;
      Button _button_4 = new Button("move");
      this.move = _button_4;
      InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("/viewResources/upArrow.png");
      Image upImage = new Image(_inputStreamResource, FXSettings.BUTTON_ICON_SIZE, FXSettings.BUTTON_ICON_SIZE, true, true);
      ImageView _imageView = new ImageView(upImage);
      this.up.setGraphic(_imageView);
      this.top.getChildren().add(0, this.up);
      InputStream _inputStreamResource_1 = ResourcesUtils.getInputStreamResource("/viewResources/downArrow.png");
      Image downImage = new Image(_inputStreamResource_1, FXSettings.BUTTON_ICON_SIZE, FXSettings.BUTTON_ICON_SIZE, true, true);
      ImageView _imageView_1 = new ImageView(downImage);
      this.down.setGraphic(_imageView_1);
      this.bottom.getChildren().add(this.down);
      this.bottom.getChildren().add(this.rm);
      this.bottom.getChildren().add(this.resize);
      _xblockexpression = this.bottom.getChildren().add(this.move);
    }
    return _xblockexpression;
  }
  
  public void setUpAction(final EventHandler<ActionEvent> handler) {
    this.up.setOnAction(handler);
  }
  
  public void setDownAction(final EventHandler<ActionEvent> handler) {
    this.down.setOnAction(handler);
  }
  
  public void setRemoveAction(final EventHandler<ActionEvent> handler) {
    this.rm.setOnAction(handler);
  }
  
  public void setTextCommit(final EventHandler<ActionEvent> hander) {
    this.field.setOnAction(hander);
  }
  
  public void setMoveAction(final EventHandler<ActionEvent> hander) {
    this.move.setOnAction(hander);
  }
  
  public void setResizeAction(final EventHandler<ActionEvent> hander) {
    this.resize.setOnAction(hander);
  }
}
