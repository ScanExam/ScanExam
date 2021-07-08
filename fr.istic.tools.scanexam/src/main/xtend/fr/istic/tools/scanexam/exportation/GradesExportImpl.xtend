package fr.istic.tools.scanexam.exportation

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.core.StudentSheet
import java.io.FileOutputStream
import java.io.IOException
import java.util.Collection
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File

class GradesExportImpl implements GradesExport {

	/**
	 * Méthode qui créer un fichier Excel et qui le remplit avec les noms des étudiants et leurs notes
	 */
	override void exportGrades(Collection<StudentSheet> studentSheets, File file) {
		val XSSFWorkbook workbook = new XSSFWorkbook()
		val XSSFSheet sheet = workbook.createSheet("export_grades")

		var int rowCount = 0
		val Row row1 = sheet.createRow(rowCount)
		val Cell name = row1.createCell(0)
		name.cellValue = LanguageManager.translate("exportExcel.name")
		rowCount++

		val Cell grade = row1.createCell(1)
		grade.cellValue = LanguageManager.translate("exportExcel.grade")

		for (i : 0 ..< studentSheets.size) {
			val Row row = sheet.createRow(rowCount)

			val Cell cellName = row.createCell(0)
			cellName.cellValue = studentSheets.get(i).sheetName

			val Cell cellGrade = row.createCell(1)
			cellGrade.cellValue = studentSheets.get(i).computeGrade
			rowCount++;
		}

		try {
			val FileOutputStream outStream = new FileOutputStream(file)
			workbook.write(outStream)

		} catch (IOException e) {
			e.printStackTrace
		}
	}

}
