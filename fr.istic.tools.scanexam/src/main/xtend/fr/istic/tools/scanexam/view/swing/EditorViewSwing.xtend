package fr.istic.tools.scanexam.view.swing

import java.awt.BorderLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JPanel
import javax.swing.JSplitPane
import javax.swing.SwingConstants
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
	var JPanel contentPane
	
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

	/* Panel gauche */
	var JPanel pnlLeft

	/* Label de sélection */
	var JLabel lblThing

	/* ComboBox de box thing */
	var JComboBox<String> cmbBxThing

	/* Label de sélection d'examen vide */
	var JLabel lblBlankExam

	/* ComboBox d'examen vide */
	var JComboBox<String> cmbBxBlankExam
	
	/* Panel principal à gauche */
	var JPanel pnlLeftMain
	
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
		
		contentPane = new JPanel()
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5))
		window. setContentPane(contentPane)
		contentPane.setLayout(new BorderLayout(0, 0))
		
		pnlButtons = new JPanel()
		contentPane.add(pnlButtons, BorderLayout::NORTH)
		pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout::X_AXIS))
		
		btnQuestionArea = new JButton("Question area")
		pnlButtons.add(btnQuestionArea)
		
		btnIdArea = new JButton("ID area")
		pnlButtons.add(btnIdArea)
		
		btnQrArea = new JButton("QR area")
		pnlButtons.add(btnQrArea)
		
		var JSplitPane splitPane = new JSplitPane()
		splitPane.setOrientation(JSplitPane::VERTICAL_SPLIT)
		contentPane.add(splitPane, BorderLayout::WEST)
		
		pnlLeft = new JPanel()
		splitPane.setLeftComponent(pnlLeft)
		
		var GridBagLayout gblPnlLeft = new GridBagLayout()
		gblPnlLeft.columnWidths = (#[100, 0] as int[])
		gblPnlLeft.rowHeights = (#[0, 0, 0, 22, 0] as int[])
		gblPnlLeft.columnWeights = (#[0.0, Double::MIN_VALUE] as double[])
		gblPnlLeft.rowWeights = (#[0.0, 0.0, 0.0, 0.0, Double::MIN_VALUE] as double[])
		pnlLeft.setLayout(gblPnlLeft)
		
		lblThing = new JLabel("Selected Thing :")
		lblThing.setHorizontalAlignment(SwingConstants::LEFT)
		
		var GridBagConstraints gbcPnlLeft = new GridBagConstraints()
		gbcPnlLeft.fill = GridBagConstraints::BOTH
		gbcPnlLeft.insets = new Insets(0, 0, 5, 0)
		gbcPnlLeft.gridx = 0
		gbcPnlLeft.gridy = 0
		pnlLeft.add(lblThing, gbcPnlLeft)
		
		cmbBxThing = new JComboBox()
		
		var GridBagConstraints gbcCmbBxThing = new GridBagConstraints()
		gbcCmbBxThing.fill = GridBagConstraints::BOTH
		gbcCmbBxThing.insets = new Insets(0, 0, 5, 0)
		gbcCmbBxThing.gridx = 0
		gbcCmbBxThing.gridy = 1
		pnlLeft.add(cmbBxThing, gbcCmbBxThing)
		
		lblBlankExam = new JLabel("Selected Blank Exam :")
		lblBlankExam.setHorizontalAlignment(SwingConstants::LEFT)
		
		var GridBagConstraints gbcLblBlankExam = new GridBagConstraints()
		gbcLblBlankExam.fill = GridBagConstraints::BOTH
		gbcLblBlankExam.insets = new Insets(0, 0, 5, 0)
		gbcLblBlankExam.gridx = 0
		gbcLblBlankExam.gridy = 2
		pnlLeft.add(lblBlankExam, gbcLblBlankExam)
		
		cmbBxBlankExam = new JComboBox()
		
		var GridBagConstraints gbcCmbBxBlankExam = new GridBagConstraints()
		gbcCmbBxBlankExam.fill = GridBagConstraints::BOTH
		gbcCmbBxBlankExam.gridx = 0
		gbcCmbBxBlankExam.gridy = 3
		pnlLeft.add(cmbBxBlankExam, gbcCmbBxBlankExam)
		
		pnlLeftMain = new JPanel()
		splitPane.setRightComponent(pnlLeftMain)
		
		pnlPdf = new PdfAndBoxPanel(this.adapterPdfAndBoxPanel)
		contentPane.add(pnlPdf, BorderLayout::CENTER)
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
	
	/**
	 * Envoie le bouton de chargement de pdf
	 * @return mnItemLoad
	 */
	def JMenuItem getMnItemLoad() {
		return mnItemLoad;
	}
	
	def JMenuItem getMnItemClose() {
		return mnItemSave;
	}

}
