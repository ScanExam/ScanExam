package fr.istic.tools.scanexam.mailing

import java.io.File
import fr.istic.tools.scanexam.core.StudentSheet
import java.util.Collection
import fr.istic.tools.scanexam.core.config.Config
import java.util.logging.Logger
import fr.istic.tools.scanexam.services.ServiceGraduation

class GradesExportMailImpl implements GradesExportMail {

	static ServiceGraduation service

	new(ServiceGraduation serv) {
		service = serv
	}

	static Config instance

	override exportGradesMail(File pdf) {
		exportGradesMail1(pdf, instance.email, instance.emailPassword, service.studentSheets.size, service.examName,
			service.studentSheets);
	}

	override exportGradesMail1(File pdf, String mail, String mdp, int taille, String nameExam,
		Collection<StudentSheet> studentSheetss) {
		val SendMailXtend mailSender = new SendMailXtend()
		for (i : 0 ..< taille) {
			mailSender.recipent = studentSheetss.get(i).studentName
			mailSender.title = nameExam // "Controle" // TODO Récupérer le nom de l'exam : service.examName
			mailSender.message = "Note :" // + service.studentSheets.get(i).computeGrade // TODO : Détail des notes ? En attendant d'avoir un PDF annoté
			mailSender.pieceJointe = pdf.absoluteFile + ".pdf" // TODO Remplacer par le PDF annoté
			mailSender.sendMailXtend()
		}
		Logger.getGlobal.info("Les mails ont été envoyés")
	}

}
