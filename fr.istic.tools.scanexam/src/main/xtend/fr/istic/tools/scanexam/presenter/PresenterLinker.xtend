package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.services.ExamEditionService
import fr.istic.tools.scanexam.services.ExamGraduationService
import fr.istic.tools.scanexam.view.Adapter

class PresenterLinker 
{
	
	def static void linkEditorPresenter(Adapter adapter)
	{
		val service = new ExamEditionService;
		val presenter = new PresenterVueCreation(adapter,service);
		adapter.setPresenter(presenter);
	}
	
	def static void linkGraduationPresenter(Adapter adapter)
	{
		val service = new ExamGraduationService;
		val presenter = new PresenterVueCorrection(adapter,service);
		adapter.setPresenter(presenter);
	}
	
}
