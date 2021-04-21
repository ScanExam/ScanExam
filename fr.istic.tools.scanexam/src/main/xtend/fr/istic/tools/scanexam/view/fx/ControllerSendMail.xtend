package fr.istic.tools.scanexam.view.fx

import javafx.fxml.FXML
import javafx.scene.control.TextField
import javafx.scene.layout.Pane
import javafx.scene.web.HTMLEditor
import javafx.stage.Stage
import fr.istic.tools.scanexam.mailing.StudentDataManager
import fr.istic.tools.scanexam.services.api.ServiceGraduation
import java.util.Map
import java.util.Collection
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.view.fx.utils.DialogMessageSender
import javafx.scene.control.Alert.AlertType
import fr.istic.tools.scanexam.export.ExportExamToPdf
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation
import fr.istic.tools.scanexam.mailing.SendMailTls
import fr.istic.tools.scanexam.config.ConfigurationManager
import javafx.fxml.Initializable
import java.net.URL
import java.util.ResourceBundle
import java.util.Optional
import fr.istic.tools.scanexam.config.LanguageManager

/**
 * Classe pour envoyer les corrigés par mail en JavaFX
 * @author Julien Cochet
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
	
	ServiceGraduation service
	
	Optional<Map<String,String>> mailMap
	
	Collection<StudentSheet> studentSheets
	
	ControllerFxGraduation controllerGraduation
	// ----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------

	@FXML
	def void saveAndQuit() 
	{
		var sent = 0;
		
		for (i : studentSheets.size >.. 0)
		{
			
			val studentSheet = studentSheets.get(i)
			val studentMail = mailMap.get.get(studentSheet.studentName)
			
			if (studentSheet.studentName === null)
			{
				val pdf = ExportExamToPdf.exportToTempFile(controllerGraduation.pdfManager.pdfInputStream,studentSheet)
			
				SendMailTls.sendMail(ConfigurationManager.instance.email,ConfigurationManager.instance.emailPassword,
				studentMail,txtFldTitle.text,htmlEditor.htmlText,pdf.absolutePath)
				sent++;
			
			}
				
			
		}
		
		DialogMessageSender.sendDialog(AlertType.CONFIRMATION,
				LanguageManager.translate("sendMail.resultHeader"),
				LanguageManager.translate("sendMail.resultHeader"),
				sent + " / " + studentSheets.size + " mail envoyés."
			);
		
		
		quit
	}
	
	@FXML
	def void quit() {
  		val Stage stage = mainPane.scene.window as Stage
  		stage.close();
	}
	
	
	def void init(ServiceGraduation service,ControllerFxGraduation controllerGraduation)
	{
		this.service = service
		this.controllerGraduation = controllerGraduation
		this.mailMap =StudentDataManager.getNameToMailMap()
		this.studentSheets = service.studentSheets
		
		
		val allStudentHasName =  studentSheets.stream.anyMatch(x | x.studentName === null)
	 	
		if (mailMap.empty || allStudentHasName)
		{
			DialogMessageSender.sendDialog(AlertType.WARNING,
				LanguageManager.translate("sendMail.noStudentDataHeader"),
				LanguageManager.translate("sendMail.noStudentDataHeader"),
				LanguageManager.translate("sendMail.noStudentData")
			);
			return
		}

	
	}
	
	
	
}
