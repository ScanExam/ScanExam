package fr.istic.tools.scanexam.view.swing

import fr.istic.tools.scanexam.box.BoxList
import fr.istic.tools.scanexam.presenter.EditorPresenter
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import fr.istic.tools.scanexam.view.GraduationAdapter
import fr.istic.tools.scanexam.presenter.GraduationPresenter

/** 
 * Controlleur swing de la fenêtre de création d'examen
 * @author Julien Cochet
 */
class GraduationAdapterSwing implements GraduationAdapter {
	
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/* Presenter de la correction d'exman */
	var GraduationPresenter graduationPresenter
	
	/* Vue de la création d'exman */
	var GraduationViewSwing view
	
	/* Présentateur du pdf */
	var AdapterSwingPdfPanel adapterPdf	
	
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/** 
	 * Constructeur
	 */
	new() {
		var InputStream pdfInput = null//ResourcesUtils.getInputStreamResource("/QRCode/pfo_example.pdf")
		
		adapterPdf = new AdapterSwingPdfPanel(1280, 720, pdfInput)
		view = new GraduationViewSwing(adapterPdf)
		addActionListeners()
		
		view.getWindow().setVisible(true)
	}
	
	/** 
	 * Ajoute les action listeners aux boutons de la vue
	 */
	def private void addActionListeners() {
		view.getMnItemLoad().addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				openFile()
			}
	    });
	}
	
	/**
	 * Ouvre un fichier pdf
	 */
	def void openFile() throws IOException, ClassNotFoundException {
	    var FileNameExtensionFilter filter = new FileNameExtensionFilter("Pdf file(.pdf)", "pdf")
	    var JFileChooser fc = new JFileChooser()
	    fc.setDialogTitle("Open your file")
	   	fc.setFileSelectionMode(JFileChooser.FILES_ONLY)
	    fc.setCurrentDirectory(new File("."))
	    fc.setFileFilter(filter)

	    var result = fc.showOpenDialog(view.getWindow())
	    if (result == JFileChooser.CANCEL_OPTION) {
	        //cancel action
	    } else if (result == JFileChooser.APPROVE_OPTION) {
	        //open file using 
	        var File selectedFile = fc.getSelectedFile()
	        var path = selectedFile.getAbsolutePath()
	        
	        
			var InputStream pdfInput = new FileInputStream(path);
			adapterPdf.setPDF(pdfInput, 0)
			/*
			pdfFileSelected = new File(path)
	        println(pdfFileSelected)
	        */
	    }
	}
	
	override questionNames() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override nextQuestion() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override previousQuestion() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override thisQuestion(int index) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override setPresenter(GraduationPresenter presenter) {
		graduationPresenter = presenter
	}
	
	override getPresenter() {
		presenter
	}
	
}
