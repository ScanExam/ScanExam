package fr.istic.tools.scanexam.view.swing

import java.util.ArrayList
import java.util.LinkedList
import java.util.List
import javax.swing.JPanel

/** 
 * Regroupement de plusieurs boîtes
 * @author Julien Cochet
 */
class BoxList {
	
	// ----------------------------------------------------------------------------------------------------
	/* 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	// Liste des boîtes
	List<Box> list
	
	// Largueur minimum des boîtes (ne peux pas être négatif)
	double minWidth
	
	// Hauteur minimum des boîtes (ne peux pas être négatif)
	double minHeight
	
	// Largueur maximum des boîtes (-1 = pas de maximum)
	double maxWidth
	
	// Hauteur maximum des boîtes (-1 = pas de maximum)
	double maxHeight
	
	// Panel où sont affichées les boîtes (ne sert que si l'objet est affiché dans une interface Swing)
	JPanel panel

	// ----------------------------------------------------------------------------------------------------
	/* 
	 * CONSTRUCTEURS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/** 
	 * Constructeur par défaut
	 */
	new() {
		this.setList(new ArrayList())
		this.setMinWidth(-1.0)
		this.setMinHeight(-1.0)
		this.setMaxWidth(-1.0)
		this.setMaxHeight(-1.0)
	}

	/** 
	 * Constructeur paramétré avec création d'une nouvelle liste de boîtes
	 * @param minWidth Largueur minimum des boîtes
	 * @param minHeight Hauteur minimum des boîtes
	 * @param maxWidth Largueur maximum des boîtes
	 * @param maxHeight Hauteur maximum des boîtes
	 */
	new(double minWidth, double minHeight, double maxWidth, double maxHeight) {
		this.setList(new ArrayList())
		this.setMinWidth(minWidth)
		this.setMinHeight(minHeight)
		this.setMaxWidth(maxWidth)
		this.setMaxHeight(maxHeight)
	}

	/** 
	 * Constructeur paramétré
	 * @param list Boîtes à importer
	 * @param minWidth Largueur minimum des boîtes
	 * @param minHeight Hauteur minimum des boîtes
	 * @param maxWidth Largueur maximum des boîtes
	 * @param maxHeight Hauteur maximum des boîtes
	 */
	new(List<Box> list, double minWidth, double minHeight, double maxWidth, double maxHeight) {
		this.setList(list)
		this.setMinWidth(minWidth)
		this.setMinHeight(minHeight)
		this.setMaxWidth(maxWidth)
		this.setMaxHeight(maxHeight)
	}

	// ----------------------------------------------------------------------------------------------------
	/* 
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Ajoute une boîte à la liste
	 * @param box Boîte à ajouter
	 */
	def void addBox(Box box) {
		list.add(box)
	}

	/**
	 * Créer une nouvelle boîte dans la liste à partir de coordonnées
	 * @param x Coordonnée X de la boîte
	 * @param y Coordonnée Y de la boîte
	 */
	def void addBox(double x, double y) {
		addBox(x, y, "")
	}

	/**
	 * Créer une nouvelle boîte dans la liste à partir de coordonnées et d'un nom
	 * @param x Coordonnée X de la boîte
	 * @param y Coordonnée Y de la boîte
	 * @param title Nom de la boîte
	 */
	def void addBox(double x, double y, String title) {
		addBox(x, y, minWidth, minHeight, title)
	}

	/**
	 * Créer une nouvelle boîte dans la liste à partir de coordonnées, de dimensions et d'un nom
	 * @param x Coordonnée X de la boîte
	 * @param y Coordonnée Y de la boîte
	 * @param width Largueur de la boîte
	 * @param height Hauteur de la boîte
	 * @param title Nom de la boîte
	 */
	def void addBox(double x, double y, double width, double height, String title) {
		list.add(new Box(x, y, width, height, title, minWidth, minHeight, maxWidth, maxHeight))
	}

	/**
	 * Met à jour les coordonnées et dimension d'une boîte à partir de son identifiant
	 * @param index Identifiant de la boîte
	 * @param x Nouvelle coordonnée X
	 * @param y Nouvelle coordonnée Y
	 * @param width Nouvelle largueur
	 * @param height Nouvelle hauteur
	 */
	def void updateBox(int index, double x, double y, double width, double height) {
		updateBox(getBox(index), x, y, width, height)
	}

	/**
	 * Met à jour les coordonnées et dimension d'une boîte
	 * @param box Boîte à mettre à jour
	 * @param x Nouvelle coordonnée X
	 * @param y Nouvelle coordonnée Y
	 * @param width Nouvelle largueur
	 * @param height Nouvelle hauteur
	 */
	def void updateBox(Box box, double x, double y, double width, double height) {
		box.updateCoordinates(x, y)
		box.updateSize(width, height)
	}

