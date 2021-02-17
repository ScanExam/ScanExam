package fr.istic.tools.scanexam.view

import fr.istic.tools.scanexam.core.Question
import java.util.List

interface ControllerI {
	def void showQuestion(Question question)
	def void initQuestionNames(List<String> names);
}