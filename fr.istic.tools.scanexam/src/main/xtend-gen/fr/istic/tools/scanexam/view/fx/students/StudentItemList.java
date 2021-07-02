package fr.istic.tools.scanexam.view.fx.students;

import fr.istic.tools.scanexam.view.fx.students.StudentItem;
import javafx.scene.layout.VBox;

@SuppressWarnings("all")
public class StudentItemList extends VBox {
  public boolean addItem(final StudentItem item) {
    return this.getChildren().add(item);
  }
}