	/**
	 * Met à jour les bornes minimales et maximales des boîtes
	 * @param minWidth Nouvelle largueur minimale
	 * @param minHeight Nouvelle hauteur minimale
	 * @param maxWidth Nouvelle largueur maximale
	 * @param maxHeight Nouvelle hauteur maximale
	 */
	def void updateBounds(double minWidth, double minHeight, double maxWidth, double maxHeight) {
		setMinWidth(minWidth)
		setMinHeight(minHeight)
		setMaxWidth(maxWidth)
		setMaxHeight(maxHeight)
	}

	/**
	 * Retire à boîte de la liste à partir de son identifiant
	 * @param index Indentifiant de la boîte
	 */
	def void removeBox(int index) {
		list.remove(index)
	}

	/**
	 * Retire à boîte de la liste
	 * @param box Boîte à retirer
	 */
	def void removeBox(Box box) {
		list.remove(box)
	}

	/**
	 * Retirer toutes les boîtes de la liste
	 */
	def void clearList() {
		list.clear()
		refreshPanel()
	}
	
	/**
	 * Retranscrit les informations des boîtes sous forme de liste de String
	 * @return Informations des boîtes
	 */
	def List<String> toStringList() {
		var List<String> stringList = new LinkedList<String>()
		for (var int i = 0; i < size(); i++) {
			stringList.add(getBox(i).toString())
		}
		return stringList
	}

	/**
	 * Met à jour la vue
	 */
	def private void refreshPanel() {
		if (panel !== null) {
			panel.repaint()
		}
	}

	// ----------------------------------------------------------------------------------------------------
	/* 
	 * GETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Renvoie la liste des boîtes
	 * @return Liste des boîtes
	 */
	def List<Box> getList() {
		return list
	}

	/**
	 * Renvoie une boîte à partir de son identifiant
	 * @param index Identifiant de la boîte
	 * @return Boîte
	 */
	def Box getBox(int index) {
		return list.get(index)
	}

	/**
	 * Renvoie le nombre de boîtes
	 * @return Nombre de boîtes
	 */
	def int size() {
		return list.size()
	}

	/**
	 * Renvoie la largueur minimale des boîtes
	 * @return Largueur minimale des boîtes
	 */
	def double getMinWidth() {
		return minWidth
	}

	/**
	 * Renvoie la hauteur minimale des boîtes
	 * @return Hautuer minimale des boîtes
	 */
	def double getMinHeight() {
		return minHeight
	}

	/**
	 * Renvoie la largueur maximale des boîtes
	 * @return Largueur maximale des boîtes
	 */
	def double getMaxWidth() {
		return maxWidth
	}

	/**
	 * Renvoie la hauteur maximale des boîtes
	 * @return Hautuer maximale des boîtes
	 */
	def double getMaxHeight() {
		return maxHeight
	}

	// ----------------------------------------------------------------------------------------------------
	/* 
	 * SETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Replace la liste des boîtes par celle donnée en paramètre
	 * @param list Nouvelle liste de boîtes
	 */
	def void setList(List<Box> list) {
		this.list = list
	}

	/**
	 * Met à jour la largueur minimale des boîtes
	 * @param minWidth Nouvelle largueur minimale des boîtes
	 */
	def void setMinWidth(double minWidth) {
		if (minWidth < 0) {
			this.minWidth = 0
		} else {
			this.minWidth = minWidth
		}
		for (var int i = 0; i < size(); i++) {
			getBox(i).setMinWidth(this.minWidth)
		}
	}

	/**
	 * Met à jour la hauteur minimale des boîtes
	 * @param minWidth Nouvelle hauteur minimale des boîtes
	 */
	def void setMinHeight(double minHeight) {
		if (minHeight < 0) {
			this.minHeight = 0
		} else {
			this.minHeight = minHeight
		}
		for (var int i = 0; i < size(); i++) {
			getBox(i).setMinHeight(this.minHeight)
		}
	}

	/**
	 * Met à jour la largueur maximale des boîtes
	 * @param minWidth Nouvelle largueur maximale des boîtes
	 */
	def void setMaxWidth(double maxWidth) {
		if (maxWidth < 0) {
			this.maxWidth = -1
		} else {
			this.maxWidth = maxWidth
		}
		for (var int i = 0; i < size(); i++) {
			getBox(i).setMaxWidth(this.maxWidth)
		}
	}

	/**
	 * Met à jour la hauteur maximale des boîtes
	 * @param minWidth Nouvelle hauteur maximale des boîtes
	 */
	def void setMaxHeight(double maxHeight) {
		if (maxHeight < 0) {
			this.maxHeight = -1
		} else {
			this.maxHeight = maxHeight
		}
		for (var int i = 0; i < size(); i++) {
			getBox(i).setMaxHeight(this.maxHeight)
		}
	}

	/**
	 * Met à jour la référence vers la vue
	 * @param panel Vue swing
	 */
	def void setPanel(JPanel panel) {
		this.panel = panel
	}
	
}
