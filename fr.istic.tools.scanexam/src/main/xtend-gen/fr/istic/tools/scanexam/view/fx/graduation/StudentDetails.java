package fr.istic.tools.scanexam.view.fx.graduation;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.view.fx.component.RenameFieldSuggests;
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.StudentItemGraduation;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Affiche les détails d'un élève lors de la correction de sa copie
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class StudentDetails extends VBox {
  /**
   * Controleur JavaFX de graduation
   */
  private ControllerFxGraduation controller;
  
  /**
   * Grille contenant les informations
   */
  private GridPane grid;
  
  /**
   * Champ de text econtenant le nom de l'étudiant
   */
  private RenameFieldSuggests name;
  
  /**
   * Label affichant l'identifiant de l'étudiant
   */
  private Label idLabel;
  
  /**
   * Label affichant l'identifiant de l'étudiant
   */
  private Label gradeLabel;
  
  /**
   * Cercle affichant la qualité de la copie
   */
  private Circle qualityCircle;
  
  /**
   * Elément courant de notation
   */
  private StudentItemGraduation currentItem;
  
  /**
   * Logger
   */
  private static final Logger logger = LogManager.getLogger();
  
  /**
   * Constructeur
   * @param controller Controleur JavaFX de graduation
   */
  public StudentDetails(final ControllerFxGraduation controller) {
    this.controller = controller;
    String _translate = LanguageManager.translate("label.student.name");
    Label nameRow = new Label(_translate);
    String _translate_1 = LanguageManager.translate("label.student.id");
    Label idRow = new Label(_translate_1);
    String _translate_2 = LanguageManager.translate("label.student.grade");
    Label gradeRow = new Label(_translate_2);
    String _translate_3 = LanguageManager.translate("label.student.quality");
    Label qualityRow = new Label(_translate_3);
    GridPane _gridPane = new GridPane();
    this.grid = _gridPane;
    this.grid.add(nameRow, 0, 0);
    this.grid.add(idRow, 0, 2);
    this.grid.add(gradeRow, 0, 3);
    this.grid.add(qualityRow, 0, 4);
    RenameFieldSuggests _renameFieldSuggests = new RenameFieldSuggests();
    this.name = _renameFieldSuggests;
    Label _label = new Label();
    this.idLabel = _label;
    Label _label_1 = new Label();
    this.gradeLabel = _label_1;
    Circle _circle = new Circle(8, Color.GRAY);
    this.qualityCircle = _circle;
    this.grid.add(this.name, 0, 1);
    this.grid.add(this.idLabel, 1, 2);
    this.grid.add(this.gradeLabel, 1, 3);
    this.grid.add(this.qualityCircle, 1, 4);
    this.getChildren().add(this.grid);
    this.setupEvents();
  }
  
  public void setupEvents() {
    final ChangeListener<String> _function = (ObservableValue<? extends String> obs, String oldVal, String newVal) -> {
      this.commitRename();
    };
    this.name.getTextProperty().addListener(_function);
    final ChangeListener<String> _function_1 = (ObservableValue<? extends String> obs, String oldVal, String newVal) -> {
      this.findSuggestions(newVal);
    };
    this.name.getFieldTextProperty().addListener(_function_1);
  }
  
  public void commitRename() {
    String _text = this.name.getText();
    String _plus = ("Renaming to" + _text);
    StudentDetails.logger.info(_plus);
    this.currentItem.setStudentName(this.name.getText());
    this.controller.getStudentList().updateInModel(this.currentItem);
  }
  
  public void findSuggestions(final String start) {
    StudentDetails.logger.info("Changing");
    List<String> l = this.controller.getStudentsSuggestedNames(start);
    this.name.showSuggestion(l);
  }
  
  public void display(final StudentItemGraduation item) {
    this.setVisible(true);
    this.currentItem = item;
    this.setName();
    this.setId();
    this.updateGrade();
  }
  
  public void updateGrade() {
    float _globalGrade = this.controller.getGlobalGrade();
    String _plus = (Float.valueOf(_globalGrade) + "/");
    float _globalScale = this.controller.getGlobalScale();
    String _plus_1 = (_plus + Float.valueOf(_globalScale));
    this.gradeLabel.setText(_plus_1);
  }
  
  public void updateQuality() {
    float _globalGrade = this.controller.getGlobalGrade();
    float _currentMaxGrade = this.controller.getCurrentMaxGrade();
    final float quality = (_globalGrade / _currentMaxGrade);
    if ((quality >= 0.75)) {
      this.qualityCircle.setFill(Color.GREEN);
    } else {
      if ((quality >= 0.5)) {
        this.qualityCircle.setFill(Color.YELLOW);
      } else {
        if ((quality >= 0.25)) {
          this.qualityCircle.setFill(Color.ORANGE);
        } else {
          if ((quality >= 0)) {
            this.qualityCircle.setFill(Color.RED);
          } else {
            this.qualityCircle.setFill(Color.GRAY);
          }
        }
      }
    }
  }
  
  public void clearDisplay() {
    this.setVisible(false);
  }
  
  /**
   * SETTERS
   */
  private void setName() {
    this.name.setText(this.currentItem.getStudentName());
  }
  
  private void setId() {
    int _studentId = this.currentItem.getStudentId();
    String _plus = (Integer.valueOf(_studentId) + "");
    this.idLabel.setText(_plus);
  }
}
