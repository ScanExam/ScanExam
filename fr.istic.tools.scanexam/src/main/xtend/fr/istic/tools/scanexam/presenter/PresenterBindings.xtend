package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.services.ExamEditionService
import fr.istic.tools.scanexam.services.ExamGraduationService
import fr.istic.tools.scanexam.view.Adapter

class PresenterBindings 
{
	
	def static void linkEditorPresenter(Adapter<EditorPresenter> adapter)
	{
		val service = new ExamEditionService;
		val presenter = new EditorPresenter(adapter,service);
		adapter.setPresenter(presenter);
	}
	
	def static void linkGraduationPresenter(Adapter<GraduationPresenter> adapter)
	{
		val service = new ExamGraduationService;
		val presenter = new GraduationPresenter(adapter,service);
		adapter.setPresenter(presenter);
	}
	
}
