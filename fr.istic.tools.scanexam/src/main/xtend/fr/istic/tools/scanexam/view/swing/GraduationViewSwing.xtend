package fr.istic.tools.scanexam.view.swing

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.utils.ResourcesUtils
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.InputStream
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JPanel
import javax.swing.JSplitPane
import javax.swing.JTextPane
import javax.swing.SwingConstants

/** 
 * Vue swing de la fenêtre de correction d'examen
 * @author Julien Cochet
 */
class GraduationViewSwing {

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/* Controlleur liant les controlleurs du Pdf et des boîtes */
	var AdapterSwingPdfPanel pdfPresenter

	/* Fenêtre de correction d'examen */
	var JFrame window

	/* Barre de menu de la fenêtre */
	var JMenuBar menuBar
	/* Menu fichier de la bare de menu de la fenêtre */
	var JMenu mnFile
	/* Bouton pour charger un examen */
	var JMenuItem mnItemLoad
	/* Bouton pour charger de session */
	var JMenuItem mnItemSession
	/* Bouton pour swap*/
	var JMenuItem mnItemSwap

	/* Panel des boutons principaux */
	var JPanel pnlMainBtn
	/* 1er bouton du panel des boutons principaux */
	var JButton btnMain1
	/* 2ème bouton du panel des boutons principaux */
	var JButton btnMain2
	/* Bouton crayon du panel des boutons principaux */
	var JButton btnPen
	/* Bouton gomme du panel des boutons principaux */
	var JButton btnEraser
	/* Bouton tampon du panel des boutons principaux */
	var JButton btnStamp
	/* Bouton épais du panel des boutons principaux */
	var JButton btnThicc
	/* Bouton fin du panel des boutons principaux */
	var JButton btnThinn
	/* 3ème bouton du panel des boutons principaux */
	var JButton btnMain3
	/* 4ème bouton du panel des boutons principaux */
	var JButton btnMain4
	/* 5ème bouton du panel des boutons principaux */
	var JButton btnMain5
	/* Bouton sauver du panel des boutons principaux */
	var JButton btnSave
	/* Bouton charger du panel des boutons principaux */
	var JButton btnLoad
	/* Bouton exporter du panel des boutons principaux */
	var JButton btnExp

	/* Panel de navigation entre les copies */
	var JPanel pnlPaper
	/* Label du numéro de la copie */
	var JLabel lblNbPaper
	/* Liste textuelle des copies */
	var JTextPane txtPnPapers
	/* Bouton copie précédente */
	var JButton btnPrevPaper
	/* Bouton copie suivante */
	var JButton btnNextPaper
	/* Panel pour séparer la navigation entre les copies et la note */
	var JSplitPane spltPnPaper
	/* Panel d'affichage de la note */
	var JPanel pnlNote
	/* Label "note acutelle" */
	var JLabel lblCurrentNote
	/* Label de la note de la copie */
	var JLabel lblNote

	/* Panel principal présentant la copie */
	var JPanel pnlPdf

	/* Panel de navigation entre les questions */
	var JPanel pnlQst
	/* Label de numéro de question */
	var JLabel lblNbQst
	/* Liste textuelle des questions */
	var JTextPane txtPnQst
	/* Panel pour séparer la liste des questions et les boutons de navigation */
	var JSplitPane spltPnQst
	/* Bouton question précédente */
	var JButton btnPrevQst
	/* Bouton question suivante */
	var JButton btnNextQst

	/* Panel pour voir l'énoncé d'une question */
	var JPanel pnlDown
	/* Bouton pour voir l'énoncé d'une question */
	var JButton btnDown
	/* Panel d'énoncé d'une question */
	var JPanel pnlContentDown

	var JSplitPane mainSplitPane
	
	/* Panel d'énoncé d'une question */
		var boolean contentDown
		
	var currentPage = 0;
	
	var totalPage = 20;
	

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * Constructeur
	 */
	new(AdapterSwingPdfPanel pdfPresenter) {
		contentDown = false
		this.pdfPresenter = pdfPresenter
		initialize()
	}

