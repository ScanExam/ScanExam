package fr.istic.tools.scanexam.presenter
import fr.istic.tools.scanexam.view.Adapter
import fr.istic.tools.scanexam.services.*

class PresenterLinker 
{
	
	def void linkEditorPresenter(Adapter adapter)
	{
		val service = new ExamEditionService;
		val presenter = new PresenterVueCreation(adapter,service);
		adapter.setPresenter(presenter);
	}
	
	def void linkGraduationPresenter(Adapter adapter)
	{
		val service = new ExamGraduationService;
		val presenter = new PresenterVueCorrection(adapter,service);
		adapter.setPresenter(presenter);
	}
	
}
