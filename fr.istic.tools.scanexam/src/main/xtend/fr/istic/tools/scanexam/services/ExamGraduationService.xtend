package fr.istic.tools.scanexam.services


import fr.istic.tools.scanexam.core.StudentSheet
import java.util.Set

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
	
}
