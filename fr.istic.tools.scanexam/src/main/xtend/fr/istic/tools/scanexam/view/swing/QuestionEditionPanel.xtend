package fr.istic.tools.scanexam.view.swing

import javax.swing.JSplitPane
import javax.swing.JLabel
import javax.swing.JButton
import javax.swing.JPanel
import java.awt.FlowLayout

import static fr.istic.tools.scanexam.config.LanguageManager.*
import fr.istic.tools.scanexam.box.Box

/** 
 * Classe Swing pour éditer une zone de quesition 
 * @author Julien Cochet
 */
class QuestionEditionPanel extends JPanel {
	
	/* Boîte liée à la question */
	var Box box
	
	/* Label affichant le titre de la question */
	var JLabel lblTitle
	
	/* Panel contenant les boutons */
	var JPanel pnlButtons
	
	/* Bouton pour déplacer la zone de question */
	var JButton btnMove
	
	/* Bouton pour redimensionner la zone de question */
	var JButton btnResize
	
	/* Bouton pour supprimer la zone de question */
	var JButton btnDelete
	
	/**
	 * Constructeur
	 */
	new(Box box) {
		this.box = box
		
		var JSplitPane splitPane = new JSplitPane
		
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT)
		
		lblTitle = new JLabel(this.box.getTitle())
		splitPane.setLeftComponent(lblTitle)

		pnlButtons = new JPanel()
		splitPane.setRightComponent(pnlButtons)
		pnlButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5))
		
		btnMove = new JButton(translate("label.tool.moveZone"))
		pnlButtons.add(btnMove)
		
		btnResize = new JButton(translate("label.tool.resizeZone"))
		pnlButtons.add(btnResize)
		
		btnDelete = new JButton(translate("menu.edit.delete"))
		pnlButtons.add(btnDelete)
		
		this.add(splitPane)
	}
}
