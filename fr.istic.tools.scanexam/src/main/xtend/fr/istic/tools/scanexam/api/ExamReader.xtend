package fr.istic.tools.scanexam.api

import fr.istic.tools.scanexam.core.*;
import java.util.List

interface ExamReader {
	
	def int getId()
	
	def String getName()
	
	def Question getQuestion(int index)
	
	def List<Question> getQuestions()
	
	def Rectangle getQuestionZone(int index)
}