package fr.istic.tools.scanexam.controller

import fr.istic.tools.scanexam.view.ExamCreationSwingView
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import fr.istic.tools.scanexam.sessions.CreationSession
import java.io.InputStream
import fr.istic.tools.scanexam.utils.ResourcesUtils
import fr.istic.tools.scanexam.box.BoxList
import java.io.IOException
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.JFileChooser
import java.io.File

/** 
 * Controlleur swing de la fenêtre de création d'examen
 * @author Julien Cochet
 */
class ExamCreationSwingController {
	
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/* Vue de la création d'exman */
	var ExamCreationSwingView view
	
	/* Créateur de session */
	var CreationSession creaSess
	
	var File pdfFileSelected
	
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/** 
	 * Constructeur
	 */
	new() {
		
		pdfFileSelected = new File("")
		var InputStream pdfInput = ResourcesUtils.getInputStreamResource("/viewResources/pfo_example.pdf")
		var PdfAndBoxPresenterSwing pdfPresenteur = new PdfAndBoxPresenterSwing(1280, 720, pdfInput, new BoxList())
		view = new ExamCreationSwingView(pdfPresenteur)
		addActionListeners()
		
		view.getWindow().setVisible(true)
	}
	
	/** 
	 * Ajoute les action listeners aux boutons de la vue
	 */
	def private void addActionListeners() {
		view.getMnItemSession().addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				print("koukou")
			}
	    });
	    
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

			pdfFileSelected = new File(path)
	        println(pdfFileSelected)
	    }
	}
}
