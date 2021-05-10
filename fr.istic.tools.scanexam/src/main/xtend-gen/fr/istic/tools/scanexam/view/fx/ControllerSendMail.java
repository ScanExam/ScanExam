package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.export.ExportExamToPdf;
import fr.istic.tools.scanexam.mailing.SendMailTls;
import fr.istic.tools.scanexam.services.api.ServiceGraduation;
import fr.istic.tools.scanexam.view.fx.ControllerWaiting;
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;
import fr.istic.tools.scanexam.view.fx.utils.DialogMessageSender;
import java.io.File;
import java.util.Collection;
import java.util.Map;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Classe pour envoyer les corrigés par mail en JavaFX
 * @author Julien Cochetn Marius Lumbroso, Théo Giraudet
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
  
  private int nbSheetWithoutName = 0;
  
  private Map<String, String> mailMap;
  
  private Collection<StudentSheet> studentSheets;
  
  private ControllerFxGraduation controllerGraduation;
  
  private float globalScale;
  
  /**
   * METHODES
   */
  @FXML
  public void saveAndQuit() {
    final Task<Integer> task = new Task<Integer>() {
      @Override
      protected Integer call() {
        int sent = 0;
        int _size = ControllerSendMail.this.studentSheets.size();
        int _minus = (_size - ControllerSendMail.this.nbSheetWithoutName);
        this.updateProgress(sent, _minus);
        String _translate = LanguageManager.translate("sendMail.progress");
        int _size_1 = ControllerSendMail.this.studentSheets.size();
        int _minus_1 = (_size_1 - ControllerSendMail.this.nbSheetWithoutName);
        this.updateMessage(String.format(_translate, Integer.valueOf(sent), Integer.valueOf(_minus_1)));
        for (final StudentSheet studentSheet : ControllerSendMail.this.studentSheets) {
          {
            final String studentMail = ControllerSendMail.this.mailMap.get(studentSheet.getStudentName());
            if (((studentSheet.getStudentName() != null) && (studentMail != null))) {
              final Pair<String, File> pair = ExportExamToPdf.exportStudentExamToTempPdfWithAnnotations(
                ControllerSendMail.this.controllerGraduation.getPdfManager().getPdfInputStream(), studentSheet, ControllerSendMail.this.globalScale, ControllerSendMail.this.mainPane.getWidth());
              final SendMailTls sender = new SendMailTls();
              sender.sendMail(ControllerSendMail.this.controllerGraduation.getPdfManager().getPdfInputStream(), ControllerSendMail.this.txtFldTitle.getText(), 
                ControllerSendMail.this.htmlEditor.getHtmlText(), pair.getKey(), studentMail, pair.getValue());
              sent++;
              int _size_2 = ControllerSendMail.this.studentSheets.size();
              int _minus_2 = (_size_2 - ControllerSendMail.this.nbSheetWithoutName);
              this.updateProgress(sent, _minus_2);
              String _translate_1 = LanguageManager.translate("sendMail.progress");
              int _size_3 = ControllerSendMail.this.studentSheets.size();
              int _minus_3 = (_size_3 - ControllerSendMail.this.nbSheetWithoutName);
              this.updateMessage(String.format(_translate_1, Integer.valueOf(sent), Integer.valueOf(_minus_3)));
            }
          }
        }
        return Integer.valueOf(sent);
      }
    };
    final Service<Integer> _function = new Service<Integer>() {
      @Override
      protected Task<Integer> createTask() {
        return task;
      }
    };
    final Service<Integer> service = _function;
    final EventHandler<WorkerStateEvent> _function_1 = (WorkerStateEvent e) -> {
      this.onFinish((service.getValue()).intValue());
    };
    service.setOnSucceeded(_function_1);
    service.start();
    Window _window = this.mainPane.getScene().getWindow();
    ControllerWaiting.openWaitingDialog(service.messageProperty(), service.progressProperty(), ((Stage) _window));
  }
  
  private void onFinish(final int sent) {
    DialogMessageSender.sendDialog(
      Alert.AlertType.CONFIRMATION, 
      LanguageManager.translate("sendMail.resultHeader"), 
      LanguageManager.translate("sendMail.resultHeader"), 
      String.format(LanguageManager.translate("sendMail.progress"), Integer.valueOf(sent), Integer.valueOf(this.studentSheets.size())));
    this.quit();
  }
  
  @FXML
  public void quit() {
    Window _window = this.mainPane.getScene().getWindow();
    final Stage stage = ((Stage) _window);
    stage.close();
  }
  
  public void init(final ServiceGraduation service, final ControllerFxGraduation controllerGraduation) {
    this.controllerGraduation = controllerGraduation;
    this.mailMap = service.getStudentInfos();
    this.studentSheets = service.getStudentSheets();
    this.globalScale = service.getGlobalScale();
    int _xifexpression = (int) 0;
    boolean _isEmpty = this.mailMap.isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      final Function1<StudentSheet, Boolean> _function = (StudentSheet x) -> {
        boolean _containsKey = this.mailMap.containsKey(x.getStudentName());
        return Boolean.valueOf((!_containsKey));
      };
      int _size = IterableExtensions.size(IterableExtensions.<StudentSheet>filter(this.studentSheets, _function));
      _xifexpression = ((int) _size);
    } else {
      _xifexpression = (-1);
    }
    this.nbSheetWithoutName = _xifexpression;
    InputOutput.<Integer>println(Integer.valueOf(this.nbSheetWithoutName));
    boolean _isEmpty_1 = this.mailMap.isEmpty();
    if (_isEmpty_1) {
      DialogMessageSender.sendTranslateDialog(
        Alert.AlertType.WARNING, 
        "sendMail.noStudentDataHeader", 
        "sendMail.noStudentDataHeader", 
        "sendMail.noStudentData");
      return;
    } else {
      if ((this.nbSheetWithoutName != 0)) {
        String _translate = LanguageManager.translate("sendMail.noStudentDataHeader");
        String _xifexpression_1 = null;
        if ((this.nbSheetWithoutName > 1)) {
          _xifexpression_1 = String.format(LanguageManager.translate("sendMail.notAllStudent"), 
            Integer.valueOf(this.nbSheetWithoutName));
        } else {
          _xifexpression_1 = LanguageManager.translate("sendMail.notAllStudent1");
        }
        DialogMessageSender.sendDialog(
          Alert.AlertType.WARNING, _translate, _xifexpression_1, 
          null);
      }
    }
  }
}
