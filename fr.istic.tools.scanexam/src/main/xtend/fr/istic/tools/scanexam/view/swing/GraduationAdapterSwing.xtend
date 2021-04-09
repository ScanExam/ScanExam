package fr.istic.tools.scanexam.view.swing

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import java.io.IOException
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import fr.istic.tools.scanexam.presenter.PresenterGraduation
import fr.istic.tools.scanexam.view.AdapterGraduation

/** 
 * Controlleur swing de la fenêtre de création d'examen
 * @author Julien Cochet
 */
class GraduationAdapterSwing implements AdapterGraduation {
	
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	/*View de editor */
	var EditionViewSwing viewEdit
	
	/* Presenter de la correction d'exman */
	var PresenterGraduation graduationPresenter
	
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
		adapterPdf = new AdapterSwingPdfPanel(1280, 720, graduationPresenter)
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
	    })
	    
	    view.getBtnDown().addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				view.showContentDown()
			}
		})
	    view.getMnItemSwap().addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				swapToEditor()
			}
	    });
	}
	
	/**
	 * Passe de la correction vers l'édition
	 */
	def void swapToEditor(){
		view.getWindow().setVisible(false)
		viewEdit.getWindow().setVisible(true) 
		//LauncherSwing.swapToEditor()
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
	        graduationPresenter.getPresenterPdf().create(selectedFile)
	    }
	}
	
	/**
	 * Change la vue de l'éditeur
	 */
	def void setViewEditor(EditionViewSwing view){
		this.viewEdit = view
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
	
	override setPresenter(PresenterGraduation presenter) {
		graduationPresenter = presenter
		adapterPdf.presenterPdf = presenter
	}
	
	override getPresenter() {
		presenter
	}
	
}
