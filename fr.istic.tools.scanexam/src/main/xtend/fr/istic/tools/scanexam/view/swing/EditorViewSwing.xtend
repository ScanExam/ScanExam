package fr.istic.tools.scanexam.view.swing

import java.awt.BorderLayout
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

import static fr.istic.tools.scanexam.config.LanguageManager.*

/** 
 * Vue swing de la fenêtre de création d'examen
 * @author Julien Cochet
 */
class EditorViewSwing {
	
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/* Controlleur liant les controlleurs du Pdf et des boîtes */
	var AdapterSwingPdfAndBoxPanel adapterPdfAndBoxPanel
	
	/* Fenêtre de correction d'examen */
	var JFrame window
	
	/* Panel père */
	var JPanel pnlMain
	
	/* Barre de menu de la fenêtre */
	var JMenuBar menuBar
		
	/* Menu fichier de la bare de menu de la fenêtre */
	var JMenu mnFile
	
	/* Bouton pour créer un examen */
	var JMenuItem mnItemNew
	
	/* Bouton pour sauver un examen */
	var JMenuItem mnItemSave
	
	/* Bouton pour charger un examen */
	var JMenuItem mnItemLoad
	
	/* Bouton pour fermer un examen */
	var JMenuItem mnItemClose

	/* Menu édition de la bare de menu de la fenêtre */
	var JMenu mnEdit 

	/* Bouton de suppression */
	var JMenuItem mnItemDelete

	/* Menu aide de la bare de menu de la fenêtre */
	var JMenu mnHelp
	
	/* Bouton "A propos" */
	var JMenuItem mnItemAbout

	/* Panel des boutons principaux */
	var JPanel pnlButtons

	/* Bouton "Question" */
	var JButton btnQuestionArea

	/* Bouton "ID" */
	var JButton btnIdArea

	/* Bouton "QR" */
	var JButton btnQrArea
	
	/* Bouton de mouvement de caméra */
	var JButton btnMoveCam
	
	/* Panel de navigation entre les questions */
	var JPanel pnlQst
	
	/* Label "Questions :" */
	var JLabel lblQst
	
	/* Liste des questions */
	var JList<String> listQst
	
	/* Panel pour les pages */
	var JPanel pnlPage
	
	/* Label "Pages :" */
	var JLabel lblPage
	
	/* Liste des pages */
	var JComboBox<Integer> cmbBxPage
	
	/* Panel de navigation entre les pages*/
	var JPanel pnlNavPage
	
	/* Bouton page précédente */
	var JButton btnPrev
	
	/* Numéro de la page actuelle */
	var JLabel lblNumPage
	
	/* Bouton page suivante */
	var JButton btnNext
	
	/* Panel principal présentant la copie */
	var JPanel pnlPdf


	// ----------------------------------------------------------------------------------------------------
	/** 
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/** 
	 * Constructeur
	 */
	new(AdapterSwingPdfAndBoxPanel adapterPdfAndBoxPanel) {
		this.adapterPdfAndBoxPanel = adapterPdfAndBoxPanel
		initialize()
	}

