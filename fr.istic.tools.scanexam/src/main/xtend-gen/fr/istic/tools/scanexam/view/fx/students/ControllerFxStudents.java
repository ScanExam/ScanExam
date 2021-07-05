package fr.istic.tools.scanexam.view.fx.students;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.core.GradeEntry;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.services.api.ServiceGraduation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.GridPane;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ControllerFxStudents {
  @FXML
  private ScrollPane mainPane;
  
  private GridPane grille;
  
  private ServiceGraduation serviceGrad;
  
  private ContextMenu menu;
  
  public ContextMenu init(final ServiceGraduation serviceG) {
    ContextMenu _xblockexpression = null;
    {
      this.serviceGrad = serviceG;
      GridPane _gridPane = new GridPane();
      this.grille = _gridPane;
      ContextMenu _contextMenu = new ContextMenu();
      _xblockexpression = this.menu = _contextMenu;
    }
    return _xblockexpression;
  }
  
  public void update() {
    this.grille.getChildren().clear();
    this.updateQuestionList();
    this.updateStudentsList();
    this.addContextMenuOnEachLines();
    Insets _insets = new Insets(10, 10, 10, 10);
    this.grille.setPadding(_insets);
    this.mainPane.setContent(this.grille);
    this.grille.setGridLinesVisible(true);
  }
  
  public void updateStudentsList() {
    List<StudentSheet> _list = IterableExtensions.<StudentSheet>toList(this.serviceGrad.getStudentSheets());
    final List<StudentSheet> sheets = new LinkedList<StudentSheet>(_list);
    final Comparator<StudentSheet> _function = (StudentSheet s1, StudentSheet s2) -> {
      int _id = s1.getId();
      int _id_1 = s2.getId();
      return (_id - _id_1);
    };
    Collections.<StudentSheet>sort(sheets, _function);
    for (final StudentSheet sheet : sheets) {
      {
        ArrayList<Label> questions = new ArrayList<Label>();
        float studentNote = 0f;
        int _size = sheet.getGrades().size();
        ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
        for (final int g : _doubleDotLessThan) {
          {
            float pointsGrade = 0f;
            EList<GradeEntry> _entries = sheet.getGrades().get(g).getEntries();
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
            Label someGrade = null;
            if ((g == 0)) {
              String name = sheet.getStudentName();
              if (((name == null) || (name == ""))) {
                String _translate = LanguageManager.translate("name.default");
                String _plus = (_translate + " ");
                int _id = sheet.getId();
                String _plus_1 = (_plus + Integer.valueOf(_id));
                name = _plus_1;
              }
              Label _label = new Label(name);
              someGrade = _label;
            } else {
              String _string = Float.valueOf(pointsGrade).toString();
              Label _label_1 = new Label(_string);
              someGrade = _label_1;
            }
            Insets _insets = new Insets(5, 5, 5, 5);
            someGrade.setPadding(_insets);
            questions.add(someGrade);
          }
        }
        String _string = Float.valueOf(studentNote).toString();
        Label total = new Label(_string);
        Insets _insets = new Insets(5, 5, 5, 5);
        total.setPadding(_insets);
        questions.add(total);
        for (final Label question : questions) {
          int _indexOf = questions.indexOf(question);
          int _id = sheet.getId();
          int _plus = (_id + 1);
          this.grille.add(question, _indexOf, _plus);
        }
      }
    }
  }
  
  public void updateQuestionList() {
    int _numberOfQuestions = this.serviceGrad.numberOfQuestions();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _numberOfQuestions, true);
    for (final int i : _doubleDotLessThan) {
      {
        String _name = this.serviceGrad.getQuestion(i).getName();
        Label tete = new Label(_name);
        Insets _insets = new Insets(5, 5, 5, 5);
        tete.setPadding(_insets);
        this.grille.add(tete, i, 0);
      }
    }
    String _translate = LanguageManager.translate("studentsTab.total");
    Label total = new Label(_translate);
    Insets _insets = new Insets(5, 5, 5, 5);
    total.setPadding(_insets);
    this.grille.add(total, this.serviceGrad.numberOfQuestions(), 0);
  }
  
  public void addContextMenuOnEachLines() {
    int _rowCount = this.grille.getRowCount();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _rowCount, true);
    for (final Integer line : _doubleDotLessThan) {
      int _columnCount = this.grille.getColumnCount();
      ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _columnCount, true);
      for (final Integer col : _doubleDotLessThan_1) {
        ObservableList<Node> _children = this.grille.getChildren();
        int _columnCount_1 = this.grille.getColumnCount();
        int _multiply = ((line).intValue() * _columnCount_1);
        int _plus = (_multiply + (col).intValue());
        Node _get = _children.get(_plus);
        _get.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
          @Override
          public void handle(final ContextMenuEvent event) {
            int _columnCount = ControllerFxStudents.this.grille.getColumnCount();
            int _multiply = ((line).intValue() * _columnCount);
            String _plus = ("Bonjour " + Integer.valueOf(_multiply));
            String _plus_1 = (_plus + col);
            final MenuItem itemTest = new MenuItem(_plus_1);
            ControllerFxStudents.this.menu.getItems().clear();
            ControllerFxStudents.this.menu.getItems().add(itemTest);
            ControllerFxStudents.this.menu.show(ControllerFxStudents.this.grille, event.getScreenX(), event.getScreenY());
          }
        });
      }
    }
  }
}
