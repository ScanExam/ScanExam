package fr.istic.tools.scanexam.export

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import fr.istic.tools.scanexam.services.ExamGraduationService
import java.io.FileOutputStream
import java.io.IOException
import java.util.Collection
import fr.istic.tools.scanexam.core.StudentSheet

class GradesExportImpl implements GradesExport {
    ExamGraduationService service
    
    new(ExamGraduationService serv){
        service = serv
    }
    
    
    /**
     * Méthode qui créer un fichier Excel et qui le remplit avec les noms des étudiants et leurs notes
     */
    override exportGrades() {
        exportGradesPrivate(service.studentSheets, service.examName)
        
    }
    
    def void exportGradesPrivate(Collection<StudentSheet> studentSheets, String examName){
        val XSSFWorkbook workbook = new XSSFWorkbook()
        val XSSFSheet sheet = workbook.createSheet("export_grades")
        
        var int rowCount = 0
        val Row row1 = sheet.createRow(rowCount)
        val Cell name = row1.createCell(0)
        name.cellValue = "Nom"
        rowCount++
            
        val Cell grade = row1.createCell(1)
        grade.cellValue = "Note"
        
        for(i : 0 ..< studentSheets.size){
            val Row row = sheet.createRow(rowCount)
            
            val Cell cellName = row.createCell(0)
            cellName.cellValue = studentSheets.get(i).studentName
            
            val Cell cellGrade = row.createCell(1)
            cellGrade.cellValue = studentSheets.get(i).computeGrade
            rowCount++;
        }
        
        try{
            val FileOutputStream outStream = new FileOutputStream(examName +".xlsx")
            workbook.write(outStream)
            
        }
        catch(IOException e){
            e.printStackTrace
        }
    }
    
}