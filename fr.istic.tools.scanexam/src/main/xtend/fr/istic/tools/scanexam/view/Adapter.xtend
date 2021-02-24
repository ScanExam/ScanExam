package fr.istic.tools.scanexam.view

import fr.istic.tools.scanexam.presenter.Presenter

/** 
 * Interface d'adaptateur général
 * @author Julien Cochet
 */
interface Adapter<T extends Presenter> {
	
	def void setPresenter(T presenter);
	
	
	
}
