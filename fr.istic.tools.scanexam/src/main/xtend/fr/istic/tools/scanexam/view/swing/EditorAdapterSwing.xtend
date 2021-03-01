package fr.istic.tools.scanexam.view.swing

import fr.istic.tools.scanexam.box.BoxList
import fr.istic.tools.scanexam.presenter.EditorPresenter
import fr.istic.tools.scanexam.view.EditorAdapter
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import java.io.IOException
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

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
	var EditorPresenter editorPresenter
	
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
		adapterPdfAndBox = new AdapterSwingPdfAndBoxPanel(1280, 720, editorPresenter, new BoxList())
		view = new EditorViewSwing(adapterPdfAndBox)
		addActionListeners()
		
		view.getWindow().setVisible(true)
	}
	
	/** 
	 * Ajoute les action listeners aux boutons de la vue
	 */
	def private void addActionListeners() {
		view.getMnItemSave().addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				//Actions lorsque le bouton "save" est cliqué
				
			}
	    });
	    
		view.getMnItemLoad().addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				//Actions lorsque le bouton "load" est cliqué
				openFile()
			}
	    });
	    
		view.getMnItemCreate().addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				//Actions lorsque le bouton "create" est cliqué
				
			}
	    });
	    
		view.getMnItemClose().addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				//Actions lorsque le bouton "close" est cliqué
				
			}
	    });
	    
		view.getMnItemSession().addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				//Actions lorsque le bouton "session" est cliqué
				
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
	        editorPresenter.create(selectedFile)
	        adapterPdfAndBox.refreshPdf
	    }
	}
	
	override setPresenter(EditorPresenter presenter) {
		editorPresenter = presenter
		adapterPdfAndBox.presenterPdf = presenter
	}
	
	override getPresenter() {
		presenter
	}
	
}
