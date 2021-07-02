package fr.istic.tools.scanexam.view.fx.students;

import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.services.api.ServiceEdition;
import fr.istic.tools.scanexam.services.api.ServiceGraduation;
import fr.istic.tools.scanexam.view.fx.students.QuestionList;
import fr.istic.tools.scanexam.view.fx.students.StudentItem;
import fr.istic.tools.scanexam.view.fx.students.StudentItemList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ControllerFxStudents {
  @FXML
  private ScrollPane mainPane;
  
  private QuestionList questList;
  
  private StudentItemList studentsList;
  
  private ServiceGraduation serviceGrad;
  
  private ServiceEdition serviceEdition;
  
  public QuestionList init(final ServiceGraduation serviceG) {
    QuestionList _xblockexpression = null;
    {
      this.serviceGrad = serviceG;
      StudentItemList _studentItemList = new StudentItemList();
      this.studentsList = _studentItemList;
      QuestionList _questionList = new QuestionList();
      _xblockexpression = this.questList = _questionList;
    }
    return _xblockexpression;
  }
  
  public void update() {
    this.updateQuestionList();
    this.updateStudentsList();
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
    this.studentsList.getChildren().clear();
    for (final StudentSheet sheet : sheets) {
      StudentItem _studentItem = new StudentItem(sheet);
      this.studentsList.addItem(_studentItem);
    }
    this.mainPane.setContent(this.studentsList);
  }
  
  public Object updateQuestionList() {
    return null;
  }
}
