package fr.istic.tools.scanexam.view.fx.students;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.services.api.ServiceGraduation;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ControllerFxStudents {
  @FXML
  private ScrollPane mainPane;
  
  private TableView<StudentSheet> table;
  
  private ServiceGraduation serviceGrad;
  
  private ContextMenu menu;
  
  public ContextMenu init(final ServiceGraduation serviceG) {
    ContextMenu _xblockexpression = null;
    {
      this.serviceGrad = serviceG;
      ContextMenu _contextMenu = new ContextMenu();
      _xblockexpression = this.menu = _contextMenu;
    }
    return _xblockexpression;
  }
  
  public void update() {
    this.initTable();
    this.updateQuestionList();
    this.updateStudentsList();
    this.addContextMenuOnEachLines();
    this.mainPane.setContent(this.table);
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
      this.table.getItems().add(sheet);
    }
  }
  
  public boolean updateQuestionList() {
    boolean _xblockexpression = false;
    {
      int _numberOfQuestions = this.serviceGrad.numberOfQuestions();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _numberOfQuestions, true);
      for (final int i : _doubleDotLessThan) {
        if ((i == 0)) {
          String _name = this.serviceGrad.getQuestion(i).getName();
          TableColumn<StudentSheet, String> col = new TableColumn<StudentSheet, String>(_name);
          PropertyValueFactory<StudentSheet, String> _propertyValueFactory = new PropertyValueFactory<StudentSheet, String>("studentName");
          col.setCellValueFactory(_propertyValueFactory);
          this.table.getColumns().add(col);
        } else {
          String _name_1 = this.serviceGrad.getQuestion(i).getName();
          TableColumn<StudentSheet, Float> col_1 = new TableColumn<StudentSheet, Float>(_name_1);
          col_1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StudentSheet, Float>, ObservableValue<Float>>() {
            @Override
            public ObservableValue<Float> call(final TableColumn.CellDataFeatures<StudentSheet, Float> cd) {
              final Callable<Float> _function = () -> {
                return Float.valueOf(cd.getValue().getGrades().get(i).getGradeValue());
              };
              return Bindings.<Float>createObjectBinding(_function);
            }
          });
          this.table.getColumns().add(col_1);
        }
      }
      String _translate = LanguageManager.translate("studentsTab.total");
      TableColumn<StudentSheet, Float> col_2 = new TableColumn<StudentSheet, Float>(_translate);
      col_2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StudentSheet, Float>, ObservableValue<Float>>() {
        @Override
        public ObservableValue<Float> call(final TableColumn.CellDataFeatures<StudentSheet, Float> cd) {
          final Callable<Float> _function = () -> {
            return Float.valueOf(cd.getValue().computeGrade());
          };
          return Bindings.<Float>createObjectBinding(_function);
        }
      });
      _xblockexpression = this.table.getColumns().add(col_2);
    }
    return _xblockexpression;
  }
  
  public void initTable() {
    TableView<StudentSheet> _tableView = new TableView<StudentSheet>();
    this.table = _tableView;
    this.table.setPrefHeight(720);
    this.table.setPrefWidth(720);
  }
  
  public Object addContextMenuOnEachLines() {
    return null;
  }
}
