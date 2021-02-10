package fr.istic.tools.scanexam.view

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.controller.PdfAndBoxPresenterSwing
import java.awt.BorderLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.io.File
import java.io.IOException
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JPanel
import javax.swing.JSplitPane
import javax.swing.SwingConstants
import javax.swing.border.EmptyBorder
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.JMenuItem
import java.awt.event.ActionListener
import java.awt.event.ActionEvent

/** 
 * Vue swing de la fenêtre de création d'examen
 * @author Julien Cochet
 */
class ExamCreationSwingView {
	
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/* Controlleur liant les controlleurs du Pdf et des boîtes */
	var PdfAndBoxPresenterSwing pdfPresenter
	
	/* Fenêtre de correction d'examen */
	var JFrame window
	
	/* Panel père */
	var JPanel contentPane
	
	/* Barre de menu de la fenêtre */
	var JMenuBar menuBar
		
	/* Menu fichier de la bare de menu de la fenêtre */
	var JMenu mnFile
	
	var JMenuItem mnItemLoad

	/* Menu édition de la bare de menu de la fenêtre */
	var JMenu mnEdit 

	/* Menu aide de la bare de menu de la fenêtre */
	var JMenu mnHelp

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
	
	var File pdfFileSelected


	// ----------------------------------------------------------------------------------------------------
	/** 
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/** 
	 * Constructeur
	 */
	new(PdfAndBoxPresenterSwing pdfPresenter) {
		this.pdfPresenter = pdfPresenter
		initialize()
	}

	/** 
	 * Initialise la fenêtre
	 */
	def private void initialize() {
		window = new JFrame(LanguageManager.translate("title.ScanExam"))
		window.setBounds(100, 100, 1280, 720)
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
		
		pdfFileSelected = new File("")
		
		menuBar = new JMenuBar()
		window.setJMenuBar(menuBar)
		
		mnFile = new JMenu("File")
		mnItemLoad = new JMenuItem("Load")
		mnItemLoad.addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				openFile()
			}
	    });
	    mnFile.add(mnItemLoad)
		menuBar.add(mnFile)
		
		mnEdit = new JMenu("Edit")
		menuBar.add(mnEdit)
		
		mnHelp = new JMenu("Help")
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
		
		pnlPdf = new PdfAndBoxPanel(this.pdfPresenter)
		contentPane.add(pnlPdf, BorderLayout::CENTER)
	}
	

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * GETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def JFrame getWindow() {
		return window;
	}
	
	def void openFile() throws IOException, ClassNotFoundException {
	    var FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "Pdf file(.pdf)", "pdf")
	    var JFileChooser fc = new JFileChooser()
	    fc.setDialogTitle("Open your file")
	   	fc.setFileSelectionMode(JFileChooser.FILES_ONLY)
	    fc.setCurrentDirectory(new File("."))
	    fc.setFileFilter(filter)

	    var result = fc.showOpenDialog(window)
	    if (result == JFileChooser.CANCEL_OPTION) {
	        //cancel action
	    } else if (result == JFileChooser.APPROVE_OPTION) {
	        //open file using 
	        var File selectedFile = fc.getSelectedFile()
	        var path = selectedFile.getAbsolutePath()

	        pdfFileSelected = new File(path)
	        //Desktop.getDesktop().open(myFile);
	        
	        //pasController.setPdfPath(path);
	        //SwingWindowController.setPdfPath(path);
	        //PdfAndSelectionController.pdfPath = path;
	        //frame.invalidate();
	        //frame.validate();
	        //frame.repaint();
	        //System.out.println(SwingWindowController.pdfPath);
	        
	        //PdfAndSelectionView.s = "boubou";
	        println(pdfFileSelected)
	        //pasView.validate();
	        //pasView.repaint();
	        //System.out.println(PdfAndSelectionView.s);

	    }
	}

}
