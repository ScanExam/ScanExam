package fr.istic.tools.scanexam.view.fx.graduation;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.view.fx.component.RenameFieldSuggests;
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.StudentItemGraduation;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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
   * Champ de text contenant l'identifiant de l'étudiant
   */
  private RenameFieldSuggests userId;
  
  /**
   * Label affichant l'identifiant de l'étudiant pour l'application
   */
  private Label idLabel;
  
  /**
   * Label affichant la note de l'étudiant
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
    this.setAlignment(Pos.CENTER);
    String _translate = LanguageManager.translate("label.student.name");
    Label userIdRow = new Label(_translate);
    String _translate_1 = LanguageManager.translate("label.student.id");
    Label idRow = new Label(_translate_1);
    String _translate_2 = LanguageManager.translate("label.student.grade");
    Label gradeRow = new Label(_translate_2);
    String _translate_3 = LanguageManager.translate("label.student.quality");
    Label qualityRow = new Label(_translate_3);
    userIdRow.getStyleClass().add("RowTitle");
    idRow.getStyleClass().add("RowTitle");
    gradeRow.getStyleClass().add("RowTitle");
    qualityRow.getStyleClass().add("RowTitle");
    RenameFieldSuggests _renameFieldSuggests = new RenameFieldSuggests();
    this.userId = _renameFieldSuggests;
    this.userId.setAlignment(Pos.CENTER);
    Label _label = new Label();
    this.idLabel = _label;
    Label _label_1 = new Label();
    this.gradeLabel = _label_1;
    Circle _circle = new Circle(8, Color.GRAY);
    this.qualityCircle = _circle;
    this.getChildren().add(userIdRow);
    this.getChildren().add(this.userId);
    this.getChildren().add(idRow);
    this.getChildren().add(this.idLabel);
    this.getChildren().add(gradeRow);
    this.getChildren().add(this.gradeLabel);
    this.getChildren().add(qualityRow);
    this.getChildren().add(this.qualityCircle);
    this.setupEvents();
  }
  
  public void setupEvents() {
    final ChangeListener<String> _function = (ObservableValue<? extends String> obs, String oldVal, String newVal) -> {
      this.commitRename();
    };
    this.userId.getTextProperty().addListener(_function);
    final ChangeListener<String> _function_1 = (ObservableValue<? extends String> obs, String oldVal, String newVal) -> {
      this.findUserIdSuggestions(newVal);
    };
    this.userId.getFieldTextProperty().addListener(_function_1);
  }
  
  public void commitRename() {
    String _text = this.userId.getText();
    String _plus = ("Renaming to " + _text);
    StudentDetails.logger.info(_plus);
    this.currentItem.setStudentName(this.userId.getText());
    this.controller.getStudentList().updateInModel(this.currentItem);
  }
  
  public void findUserIdSuggestions(final String start) {
    StudentDetails.logger.info("Changing");
    List<String> l = this.controller.getStudentsSuggestedIds(start);
    this.userId.showSuggestion(l);
  }
  
  /**
   * def findLastNameSuggestions(String start) {
   * logger.info("Changing")
   * var l = controller.getStudentsSuggestedLastNames(start)
   * lastName.showSuggestion(l)
   * }
   * 
   * def findFirstNameSuggestions(String start) {
   * logger.info("Changing")
   * var l = controller.getStudentsSuggestedFirstNames(start)
   * firstName.showSuggestion(l)
   * }
   */
  public void display(final StudentItemGraduation item) {
    this.setVisible(true);
    this.currentItem = item;
    this.setUserId();
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
  private void setUserId() {
    this.userId.setText(this.currentItem.getStudentName());
  }
  
  /**
   * private def setLastName() {
   * lastName.text = currentItem.studentName
   * }
   * 
   * private def setFirstName() {
   * firstName.text = currentItem.studentName
   * }
   */
  private void setId() {
    int _studentId = this.currentItem.getStudentId();
    String _plus = (Integer.valueOf(_studentId) + "");
    this.idLabel.setText(_plus);
  }
}