	/** 
	 * Initialise la fenêtre
	 */
	def private void initialize() {
		window = new JFrame(LanguageManager.translate("title.scanexam"))
		window.setBounds(100, 100, 1280, 720)
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE)

		menuBar = new JMenuBar()
		window.setJMenuBar(menuBar)

		mnFile = new JMenu("File")
		menuBar.add(mnFile)

		mnItemLoad = new JMenuItem("Load")
		mnFile.add(mnItemLoad)

		mnItemSession = new JMenuItem("Change session")
		mnFile.add(mnItemSession)
		
		mnItemSwap = new JMenuItem(LanguageManager.translate("menu.file.switchToEditor"))
		mnFile.add(mnItemSwap)
	

		window.getContentPane().setLayout(new BorderLayout(0, 0))
		pnlMainBtn = new JPanel()
		window.getContentPane().add(pnlMainBtn, BorderLayout.NORTH)
		pnlMainBtn.setLayout(new BoxLayout(pnlMainBtn, BoxLayout.X_AXIS))

		btnMain1 = new JButton("Button")
		pnlMainBtn.add(btnMain1)

		btnMain2 = new JButton("Button")
		pnlMainBtn.add(btnMain2)

		var Component hStrtMainBtn1 = Box.createHorizontalStrut(20)
		pnlMainBtn.add(hStrtMainBtn1)

		btnPen = new JButton("Pen")
		pnlMainBtn.add(btnPen)

		btnEraser = new JButton("Eraser")
		pnlMainBtn.add(btnEraser)

		btnStamp = new JButton("Stamp")
		pnlMainBtn.add(btnStamp)

		btnThicc = new JButton("Thicc")
		pnlMainBtn.add(btnThicc)

		btnThinn = new JButton("Thinn")
		pnlMainBtn.add(btnThinn)

		btnMain3 = new JButton("Button")
		pnlMainBtn.add(btnMain3)

		btnMain4 = new JButton("Button")
		pnlMainBtn.add(btnMain4)

		btnMain5 = new JButton("Button")
		pnlMainBtn.add(btnMain5)

		var Component hStrtMainBtn2 = Box.createHorizontalStrut(20)
		pnlMainBtn.add(hStrtMainBtn2)

		btnSave = new JButton("Save")
		pnlMainBtn.add(btnSave)

		btnLoad = new JButton("Load")
		pnlMainBtn.add(btnLoad)

		btnExp = new JButton("Exp")
		pnlMainBtn.add(btnExp)

		pnlPaper = new JPanel()
		window.getContentPane().add(pnlPaper, BorderLayout.WEST)
		pnlPaper.setLayout(new BorderLayout(0, 0))

		lblNbPaper = new JLabel("Copie No.x")
		lblNbPaper.setFont(new Font("Arial", Font.PLAIN, 14))
		lblNbPaper.setHorizontalAlignment(SwingConstants.CENTER)
		pnlPaper.add(lblNbPaper, BorderLayout.NORTH)

		txtPnPapers = new JTextPane()
		pnlPaper.add(txtPnPapers, BorderLayout.CENTER)

		pnlNote = new JPanel()
		pnlPaper.add(pnlNote, BorderLayout.SOUTH)
		pnlNote.setLayout(new BorderLayout(0, 0))

		lblCurrentNote = new JLabel("Note actuelle")
		lblCurrentNote.setFont(new Font("Arial", Font.PLAIN, 14))
		lblCurrentNote.setHorizontalAlignment(SwingConstants.CENTER)
		pnlNote.add(lblCurrentNote, BorderLayout.NORTH)

		lblNote = new JLabel(currentPage+"/"+totalPage)
		lblNote.setFont(new Font("Arial", Font.PLAIN, 14))
		lblNote.setHorizontalAlignment(SwingConstants.CENTER)
		pnlNote.add(lblNote, BorderLayout.CENTER)

		spltPnPaper = new JSplitPane()
		pnlNote.add(spltPnPaper, BorderLayout.SOUTH)

