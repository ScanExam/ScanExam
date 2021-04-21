package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.config.ConfigurationManager;
import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.export.ExportExamToPdf;
import fr.istic.tools.scanexam.mailing.SendMailTls;
import fr.istic.tools.scanexam.mailing.StudentDataManager;
import fr.istic.tools.scanexam.services.api.ServiceGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;
import fr.istic.tools.scanexam.view.fx.utils.DialogMessageSender;
import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

/**
 * Classe pour envoyer les corrigés par mail en JavaFX
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class ControllerSendMail {
  /**
   * Pane principale de la vue
   */
  @FXML
  public Pane mainPane;
  
  /**
   * Champ de texte du titre
   */
  @FXML
  public TextField txtFldTitle;
  
  /**
   * Champ de texte du mail
   */
  @FXML
  public HTMLEditor htmlEditor;
  
  private ServiceGraduation service;
  
  private Optional<Map<String, String>> mailMap;
  
  private Collection<StudentSheet> studentSheets;
  
  private ControllerFxGraduation controllerGraduation;
  
  /**
   * METHODES
   */
  @FXML
  public void saveAndQuit() {
    int _size = this.studentSheets.size();
    ExclusiveRange _greaterThanDoubleDot = new ExclusiveRange(_size, 0, false);
    for (final Integer i : _greaterThanDoubleDot) {
      {
        final StudentSheet studentSheet = ((StudentSheet[])Conversions.unwrapArray(this.studentSheets, StudentSheet.class))[(i).intValue()];
        final String studentMail = this.mailMap.get().get(studentSheet.getStudentName());
        final File pdf = ExportExamToPdf.exportToTempFile(this.controllerGraduation.getPdfManager().getPdfInputStream(), studentSheet);
        SendMailTls.sendMail(ConfigurationManager.instance.getEmail(), ConfigurationManager.instance.getEmailPassword(), studentMail, this.txtFldTitle.getText(), this.htmlEditor.getHtmlText(), pdf.getAbsolutePath());
      }
    }
    this.quit();
  }
  
  @FXML
  public void quit() {
    Window _window = this.mainPane.getScene().getWindow();
    final Stage stage = ((Stage) _window);
    stage.close();
  }
  
  public void init(final ServiceGraduation service, final ControllerFxGraduation controllerGraduation) {
    this.service = service;
    this.mailMap = StudentDataManager.getNameToMailMap();
    this.studentSheets = service.getStudentSheets();
    boolean _isEmpty = this.mailMap.isEmpty();
    if (_isEmpty) {
      DialogMessageSender.sendDialog(Alert.AlertType.WARNING, "Error", LanguageManager.translate("sendMail.dummyDataHeader"), LanguageManager.translate("sendMail.dummyData"));
      return;
    }
    final Predicate<StudentSheet> _function = (StudentSheet x) -> {
      String _studentName = x.getStudentName();
      return (_studentName == null);
    };
    final boolean allStudentHasName = this.studentSheets.stream().anyMatch(_function);
    if ((!allStudentHasName)) {
      DialogMessageSender.sendDialog(Alert.AlertType.WARNING, "Error", LanguageManager.translate("sendMail.dummyDataHeader"), LanguageManager.translate("sendMail.dummyData"));
    }
  }
}
