package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.view.fx.ControllerLinkManuallySheets;
import fr.istic.tools.scanexam.view.fx.FailedPageItemList;
import fr.istic.tools.scanexam.view.fx.FxSettings;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

@SuppressWarnings("all")
public class FailedPageItem extends HBox {
  private int num;
  
  private Label numPage;
  
  private TextField field;
  
  private FailedPageItemList list;
  
  public FailedPageItem(final Integer num, final FailedPageItemList list) {
    this.num = (num).intValue();
    this.list = list;
    String _string = num.toString();
    Label _label = new Label(_string);
    this.numPage = _label;
    TextField _textField = new TextField();
    this.field = _textField;
    this.getChildren().add(this.numPage);
    this.getChildren().add(this.field);
    this.setupEvents();
  }
  
  public FailedPageItemList setList(final FailedPageItemList list) {
    return this.list = list;
  }
  
  public int getNum() {
    return this.num;
  }
  
  public TextField getField() {
    return this.field;
  }
  
  public void setFocus(final boolean b) {
    if (b) {
      this.setColor(FxSettings.ITEM_HIGHLIGHT_COLOR);
      this.numPage.setTextFill(Color.WHITE);
    } else {
      this.setColor(FxSettings.ITEM_NORMAL_COLOR);
      this.numPage.setTextFill(Color.BLACK);
    }
  }
  
  public void setColor(final Color color) {
    BackgroundFill bf = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
    Background _background = new Background(bf);
    this.setBackground(_background);
  }
  
  public void setupEvents() {
    this.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(final MouseEvent event) {
        ControllerLinkManuallySheets _controller = FailedPageItem.this.list.getController();
        _controller.setIndexCurrentPage(FailedPageItem.this.list.getController().getFailedPages().indexOf(Integer.valueOf(FailedPageItem.this.num)));
        FailedPageItem.this.list.getController().updateStatement();
      }
    });
  }
}
