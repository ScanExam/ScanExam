package fr.istic.tools.scanexam.view.swing

import fr.istic.tools.scanexam.launcher.LauncherSwing
import fr.istic.tools.scanexam.presenter.SelectionStateMachine
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import java.io.IOException
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import fr.istic.tools.scanexam.presenter.PresenterEdition
import fr.istic.tools.scanexam.view.AdapterEdition

/** 
 * Controlleur swing de la fenêtre de création d'examen
 * @author Julien Cochet
 */
class EditorAdapterSwing implements AdapterEdition {
	
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/* Presenter de la création d'exman */
	var PresenterEdition editorPresenter
	
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
		view.getMnItemNew().addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				//Actions lorsque le bouton "create" est cliqué
				openPdf()
			}
	    });
	    
		view.getMnItemSave().addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				//Actions lorsque le bouton "save" est cliqué
				saveXmi()
			}
	    });
	    view.getMnItemSwap().addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				swapVerGraduation()
			}
	    });
	    
		view.getMnItemLoad().addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				//Actions lorsque le bouton "load" est cliqué
				loadXmi();
			}
	    });
	    
		view.getMnItemClose().addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				//Actions lorsque le bouton "close" est cliqué
				presenter.close
			}
	    });
	    
	    view.getBtnQuestionArea().addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				// Actions lorsque le bouton "question area" est cliqué
				SelectionStateMachine.setState(SelectionStateMachine.CREATE)
			}
		});

		view.getBtnMoveCam().addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				// Actions lorsque le bouton "move camera" est cliqué
				SelectionStateMachine.setState(SelectionStateMachine.IDLE)
			}
		});
	    
		view.getBtnPrev().addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				//Actions lorsque le bouton "question précédente" est cliqué
				presenter.getPresenterPdf.previousPdfPage()
	        	adapterPdfAndBox.refreshPdf()
	        	view.setCurrentPage(presenter.getPresenterPdf.currentPdfPageNumber)
			}
	    });
	    
		view.getBtnNext().addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				//Actions lorsque le bouton "question suivante" est cliqué
				presenter.getPresenterPdf.nextPdfPage()
	        	adapterPdfAndBox.refreshPdf()
	        	view.setCurrentPage(presenter.getPresenterPdf.currentPdfPageNumber)
			}
	    });
	    
	}
	
	/**
	 * Ouvre un fichier pdf
	 */
	def void openPdf() throws IOException, ClassNotFoundException {
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
	        
	        // Envoie du pdf au service
	        editorPresenter.getPresenterPdf.create(selectedFile)
	        
	        // Mise à jour du pdf affiché
	        adapterPdfAndBox.refreshPdf()
	        
	        // Mise à jour de la liste des pages
			view.cmbBxPage.removeAll
			for (i : 1 ..< editorPresenter.getPresenterPdf.getPdfPageCount) {
				view.cmbBxPage.addItem(i)
			}
			view.cmbBxPage.addActionListener(new ActionListener() {
				override actionPerformed(ActionEvent e) {
					presenter.getPresenterPdf.goToPdfPage(view.cmbBxPage.selectedIndex)
		        	adapterPdfAndBox.refreshPdf()
	        		view.setCurrentPage(presenter.getPresenterPdf.currentPdfPageNumber)
				}
			});
			
	        // Mise à jour du numéro de page
	        view.setCurrentPage(presenter.getPresenterPdf.currentPdfPageNumber)
	    }
	}
	
	/**
	 * Sauvegarde le fichier
	 */
	def void saveXmi() throws IOException, ClassNotFoundException {
	    var FileNameExtensionFilter filter = new FileNameExtensionFilter("Xmi file(.xmi)", "xmi")
		var JFileChooser fc = new JFileChooser()
	    fc.setDialogTitle("Save your file")
	    fc.setCurrentDirectory(new File("."))
	    fc.setFileFilter(filter)
	    
        var int ret = fc.showSaveDialog(null)
        if(ret == JFileChooser.APPROVE_OPTION) { // validation
            presenter.save(fc.getSelectedFile())
        } 
	}
	
	/**
	 * Charge le fichier
	 */
	def void loadXmi() throws IOException, ClassNotFoundException {
	    var FileNameExtensionFilter filter = new FileNameExtensionFilter("Xmi file(.xmi)", "xmi")
		var JFileChooser fc = new JFileChooser()
	    fc.setDialogTitle("Load your file")
	    fc.setCurrentDirectory(new File("."))
	    fc.setFileFilter(filter)
	    
        var int ret = fc.showSaveDialog(null)
        if(ret == JFileChooser.APPROVE_OPTION) { // validation
        	presenter
            presenter.load(fc.getSelectedFile().path)
        } 
	}
	
	/**
	 * Swap vers graduation
	 */
	
	def void swapVerGraduation(){
			view.getWindow().setVisible(false)
			LauncherSwing.swapToGraduator(view)
	}
	
	
	override setPresenter(PresenterEdition presenter) {
		editorPresenter = presenter
		adapterPdfAndBox.presenterPdf = presenter
		adapterPdfAndBox.presenterQst = presenter.presenterQuestionZone
	}
	
	override getPresenter() {
		editorPresenter
	}
	
}
