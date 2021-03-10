package fr.istic.tools.scanexam.view.fX;

import javafx.scene.layout.VBox;

@SuppressWarnings("all")
public class StudentItem extends VBox {
  private int studentId;
  
  public StudentItem(final int studentId) {
    this.studentId = studentId;
  }
}
