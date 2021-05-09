package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.export.ExportExamToPdf
import fr.istic.tools.scanexam.mailing.SendMailTls
import fr.istic.tools.scanexam.services.api.ServiceGraduation
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation
import fr.istic.tools.scanexam.view.fx.utils.DialogMessageSender
import java.util.Collection
import java.util.Map
import javafx.concurrent.Service
import javafx.concurrent.Task
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.TextField
import javafx.scene.layout.Pane
import javafx.scene.web.HTMLEditor
import javafx.stage.Stage

/**
 * Classe pour envoyer les corrigés par mail en JavaFX
 * @author Julien Cochetn Marius Lumbroso, Théo Giraudet
 */
class ControllerSendMail  {

	/* Pane principale de la vue */
	@FXML
	public Pane mainPane
	
	/* Champ de texte du titre */
	@FXML
	public TextField txtFldTitle
	
	/* Champ de texte du mail */
	@FXML
	public HTMLEditor htmlEditor
	
	int nbSheetWithoutName = 0
	
	Map<String,String> mailMap
	
	Collection<StudentSheet> studentSheets
	
	ControllerFxGraduation controllerGraduation
	
	float globalScale
	// ----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------

	@FXML
	def void saveAndQuit() {

	val Task<Integer> task = new Task<Integer>() {
			protected override Integer call() {
				var sent = 0;
				updateProgress(sent, studentSheets.size - nbSheetWithoutName)
				updateMessage(String.format(LanguageManager.translate("sendMail.progress"), sent, studentSheets.size - nbSheetWithoutName))
				for (studentSheet : studentSheets) {

					val studentMail = mailMap.get(studentSheet.studentName)

					if (studentSheet.studentName !== null && studentMail !== null) {
						val pair = ExportExamToPdf.exportStudentExamToTempPdfWithAnnotations(
							controllerGraduation.pdfManager.pdfInputStream, studentSheet, globalScale, mainPane.width)
						val sender = new SendMailTls
						sender.sendMail(controllerGraduation.pdfManager.pdfInputStream, txtFldTitle.text,
							htmlEditor.htmlText, pair.key, studentMail, pair.value)
						sent++;
						updateProgress(sent, studentSheets.size - nbSheetWithoutName)
						updateMessage(String.format(LanguageManager.translate("sendMail.progress"), sent, studentSheets.size - nbSheetWithoutName))
					}
				}
				return sent
			}
		}
		
		val Service<Integer> service = [task]
		service.onSucceeded = [e | onFinish(service.value)]
		service.start
		ControllerWaiting.openWaitingDialog(service.messageProperty, service.progressProperty, mainPane.getScene().getWindow() as Stage)
	}
	
	private def onFinish(int sent) {
		DialogMessageSender.sendDialog(
			AlertType.CONFIRMATION,
			LanguageManager.translate("sendMail.resultHeader"),
			LanguageManager.translate("sendMail.resultHeader"),
			String.format(LanguageManager.translate("sendMail.progress"), sent, studentSheets.size)
		);
		
		quit
	}
	
	@FXML
	def void quit() {
  		val Stage stage = mainPane.scene.window as Stage
  		stage.close();
	}
	
	
	def void init(ServiceGraduation service, ControllerFxGraduation controllerGraduation) {
		this.controllerGraduation = controllerGraduation
		this.mailMap = service.studentInfos
		this.studentSheets = service.studentSheets
		this.globalScale = service.getGlobalScale

		nbSheetWithoutName = if (!mailMap.empty)
			studentSheets.filter(x|!mailMap.containsKey(x.studentName)).size as int
		else
			-1

		if (mailMap.empty) {
			DialogMessageSender.sendTranslateDialog(
				AlertType.WARNING,
				"sendMail.noStudentDataHeader",
				"sendMail.noStudentDataHeader",
				"sendMail.noStudentData"
			);
			return
		} else if (nbSheetWithoutName != 0) {
			DialogMessageSender.sendDialog(
				AlertType.WARNING,
				LanguageManager.translate("sendMail.noStudentDataHeader"),
				nbSheetWithoutName > 1 ? String.format(LanguageManager.translate("sendMail.notAllStudent"),
					nbSheetWithoutName) : LanguageManager.translate("sendMail.notAllStudent1"),
				null
			)
		}
	}
	
	
	
}
