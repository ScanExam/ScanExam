package fr.istic.tools.scanexam.presenter


/** 
 * Interface des presenters
 * @author Julien Cochet
 */
interface Presenter {
	
	def PresenterPdf getPresenterPdf()
	def PresenterGradeScale getPresenterMarkingScheme()
}
