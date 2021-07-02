package fr.istic.tools.scanexam.view.fx.students;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.core.Grade;
import fr.istic.tools.scanexam.core.GradeEntry;
import fr.istic.tools.scanexam.core.StudentSheet;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.eclipse.emf.common.util.EList;

@SuppressWarnings("all")
public class StudentItem extends HBox {
  private Label labelName;
  
  private List<Label> questions;
  
  public StudentItem(final StudentSheet sheet) {
    String name = sheet.getStudentName();
    if (((name == null) || (name == ""))) {
      String _translate = LanguageManager.translate("name.default");
      String _plus = (_translate + " ");
      int _id = sheet.getId();
      String _plus_1 = (_plus + Integer.valueOf(_id));
      name = _plus_1;
    }
    Label _label = new Label(name);
    this.labelName = _label;
    Insets _insets = new Insets(5, 5, 5, 5);
    this.labelName.setPadding(_insets);
    this.getChildren().add(this.labelName);
    ArrayList<Label> _arrayList = new ArrayList<Label>();
    this.questions = _arrayList;
    float studentNote = 0f;
    EList<Grade> _grades = sheet.getGrades();
    for (final Grade grade : _grades) {
      {
        float pointsGrade = 0f;
        EList<GradeEntry> _entries = grade.getEntries();
        for (final GradeEntry entry : _entries) {
          {
            float _pointsGrade = pointsGrade;
            float _step = entry.getStep();
            pointsGrade = (_pointsGrade + _step);
            float _studentNote = studentNote;
            float _step_1 = entry.getStep();
            studentNote = (_studentNote + _step_1);
          }
        }
        String _string = Float.valueOf(pointsGrade).toString();
        Label someGrade = new Label(_string);
        Insets _insets_1 = new Insets(5, 5, 5, 5);
        someGrade.setPadding(_insets_1);
        this.questions.add(someGrade);
      }
    }
    String _string = Float.valueOf(studentNote).toString();
    Label total = new Label(_string);
    Insets _insets_1 = new Insets(5, 5, 5, 5);
    total.setPadding(_insets_1);
    this.questions.add(total);
    this.getChildren().addAll(this.questions);
  }
}
