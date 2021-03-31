package fr.istic.tools.scanexam.export

import java.io.File
import org.apache.pdfbox.pdmodel.PDDocument
import fr.istic.tools.scanexam.core.StudentSheet
import java.io.InputStream
import org.apache.pdfbox.pdmodel.common.PDStream
import java.io.OutputStream
import java.util.Collection
import java.util.stream.Collectors
import fr.istic.tools.scanexam.services.Service

class ExportExamToPdf {
	
	static Service service
	 
	new (Service serv){
	 	service = serv
	 }

	def static exportToPdf(PDDocument pdf,StudentSheet sheet, File outputPdfFile){
		
		if(outputPdfFile.exists){
			return null;
		}
		
		var PDDocument document = new PDDocument();
		
		for(i :sheet.posPage){
			document.addPage(pdf.getPage(i));	
		}
		
		document.save(outputPdfFile);
		document.close;
	}	
	
	def static InputStream exportToInputStream(PDDocument pdf, StudentSheet sheet){
        var PDDocument document = new PDDocument();

        for(i :sheet.posPage){
            document.addPage(pdf.getPage(i))
        }
        
        var PDStream ps = new PDStream(document);
        var InputStream is = ps.createInputStream();

        document.close;
        return is;

    }
    
    def static OutputStream exportToOutputStream(PDDocument pdf, StudentSheet sheet){
        var PDDocument document = new PDDocument();

        for(i :sheet.posPage){
            document.addPage(pdf.getPage(i))
        }
        
        var PDStream ps = new PDStream(document);
        var OutputStream is = ps.createOutputStream();

        document.close;
        return is;

    }
    
    
    def static File exportToTempFile(PDDocument pdf,StudentSheet sheet){
		
		var PDDocument document = new PDDocument();
//		
		for(i :sheet.posPage){
			document.addPage(pdf.getPage(i));	
		}
		
		var File file = File.createTempFile(service.examName+sheet.studentName , ".pdf");
		
		document.save(file);
		
		document.close;
		
		return file;
	}	
    
    
    def static Collection<File> exportToCollection(PDDocument pdf,Collection<StudentSheet> sheets){
    	sheets.stream.map(s |exportToTempFile(pdf,s)).collect(Collectors.toList);
    }
    
	
	
}