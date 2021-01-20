package fr.istic.tools.scanexam.api

import fr.istic.tools.scanexam.api.ExamReader
import fr.istic.tools.scanexam.core.*;

interface ExamWriter extends ExamReader {
	
	def void addQuestion(Question q, Rectangle r)
	
	def void removeQuestion(int index)
	
}