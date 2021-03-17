package fr.istic.tools.scanexam.view.fX.corrector;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

@SuppressWarnings("all")
public class StudentItem extends VBox {
  private int studentId;
  
  public StudentItem(final int studentId) {
    super();
    this.studentId = studentId;
    ObservableList<Node> _children = this.getChildren();
    String _plus = (Integer.valueOf(studentId) + "");
    Label _label = new Label(_plus);
    _children.add(_label);
  }
  
  public int setStudentId(final int id) {
    return this.studentId = id;
  }
  
  public int getStudentId() {
    return this.studentId;
  }
}