	/** 
	 * Initialise la fenêtre
	 */
	def private void initialize() {
		window = new JFrame(translate("scanexam"))
		window.setBounds(100, 100, 1280, 720)
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
		
		menuBar = new JMenuBar()
		window.setJMenuBar(menuBar)
		
		mnFile = new JMenu(translate("menu.file"))
		
		mnItemNew = new JMenuItem(translate("menu.file.new"))
	    mnFile.add(mnItemNew)
	    
	    mnFile.addSeparator()
	    
		mnItemSave = new JMenuItem(translate("menu.file.save"))
	    mnFile.add(mnItemSave)
	    
		mnItemLoad = new JMenuItem(translate("menu.file.load"))
	    mnFile.add(mnItemLoad)
	    
	    mnFile.addSeparator()
	    
		mnItemClose = new JMenuItem(translate("menu.file.close"))
	    mnFile.add(mnItemClose)
	    
		menuBar.add(mnFile)
		
		mnEdit = new JMenu(translate("menu.edit"))
		
		mnItemDelete = new JMenuItem(translate("menu.edit.delete"))
	    mnEdit.add(mnItemDelete)
	    
		menuBar.add(mnEdit)
		
		mnHelp = new JMenu(translate("menu.help"))
		
		mnItemAbout = new JMenuItem(translate("menu.help.about"))
	    mnHelp.add(mnItemAbout)
	    
		menuBar.add(mnHelp)
		
		pnlMain = new JPanel()
		pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5))
		window. setContentPane(pnlMain)
		pnlMain.setLayout(new BorderLayout(0, 0))
		
		pnlButtons = new JPanel()
		pnlMain.add(pnlButtons, BorderLayout::NORTH)
		pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout::X_AXIS))
		
		btnQuestionArea = new JButton("Question area")
		pnlButtons.add(btnQuestionArea)
		
		btnIdArea = new JButton("ID area")
		pnlButtons.add(btnIdArea)
		
		btnQrArea = new JButton("QR area")
		pnlButtons.add(btnQrArea)
		
		btnMoveCam = new JButton("Move Camera")
		pnlButtons.add(btnMoveCam)
		
		pnlQst = new JPanel()
		pnlMain.add(pnlQst, BorderLayout.WEST)
		pnlQst.setLayout(new BorderLayout(0, 0))
		
		lblQst = new JLabel("Questions:")
		pnlQst.add(lblQst, BorderLayout.NORTH)
		
		listQst = new JList()
		pnlQst.add(listQst, BorderLayout.CENTER)
		
		pnlPage = new JPanel()
		pnlQst.add(pnlPage, BorderLayout.SOUTH)
		pnlPage.setLayout(new BorderLayout(0, 0))
		
		lblPage = new JLabel("Pages :")
		pnlPage.add(lblPage, BorderLayout.NORTH)
		
		cmbBxPage = new JComboBox()
		pnlPage.add(cmbBxPage, BorderLayout.CENTER)
		
		pnlNavPage = new JPanel()
		pnlPage.add(pnlNavPage, BorderLayout.SOUTH)
		pnlNavPage.setLayout(new BorderLayout(0, 0))
		
		btnPrev = new JButton("Prev")
		pnlNavPage.add(btnPrev, BorderLayout.WEST)
		
		lblNumPage = new JLabel(".")
		pnlNavPage.add(lblNumPage, BorderLayout.CENTER)
		
		btnNext = new JButton("Next")
		pnlNavPage.add(btnNext, BorderLayout.EAST)
		
		pnlPdf = new PdfAndBoxPanel(this.adapterPdfAndBoxPanel)
		pnlMain.add(pnlPdf, BorderLayout::CENTER)
	}
	

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * GETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Envoie la fenêtre principale
	 * @return window
	 */
	def JFrame getWindow() {
		return window;
	}
	
	def JMenuItem getMnItemNew() {
		return mnItemNew;
	}
	
	def JMenuItem getMnItemSave() {
		return mnItemSave;
	}
	
	def JMenuItem getMnItemLoad() {
		return mnItemLoad;
	}
	
	def JMenuItem getMnItemClose() {
		return mnItemClose;
	}
	
	def JMenuItem getMnItemDelete() {
		return mnItemDelete;
	}
	
	def JMenuItem getMnItemAbout() {
		return mnItemAbout;
	}
	
	def JButton getBtnQuestionArea() {
		return btnQuestionArea
	}
	
	def JButton getBtnIdArea() {
		return btnIdArea
	}
	
	def JButton getBtnQrArea() {
		return btnQrArea
	}
	
	def JButton getBtnMoveCam() {
		return btnMoveCam
	}
	
	def JButton getBtnPrev() {
		return btnPrev
	}
	
	def JButton getBtnNext() {
		return btnNext
	}
	
	def JComboBox<Integer> getCmbBxPage() {
		return cmbBxPage
	}
	
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * SETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Met à jour le numéro de page affiché
	 * @param crtPage page actuelle
	 */
	def void setCurrentPage(int crtPage) {
		lblNumPage.text = (crtPage + 1).toString
	}
	
}
