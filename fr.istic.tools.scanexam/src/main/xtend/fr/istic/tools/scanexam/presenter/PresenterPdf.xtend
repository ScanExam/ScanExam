package fr.istic.tools.scanexam.presenter

import java.io.InputStream
import java.io.File
import java.util.Objects
import fr.istic.tools.scanexam.services.ExamEditionService

/** 
 * Controlleur du pdf
 * @author Julien Cochet
 */
class PresenterPdf {
	/**
	 * presenter used for the edition of an exam
	 * @author Benjamin Danlos
	 */
	Presenter presenter
	
	
	/**
	 * Association with the model via the Service API
	 */
	ExamEditionService service;
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/* Largeur de la fenêtre */
	protected int width
	
	/* Hauteur de la fenêtre */
	protected int height
	
	/* InputStream du pdf */
	protected InputStream pdfInput
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * CONSTRUCTEUR
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Constructor 
	 * @author Benjamin Danlos
	 * @param {@link EditorPresenter} (not null)
	 * @param {@link GraduationPresenter} (not null)
	 * Constructs a PDFPresenter object.
	 */
    new(ExamEditionService s, Presenter p){
    	Objects.requireNonNull(s)
    	Objects.requireNonNull(p)
    	this.service = s
    	this.presenter = p
    }
    /**
     * set {@link Presenter} for the association 
     * @param {@link Presenter} presenter to make the association with
     * @author Benjamin Danlos
     */
    def setPresenter(Presenter p){
    	Objects.requireNonNull(p)
    	this.presenter = p
    }
    
    /**
     * returns the current presenter associated
     * @return {@link Presenter}
     * @author Benjamin Danlos
     */
    def getPresenter(){
    	this.presenter
    }
    
  
	def getCurrentPdfPage()
	{
		return service.getCurrentPdfPage
	}
	
	def void choosePdfPage(int pageNumber) {
		
	}
	def void nextPdfPage(){
		service.nextPage
	}
	def void previousPdfPage(){
		service.previousPage
	}
	
	def int getTotalPdfPageNumber(){
		service.pageNumber
	}
	def int getCurrentPdfPageNumber(){
		service.currentPageNumber
	}
	
	def create(File file)
	{
		service.create(file);
	}
}
