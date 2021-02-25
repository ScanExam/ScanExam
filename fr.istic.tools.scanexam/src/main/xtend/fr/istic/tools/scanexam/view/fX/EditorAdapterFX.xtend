package fr.istic.tools.scanexam.view.fX

import fr.istic.tools.scanexam.presenter.EditorPresenter
import fr.istic.tools.scanexam.view.EditorAdapter
import javafx.scene.image.Image
import java.io.File

class EditorAdapterFX implements EditorAdapter {
	
	EditorPresenter presenter;
	ControllerFXCreator controller;
	override setPresenter(EditorPresenter presenter) {
		this.presenter = presenter
	}
	
	
	def void loadPdf(File file) { //tells the pdf presenter to load the pdf pointer by file
		presenter.presenterPdf.loadPDF(file);
	}
	
	def Image pdfPage(int index) { //fech pdf from presenter and converts to javafx object 
		var imageStream = presenter.presenterPdf.getPage(index)
		return new Image(imageStream);
	}
	
	def void addBox(Box box) { //adds a box to the model via the presenter
		
	}
	
	def void removeBox(Box box) { //removes a box from the model via the presenter
		
	}
	
	def void updateBox(Box box) {//updates a box in the model via the presenter
		
	}
	
	def void setControllerFXCreator(ControllerFXCreator controller) {
		this.controller = controller;
	}
}