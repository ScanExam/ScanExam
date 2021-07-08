package fr.istic.tools.scanexam.view.fx.students;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.services.api.ServiceGraduation;
import fr.istic.tools.scanexam.view.fx.ControllerRoot;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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
  
  private ControllerRoot controllerRoot;
  
  public ControllerRoot init(final ServiceGraduation serviceG, final ControllerRoot rootController) {
    ControllerRoot _xblockexpression = null;
    {
      this.serviceGrad = serviceG;
      _xblockexpression = this.controllerRoot = rootController;
    }
    return _xblockexpression;
  }
  
  public Object update() {
    this.initTable();
    this.updateQuestionList();
    this.updateStudentsList();
    this.addContextMenuOnEachLines();
    this.mainPane.setContent(this.table);
    return null;
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
      String _translate = LanguageManager.translate("studentsTab.tableView.ID");
      TableColumn<StudentSheet, String> idCol = new TableColumn<StudentSheet, String>(_translate);
      PropertyValueFactory<StudentSheet, String> _propertyValueFactory = new PropertyValueFactory<StudentSheet, String>("studentName");
      idCol.setCellValueFactory(_propertyValueFactory);
      this.table.getColumns().add(idCol);
      int _numberOfQuestions = this.serviceGrad.numberOfQuestions();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _numberOfQuestions, true);
      for (final int i : _doubleDotLessThan) {
        float _maxPoint = this.serviceGrad.getQuestion(i).getGradeScale().getMaxPoint();
        boolean _notEquals = (_maxPoint != 0f);
        if (_notEquals) {
          String _name = this.serviceGrad.getQuestion(i).getName();
          TableColumn<StudentSheet, Float> col = new TableColumn<StudentSheet, Float>(_name);
          col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StudentSheet, Float>, ObservableValue<Float>>() {
            @Override
            public ObservableValue<Float> call(final TableColumn.CellDataFeatures<StudentSheet, Float> cd) {
              final Callable<Float> _function = () -> {
                return Float.valueOf(cd.getValue().getGrades().get(i).getGradeValue());
              };
              return Bindings.<Float>createObjectBinding(_function);
            }
          });
          this.table.getColumns().add(col);
        }
      }
      String _translate_1 = LanguageManager.translate("studentsTab.total");
      TableColumn<StudentSheet, Float> col_1 = new TableColumn<StudentSheet, Float>(_translate_1);
      col_1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StudentSheet, Float>, ObservableValue<Float>>() {
        @Override
        public ObservableValue<Float> call(final TableColumn.CellDataFeatures<StudentSheet, Float> cd) {
          final Callable<Float> _function = () -> {
            return Float.valueOf(cd.getValue().computeGrade());
          };
          return Bindings.<Float>createObjectBinding(_function);
        }
      });
      _xblockexpression = this.table.getColumns().add(col_1);
    }
    return _xblockexpression;
  }
  
  public void initTable() {
    TableView<StudentSheet> _tableView = new TableView<StudentSheet>();
    this.table = _tableView;
    this.table.setPrefHeight(720);
    this.table.setPrefWidth(720);
  }
  
  public void addContextMenuOnEachLines() {
    this.table.setRowFactory(new Callback<TableView<StudentSheet>, TableRow<StudentSheet>>() {
      @Override
      public TableRow<StudentSheet> call(final TableView<StudentSheet> tableView) {
        final TableRow<StudentSheet> row = new TableRow<StudentSheet>();
        final ContextMenu sheetMenu = new ContextMenu();
        String _translate = LanguageManager.translate("studentsTab.contextMenu.goToSheet");
        final MenuItem goToSheet = new MenuItem(_translate);
        goToSheet.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(final ActionEvent action) {
            ControllerFxStudents.this.gotToSheet(row.getItem().getId());
          }
        });
        sheetMenu.getItems().add(goToSheet);
        row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).<ContextMenu>then(((ContextMenu) null)).otherwise(sheetMenu));
        return row;
      }
    });
  }
  
  public void gotToSheet(final int id) {
    this.controllerRoot.goToCorrectorTab(id);
  }
}
