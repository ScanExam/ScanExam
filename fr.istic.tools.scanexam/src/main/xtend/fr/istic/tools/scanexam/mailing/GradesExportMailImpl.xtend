package fr.istic.tools.scanexam.mailing

import fr.istic.tools.scanexam.utils.ResourcesUtils
import java.io.BufferedReader
import java.io.InputStreamReader
import fr.istic.tools.scanexam.services.ExamGraduationService

class GradesExportMailImpl implements GradesExportMail {

	ExamGraduationService service

	new(ExamGraduationService serv) {
		service = serv
	}

	override exportGradesMail(String user, String password, String path) {
		val SendMailXtend mailSender = new SendMailXtend()
		mailSender.sender = user
		mailSender.senderPassword = password

		val myMap = newLinkedHashMap()
		val file = ResourcesUtils.getInputStreamResource("mailing/mailMap.csv")
		val BufferedReader reader = new BufferedReader(new InputStreamReader(file))
		while (reader.ready()) {
			val line = reader.readLine();
			myMap.put(line.split(",").get(0), line.split(",").get(1))
		}
		reader.close()

		for (i : 0 ..< service.studentSheets.size) {
			val name = service.studentSheets.get(i).studentName
			val adresse = myMap.get(name)
			mailSender.recipent = adresse
			mailSender.title = "Controle" // TODO Récupérer le nom de l'exam
			mailSender.message = "Note :" + service.studentSheets.get(i).computeGrade // TODO : Détail des notes ? En attendant d'avoir un PDF annoté
			mailSender.pieceJointe = "" // TODO Remplacer par le PDF annoté
			mailSender.sendMailXtend()

		}
	}
}

