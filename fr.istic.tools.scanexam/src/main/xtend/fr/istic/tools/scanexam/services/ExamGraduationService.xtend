package fr.istic.tools.scanexam.services

import fr.istic.tools.scanexam.core.CoreFactory
import fr.istic.tools.scanexam.core.StudentSheet
import java.io.File
import java.util.Set
import org.apache.pdfbox.pdmodel.PDDocument

import static fr.istic.tools.scanexam.services.ExamSingleton.*

class ExamGraduationService extends Service
{
	int currentSheetIndex;
	
	int currentQuestionIndex;
	
	Set<StudentSheet> studentSheets;
	
	Set<StudentSheet> visibleSheets;
	
	override save(String path) 
	{
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override open(String xmiFile) 
	{
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override nextPage() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override previousPage() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override void create(File file) 
	{
		document = PDDocument.load(file)

		ExamSingleton.instance = CoreFactory.eINSTANCE.createExam()

		 
		for (i : 0 ..< document.pages.size()) 
		{
			
			ExamSingleton.instance.pages.add(CoreFactory.eINSTANCE.createPage());
		}
	}
	
}
