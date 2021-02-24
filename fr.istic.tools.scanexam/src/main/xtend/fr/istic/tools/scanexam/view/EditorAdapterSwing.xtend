package fr.istic.tools.scanexam.view

import fr.istic.tools.scanexam.box.BoxList
import fr.istic.tools.scanexam.utils.ResourcesUtils
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import fr.istic.tools.scanexam.presenter.Presenter
import fr.istic.tools.scanexam.presenter.PresenterVueCreation

/** 
 * Controlleur swing de la fenêtre de création d'examen
 * @author Julien Cochet
 */
class EditorAdapterSwing implements EditorAdapter {
	
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/* Presenter de la création d'exman */
	var PresenterVueCreation editorPresenter
	
	/* Vue de la création d'exman */
	var EditorViewSwing view
	
	/* Présentateur du pdf */
	var AdapterSwingPdfAndBoxPanel adapterPdfAndBox	
	
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/** 
	 * Constructeur
	 */
	new() {
		var InputStream pdfInput = ResourcesUtils.getInputStreamResource("/QRCode/pfo_example.pdf")
		
		adapterPdfAndBox = new AdapterSwingPdfAndBoxPanel(1280, 720, pdfInput, new BoxList())
		view = new EditorViewSwing(adapterPdfAndBox)
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
	        
	        
			var InputStream pdfInput = new FileInputStream(path);
			adapterPdfAndBox.setPDF(pdfInput, 0)
			/*
			pdfFileSelected = new File(path)
	        println(pdfFileSelected)
	        */
	    }
	}
	
	override setPresenter(Presenter presenter) {
		editorPresenter = presenter as PresenterVueCreation
	}
	
}
