package fr.istic.tools.scanexam.view.swing

import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import fr.istic.tools.scanexam.box.Box

import static fr.istic.tools.scanexam.config.LanguageManager.*
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import fr.istic.tools.scanexam.presenter.SelectionStateMachine

/**
 * Classe pour afficher la liste des questions en swing
 * @author Julien Cochet
 */
class ListOfQuestionsPanel extends JPanel {
	/* AdapterSwingBox Adaptateur gérant la boîte */
	var AdapterSwingBox adapterBox
	
	/**
	 * Constructeur
	 * @param AdapterSwingBox Adaptateur gérant la boîte
	 */
	new(AdapterSwingBox adapterBox) {
		this.adapterBox = adapterBox
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS))
	}
	
	/**
	 * Ajoute une question
	 * @param Box Boîte lié à la question
	 */
	def void addQst(Box box) {
		var JPanel pnlQst = new QuestionPanel(box, adapterBox, this)
		add(pnlQst)
		updateUI()
	}
}

/**
 * Classe pour gérer graphiquement une question en swing
 * @author Julien Cochet
 */
class QuestionPanel extends JPanel {
	/* Bouton "supprimer" */
	var JButton btnRemove
	/* Bouton "déplacer" */
	var JButton btnMove
	/* Bouton "redimensionner" */
	var JButton btnResize
	
	/**
	 * Constructeur
	 * @param Box Boîte lié à la question
	 * @param AdapterSwingBox Adaptateur gérant la boîte
	 * @param ListOfQuestionsPanel Liste visuelle des questions
	 */
	new(Box box, AdapterSwingBox adapterBox, ListOfQuestionsPanel listQstPanel) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS))
		
		var JLabel lblQstName = new JLabel(box.getTitle() + " ")
		add(lblQstName)
		
		btnRemove = new JButton(translate("menu.edit.delete"))
		btnMove = new JButton(translate("label.tool.moveZone"))
		btnResize = new JButton(translate("label.tool.resizeZone"))
		addActionListeners(box, adapterBox, this, listQstPanel)
		
		add(btnRemove)
		add(btnMove)
		add(btnResize)
	}
	
	/** 
	 * Ajoute les action listeners aux boutons de la vue
	 * @param Box Boîte lié à la question
	 * @param AdapterSwingBox Adaptateur gérant la boîte
	 * @param QuestionPanel JPanel de la question
	 * @param ListOfQuestionsPanel Liste visuelle des questions
	 */
	def private void addActionListeners(Box box, AdapterSwingBox adapterBox, QuestionPanel qstPanel, ListOfQuestionsPanel listQstPanel) {
		btnRemove.addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				//Actions lorsque le bouton "supprimer" est cliqué
				adapterBox.deleteBox(box)
				listQstPanel.remove(qstPanel)
				listQstPanel.updateUI()
			}
	    });
	    
		btnMove.addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				//Actions lorsque le bouton "déplacer" est cliqué
				SelectionStateMachine.setState(SelectionStateMachine.MOVE)
			}
	    });
	    
		btnResize.addActionListener(new ActionListener() {
			override actionPerformed(ActionEvent e) {
				//Actions lorsque le bouton "redimensionner" est cliqué
				SelectionStateMachine.setState(SelectionStateMachine.RESIZE)
			}
	    });
	}
	
}