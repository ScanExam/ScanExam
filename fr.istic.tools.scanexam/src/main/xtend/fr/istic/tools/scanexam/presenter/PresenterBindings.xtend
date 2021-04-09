package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.view.Adapter
import fr.istic.tools.scanexam.services.ServiceEdition
import fr.istic.tools.scanexam.services.ServiceGraduation

class PresenterBindings 
{
	
	def static void linkEditorPresenter(Adapter<PresenterEdition> adapter)
	{
		val service = new ServiceEdition;
		val presenter = new PresenterEdition(adapter,service);
		adapter.setPresenter(presenter);
	}
	
	def static void linkGraduationPresenter(Adapter<PresenterGraduation> adapter)
	{
		val service = new ServiceGraduation;
		val presenter = new PresenterGraduation(adapter,service);
		adapter.setPresenter(presenter);
	}
	
}