		btnPrevPaper = new JButton("<<")
		spltPnPaper.setLeftComponent(btnPrevPaper)
		
		btnPrevPaper.addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				if(currentPage > 0 && currentPage<=totalPage){
					currentPage--;
				}
				lblNote.setText(currentPage+"/"+totalPage);
				lblNote.repaint()
				previousPage()		
				}
		})

		btnNextPaper = new JButton(">>")
		spltPnPaper.setRightComponent(btnNextPaper)
		
		btnNextPaper.addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				if(currentPage<totalPage){
					currentPage++;
				}
				lblNote.setText(currentPage+"/"+totalPage);
				lblNote.repaint()
					nextPage()		
				}
		})

		// pnlPdf = new JPanel();
		pnlPdf = new PdfPanel(this.pdfPresenter)
		window.getContentPane().add(pnlPdf, BorderLayout.CENTER)
		pnlPdf.setLayout(new BorderLayout(0, 0))

		pnlDown = new JPanel()
		pnlPdf.add(pnlDown, BorderLayout.SOUTH)
		pnlDown.setLayout(new BorderLayout(0, 0))

		btnDown = new JButton("▲")

		

		// pnlContentDown = new JPanel();
		var InputStream inputContentDown = ResourcesUtils.getInputStreamResource("logo.png")
		pnlContentDown = new ImagePanel(inputContentDown)
		pnlContentDown.setPreferredSize(new Dimension(pnlContentDown.getSize().width, 180))

		// to resize the correction 
		mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JPanel(), pnlContentDown);
		pnlDown.add(btnDown, BorderLayout.NORTH)

		pnlQst = new JPanel()
		window.getContentPane().add(pnlQst, BorderLayout.EAST)
		pnlQst.setLayout(new BorderLayout(0, 0))

		lblNbQst = new JLabel("Question No.i")
		lblNbQst.setFont(new Font("Arial", Font.PLAIN, 14))
		lblNbQst.setHorizontalAlignment(SwingConstants.CENTER)
		pnlQst.add(lblNbQst, BorderLayout.NORTH)

		txtPnQst = new JTextPane()
		pnlQst.add(txtPnQst, BorderLayout.CENTER)

		spltPnQst = new JSplitPane()
		pnlQst.add(spltPnQst, BorderLayout.SOUTH)

		btnPrevQst = new JButton("<")
		spltPnQst.setLeftComponent(btnPrevQst)

		btnNextQst = new JButton(">")
		spltPnQst.setRightComponent(btnNextQst)
	}

	/** 
	 * Ouvre ou ferme l'énoncé d'une question
	 */
	def void showContentDown() {
		if (contentDown) {
			pnlDown.remove(mainSplitPane)
			btnDown.setText("▲")
			contentDown = false
		} else {
			pnlDown.add(mainSplitPane, BorderLayout.CENTER)
			btnDown.setText("▼")
			contentDown = true
		}
	}
	/** 
	 * Naviguer vers previously page
	 */
	def void previousPage(){
		pdfPresenter.presenterPdf.getPresenterPdf().previousPdfPage;
	}/** 
	 * Naviguer vers next page
	 */
	
	def void nextPage(){
		pdfPresenter.presenterPdf.getPresenterPdf().nextPdfPage;
	}


	// ----------------------------------------------------------------------------------------------------
	/** 
	 * GETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def JFrame getWindow() {
		return window;
	}
	
	/**
	 * Envoie le bouton de chargement de pdf
	 * @return mnItemLoad
	 */
	def JMenuItem getMnItemLoad() {
		return mnItemLoad;
	}
	
	/**
	 * Envoie le bouton de changement de session
	 * @return mnItemSession
	 */
	def JMenuItem getMnItemSession() {
		return mnItemSession;
	}
	
	def JButton getBtnDown() {
		return btnDown
	}
	
	def JPanel getPnlDown() {
		return pnlDown
		
		}
	def JMenuItem getMnItemSwap() {
		return mnItemSwap;
	}

}
