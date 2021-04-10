package fr.istic.tools.scanexam.view.fx.utils

import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.css.PseudoClass
import javafx.scene.Node
import javafx.scene.control.TextInputControl
import javafx.util.Duration

/**
 * Classe utilitaire pour signaler un mauvais format d'un nœud à l'utilisateur de diverses manières
 * @author Théo Giraudet
 */
class BadFormatDisplayer {

	/** 
	 * Déclenche une petite animation de secouement sur le nœud passé en paramètre
	 * @param node un nœud JavaFX
	 * */
	def static void shake(Node node) {
		val tl = new Timeline(
			new KeyFrame(Duration.millis(0), new KeyValue(node.translateXProperty(), 0, Interpolator.EASE_BOTH)),
			new KeyFrame(Duration.millis(100), new KeyValue(node.translateXProperty(), -10, Interpolator.EASE_BOTH)),
			new KeyFrame(Duration.millis(200), new KeyValue(node.translateXProperty(), 10, Interpolator.EASE_BOTH)),
			new KeyFrame(Duration.millis(300), new KeyValue(node.translateXProperty(), -10, Interpolator.EASE_BOTH)),
			new KeyFrame(Duration.millis(400), new KeyValue(node.translateXProperty(), 10, Interpolator.EASE_BOTH)),
			new KeyFrame(Duration.millis(500), new KeyValue(node.translateXProperty(), -10, Interpolator.EASE_BOTH)),
			new KeyFrame(Duration.millis(600), new KeyValue(node.translateXProperty(), 10, Interpolator.EASE_BOTH)),
			new KeyFrame(Duration.millis(700), new KeyValue(node.translateXProperty(), -10, Interpolator.EASE_BOTH)),
			new KeyFrame(Duration.millis(800), new KeyValue(node.translateXProperty(), 10, Interpolator.EASE_BOTH)),
			new KeyFrame(Duration.millis(900), new KeyValue(node.translateXProperty(), -10, Interpolator.EASE_BOTH)),
			new KeyFrame(Duration.millis(1000), new KeyValue(node.translateXProperty(), 0, Interpolator.EASE_BOTH))
		)
		tl.setDelay(Duration.seconds(0.2))
		tl.play()
	}

	/** 
	 * Déclenche une petite animation de secouement sur les nœuds passés en paramètre
	 * @param nodes des nœuds JavaFX
	 * */
	def static void shake(Node... nodes) {
		for (Node node : nodes)
			shake(node)
	}

	/**
	 * Déclenche une petite animation de secouement sur les {@link TextInputControl} passés en paramètre si ceux-ci sont vide, ne fait rien sinon
	 * @param nodes des {@link TextInputControl}
	 */
	def static boolean isFullOrElseShake(TextInputControl... nodes) {
		var isFull = true
		for (TextInputControl n : nodes)
			if (n.getText().equals("")) {
				shake(n)
				isFull = false
			}
		return isFull
	}

	/**
	 * Active ou désactive l'affichage de mauvais format pour le {@link TextInputControl} passé en paramètre. C'est-à-dire anime le nœud ({@link #shake shake}) et met lui une bordure rouge
	 * @param node un nœud JavaFX
	 * @param disp si il faut activer ou non l'affichage   
	 */
	def static void dispBadFormatView(TextInputControl node, boolean disp) {
		val PseudoClass errorClass = PseudoClass.getPseudoClass("wrong-format")
		if (disp) {
			node.pseudoClassStateChanged(errorClass, true)
			BadFormatDisplayer.shake(node)
		} else
			node.pseudoClassStateChanged(errorClass, false)
	}
}
