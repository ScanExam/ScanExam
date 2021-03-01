package fr.istic.tools.scanexam.presenter

import java.io.InputStream
import java.io.File
import java.util.Objects
import fr.istic.tools.scanexam.services.ExamEditionService

/** 
 * Controlleur du pdf
 * @author Julien Cochet
 */
abstract class PresenterPdf {
	/**
	 * presenter used for the edition of an exam
	 * @author Benjamin Danlos
	 */
	EditorPresenter editorPresenter
	
	/**
	 * presenter used for the graduation of student sheets of an exam
	 * @author Benjamin Danlos
	 */
	GraduationPresenter graduationPresenter
	
	/**
	 * Association with the model via the Service API
	 */
	private ExamEditionService service;
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
    new(EditorPresenter presE, GraduationPresenter presG){
    	Objects.requireNonNull(presE)
    	Objects.requireNonNull(presG)
    	this.editorPresenter = presE
    	this.graduationPresenter = presG
    }
    /**
     * set {@link EditorPresenter} for the association 
     * @param {@link EditorPresenter} presenter to make the association with
     * @author Benjamin Danlos
     */
    def setEditorPresenter(EditorPresenter p){
    	Objects.requireNonNull(p)
    	this.editorPresenter = p
    }
    
    /**
     * set {@link GraduationPresenter} for the association 
     * @param {@link GraduationPresenter} presenter to make the association with
     * @author Benjamin Danlos
     */
    def setGraduationPresenter(GraduationPresenter p){
    	Objects.requireNonNull(p)
    	this.graduationPresenter = p
    }
    /**
     * returns the current editor presenter associated
     * @return {@link EditorPresenter}
     * @author Benjamin Danlos
     */
    def getEditorPresenter(){
    	this.editorPresenter
    }
    
    /**
     * returns the current graduation presenter associated
     * @return {@link GraduationPresenter}
     * @author Benjamin Danlos
     */
    def getGraduationPresenter(){
    	this.graduationPresenter
    }
    
    /** 
	 * Constructeur
	 * @param width Largeur de la fenêtre
	 * @param height Hauteur de la fenêtre
	 * @param pdfPath Chemin vers le pdf
	 */
	new(int width, int height, InputStream pdfInput) {
		this.width = width
		this.height = height
		this.pdfInput = pdfInput
	}
	
	
	/**
	 * tells the api to change the working pdf in the model
	 * @param {@link File} path to the pdf file to be loaded
     * @author Benjamin Danlos
	 */
	def void loadPDF(File file) {
		Objects.requireNonNull(file)
		//check if file.path is in pdf format ? //FIXME
		//editorPresenter.getSessionAPI().create(file)
	}
	
	/**
	 * get the page number i from the pdf loaded in the model. Use getContents() on the return to get an InputStream if any
	 * @param i the page number
	 * @throws IllegalArgumentExcpetion if i<0 or document has less pages than i
	 * @return {@link PDPage} page of the document
     * @author Benjamin Danlos
	 */
	def getPage(int i){
		/*var pdf = editorPresenter.getSessionAPI().document //FIXME
		//if pdf === null ?
	 	//check if i is in the number of pages of the document
	 	if(i<0){
	 		throw new IllegalArgumentException("i can't be negative(" + i + ")")
	 	}
	 	if(pdf.getNumberOfPages() < i){
	 		throw new IllegalArgumentException("i can't be greater than the number of pages in the document (" + i + ")")
	 	}
	 	pdf.getPage(i) */
	}
	
	/*def InputStream getPage(int index) {
		return null;//TODO
	}*/
	/**
	 * returns all the pages in the pdf loaded in the document
	 * @return {@link PDPageTree} the pages of the document
     * @author Benjamin Danlos
	 */
	def getPages(){
		//if pdf === null ?
		// editorPresenter.getService().document.getPages() //FIXME
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
