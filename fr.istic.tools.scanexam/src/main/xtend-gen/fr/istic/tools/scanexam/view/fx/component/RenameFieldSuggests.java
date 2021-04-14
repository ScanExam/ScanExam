package fr.istic.tools.scanexam.view.fx.component;

import java.util.List;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

@SuppressWarnings("all")
public class RenameFieldSuggests extends RenameField {
  public StringProperty getFieldTextProperty() {
    return this.field.textProperty();
  }
  
  public RenameFieldSuggests() {
    super();
    ContextMenu _contextMenu = new ContextMenu();
    this.entriesPopup = _contextMenu;
  }
  
  private ContextMenu entriesPopup;
  
  public void showSuggestion(final List<String> strings) {
    this.entriesPopup.show(this, Side.BOTTOM, 0, 0);
    this.populatePopup(strings);
  }
  
  public void hideSuggestion() {
    this.entriesPopup.hide();
  }
  
  public void populatePopup(final List<String> list) {
    this.entriesPopup.getItems().clear();
    for (final String s : list) {
      {
        MenuItem item = new MenuItem(s);
        final EventHandler<ActionEvent> _function = (ActionEvent e) -> {
          this.field.setText(s);
          this.hideSuggestion();
        };
        item.setOnAction(_function);
        this.entriesPopup.getItems().add(item);
      }
    }
  }
}
