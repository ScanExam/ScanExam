package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.view.fX.Box;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

@SuppressWarnings("all")
public class ListViewBox extends HBox {
  public ListViewBox(final String text, final Box parent) {
    super();
    this.parent = parent;
    TextField field = new TextField(text);
    field.setMaxWidth(75);
    this.getChildren().add(field);
  }
  
  private Box parent;
  
  public Box getParentBox() {
    return this.parent;
  }
}
