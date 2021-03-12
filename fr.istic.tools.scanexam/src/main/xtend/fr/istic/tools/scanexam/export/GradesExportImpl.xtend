package fr.istic.tools.scanexam.export

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import fr.istic.tools.scanexam.services.ExamGraduationService
import java.io.FileOutputStream
import java.io.IOException
import fr.istic.tools.scanexam.mailing.SendMailXtend
import fr.istic.tools.scanexam.utils.ResourcesUtils
import java.io.BufferedReader
import java.io.InputStreamReader

class GradesExportImpl implements GradesExport {
	ExamGraduationService service
	
	new(ExamGraduationService serv){
		service = serv
	}
	
	/**
	 * Methode qui créer un fichier Excel et qui le remplit avec les noms des étudiants et leurs notes
	 */
	override exportGrades() {
		
		val XSSFWorkbook workbook = new XSSFWorkbook()
		val XSSFSheet sheet = workbook.createSheet("export_grades")
		
		var int rowCount = 0
		println(service.studentSheets)
		val Row row1 = sheet.createRow(rowCount)
		val Cell name = row1.createCell(0)
		name.cellValue = "Nom"
		rowCount++
			
		val Cell grade = row1.createCell(1)
		grade.cellValue = "Note"
		
		for(i : 0 ..< service.studentSheets.size){
			val Row row = sheet.createRow(rowCount)
			
			val Cell cellName = row.createCell(0)
			cellName.cellValue = service.studentSheets.get(i).studentName
			
			val Cell cellGrade = row.createCell(1)
			cellGrade.cellValue = service.studentSheets.get(i).computeGrade
		}
		
		try{
			val FileOutputStream outStream = new FileOutputStream(service.examName +".xlsx")
			workbook.write(outStream)
			
		}
		catch(IOException e){
			e.printStackTrace
		}
	}
	
	
	override exportGradesMail(String user, String password, String path) {
		val SendMailXtend mailSender = new SendMailXtend()
		mailSender.sender = user
		mailSender.senderPassword = password

		val myMap = newLinkedHashMap()
		val file = ResourcesUtils.getInputStreamResource("mailing/mailMap.csv")
		val BufferedReader reader = new BufferedReader(new InputStreamReader(file))
		while(reader.ready()) {
     		val line = reader.readLine();
     		myMap.put(line.split(",").get(0),line.split(",").get(1))
		}
		

		for (i : 0 ..< service.studentSheets.size) {
			val name = service.studentSheets.get(i).studentName
			val adresse = myMap.get(name)
			mailSender.recipent = adresse
			mailSender.title = "Controle" // TODO Récupérer le nom de l'exam
			mailSender.message = "Note :" +  service.studentSheets.get(i).computeGrade //TODO : Détail des notes ? En attendant d'avoir un PDF annoté
			mailSender.pieceJointe = "" //TODO Remplacer par le PDF annoté
			mailSender.sendMailXtend()

		}

	}
	
}