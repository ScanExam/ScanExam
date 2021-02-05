package fr.istic.tools.scanexam.sessions

import java.io.File
import fr.istic.tools.scanexam.core.StudentSheet
import java.util.Set

class CorrectionSession extends Session
{
	int currentSheetIndex;
	
	int currentQuestionIndex;
	
	Set<StudentSheet> studentSheets;
	
	Set<StudentSheet> visibleSheets;
	
	override save() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override open(File xmiFile) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
}