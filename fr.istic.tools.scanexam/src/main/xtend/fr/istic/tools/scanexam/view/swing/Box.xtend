package fr.istic.tools.scanexam.view.swing

/** 
 * Donnée d'une boîte en deux dimensions
 * @author Julien Cochet
 */
class Box {
	
	// ----------------------------------------------------------------------------------------------------
	/*
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/* Coordonnée X de la boîte */ 
	double x
	
	/* Coordonnée Y de la boîte */ 
	double y
	
	/* Largueur de la boîte */ 
	double width
	
	/* Hauteur de la boîte */ 
	double height
	
	/* Titre de la boîte */ 
	String title
	
	/* Indentifiant de la boîte (-1 par défaut) */ 
	int id
	
	/* Largueur minimum de la boîte (ne peux pas être négatif) */ 
	double minWidth
	
	/* Hauteur minimum de la boîte (ne peux pas être négatif) */ 
	double minHeight
	
	/* Largueur maximum de la boîte (-1 = pas de maximum) */ 
	double maxWidth
	
	/* Hauteur maximum de la boîte (-1 = pas de maximum) */ 
	double maxHeight
	
	/* Numero de page de la box*/ 
	int nbPage

	// ----------------------------------------------------------------------------------------------------
	/*
	 * CONSTRUCTEURS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/** 
	 * Constructeur par défaut
	 */
	new() {
		this(0.0, 0.0, 0.0, 0.0, "")
	}

	/** 
	 * Constructeur paramétré sans bornes
	 * @param x Coordonnée X de la boîte
	 * @param y Coordonnée Y de la boîte
	 * @param width Largueur de la boîte
	 * @param height Hauteur de la boîte
	 * @param title Titre de la boîte
	 */
	new(double x, double y, double width, double height, String title) {
		this(x, y, width, height, title, -1.0, -1.0, -1.0, -1.0)
	}

	/** 
	 * Constructeur paramétré
	 * @param x Coordonnée X de la boîte
	 * @param y Coordonnée Y de la boîte
	 * @param width Largueur de la boîte
	 * @param height Hauteur de la boîte
	 * @param title Titre de la boîte
	 * @param minWidth Largueur minimum de la boîte
	 * @param minHeight Hauteur minimum de la boîte
	 * @param maxWidth Largueur maximum de la boîte
	 * @param maxHeight Hauteur maximum de la boîte
	 */
	new(double x, double y, double width, double height, String title, double minWidth, double minHeight, double maxWidth, double maxHeight) {
		this.setMinWidth(minWidth)
		this.setMinHeight(minHeight)
		this.setMaxWidth(maxWidth)
		this.setMaxHeight(maxHeight)
		this.setX(x)
		this.setY(y)
		this.setWidth(width)
		this.setHeight(height)
		this.setTitle(title)
		this.setId(-1)
		this.setNbPage(-1)
	}

	// ----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Met à jour les coordonnées de la boîte
	 * @param x Nouvelle coordonnée X
	 * @param y Nouvelle coordonnée Y
	 */
	def void updateCoordinates(double x, double y) {
		setX(x)
		setY(y)
	}

	/**
	 * Met à jour la taille de la boîte
	 * @param width Nouvelle largueur de la boîte
	 * @param height Nouvelle hauteur de la boîte
	 */
	def void updateSize(double width, double height) {
		setWidth(width)
		setHeight(height)
	}

	/**
	 * Met à jour les bornes minimums et maximums de la boîte
	 * @param minWidth Nouvelle largueur minimale de la boîte
	 * @param minHeight Nouvelle hauteur minimale de la boîte
	 * @param maxWidth Nouvelle largueur maximale de la boîte
	 * @param maxHeight Nouvelle hauteur maximale de la boîte
	 */
	def void updateBounds(double minWidth, double minHeight, double maxWidth, double maxHeight) {
		setMinWidth(minWidth)
		setMinHeight(minHeight)
		setMaxWidth(maxWidth)
		setMaxHeight(maxHeight)
	}

	/**
	 * Retranscrit les informations de la boîte sous forme de String
	 * @return Informations de la boîte
	 */
	override String toString() {
		return "Box#" + id + " \"" + title + "\", " + x + ", " + y + ", " + width + ", " + height + ", on page " + nbPage
	}

	// ----------------------------------------------------------------------------------------------------
	/* 
	 * GETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Renvoie la coordonnée X de la boîte
	 * @param Coordonnée X de la boîte
	 */
	def double getX() {
		return x
	}

	/**
	 * Renvoie la coordonnée Y de la boîte
	 * @param Coordonnée Y de la boîte
	 */
	def double getY() {
		return y
	}
	
	/**
	 * Renvoie la largueur de la boîte
	 * @param Largueur de la boîte
	 */
	def double getWidth() {
		return width
	}

	/**
	 * Renvoie la hauteur de la boîte
	 * @param Hauteur de la boîte
	 */
	def double getHeight() {
		return height
	}

	/**
	 * Renvoie le nom de la boîte
	 * @param Nom de la boîte
	 */
	def String getTitle() {
		return title
	}

	/**
	 * Renvoie l'indentifiant de la boîte
	 * @param Identifiant de la boîte
	 */
	def int getId() {
		return id
	}

	/**
	 * Renvoie la largueur minimale de la boîte
	 * @param Largueur minimale de la boîte
	 */
	def double getMinWidth() {
		return minWidth
	}

	/**
	 * Renvoie la hauteur minimale de la boîte
	 * @param Hauteur minimale de la boîte
	 */
	def double getMinHeight() {
		return minHeight
	}

	/**
	 * Renvoie la largueur maximale de la boîte
	 * @param Largueur maximale de la boîte
	 */
	def double getMaxWidth() {
		return maxWidth
	}

	/**
	 * Renvoie la hauteur maximale de la boîte
	 * @param Hauteur maximale de la boîte
	 */
	def double getMaxHeight() {
		return maxHeight
	}

	/**
	 * Renvoie le numéro de la page où se trouve la boîte
	 * @return Numéro de page où se trouve la boîte
	 */
	def int getNbPage() {
		return nbPage
	}

	// ----------------------------------------------------------------------------------------------------
	/* 
	 * SETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Met à jour la coordonnée X de la boîte
	 * @param x Nouvelle coordonnée X de la boîte
	 */
	def void setX(double x) {
		this.x = x
	}

	/**
	 * Met à jour la coordonnée Y de la boîte
	 * @param y Nouvelle coordonnée Y de la boîte
	 */
	def void setY(double y) {
		this.y = y
	}

	/**
	 * Met à jour la largueur de la boîte. La valeur sera automatiquement bornée au minimum et maximum autorisé.
	 * @param y Nouvelle largueur de la boîte
	 */
	def void setWidth(double width) {
		if (width < minWidth) {
			this.width = minWidth
		} else {
			if (maxWidth >= 0.0 && width > maxWidth) {
				this.width = maxWidth
			} else {
				this.width = width
			}
		}
	}

	/**
	 * Met à jour la hauteur de la boîte. La valeur sera automatiquement bornée au minimum et maximum autorisé.
	 * @param y Nouvelle hauteur de la boîte
	 */
	def void setHeight(double height) {
		if (height < minHeight) {
			this.height = minHeight
		} else {
			if (maxHeight >= 0.0 && height > maxHeight) {
				this.height = maxHeight
			} else {
				this.height = height
			}
		}
	}

	/**
	 * Met à jour le titre de la boîte
	 * @param Nouveau nom de la boîte
	 */
	def void setTitle(String title) {
		this.title = title
	}

	/**
	 * Met à jour l'identifiant de la boîte
	 * @param Nouvel identifiant de la boîte
	 */
	def void setId(int id) {
		this.id = id
	}

	/**
	 * Met à jour la largueur minimale de la boîte
	 * @param y Nouvelle largueur minimale de la boîte
	 */
	def void setMinWidth(double minWidth) {
		if (minWidth < 0.0) {
			this.minWidth = 0.0
		} else {
			this.minWidth = minWidth
		}
		if (width < this.minWidth) {
			width = this.minWidth
		}
	}
	
	/**
	 * Met à jour la hauteur minimale de la boîte
	 * @param y Nouvelle hautueur minimale de la boîte
	 */
	def void setMinHeight(double minHeight) {
		if (minHeight < 0.0) {
			this.minHeight = 0.0
		} else {
			this.minHeight = minHeight
		}
		if (height < this.minHeight) {
			height = this.minHeight
		}
	}

	/**
	 * Met à jour la largueur maximale de la boîte
	 * @param y Nouvelle largueur maximale de la boîte
	 */
	def void setMaxWidth(double maxWidth) {
		if (maxWidth < 0.0) {
			this.maxWidth = -1.0
		} else {
			this.maxWidth = maxWidth
		}
		if (this.maxWidth >= 0.0 && width > this.maxWidth) {
			width = maxWidth
		}
	}

	/**
	 * Met à jour la hauteur maximale de la boîte
	 * @param y Nouvelle hauteur maximale de la boîte
	 */
	def void setMaxHeight(double maxHeight) {
		if (maxHeight < 0.0) {
			this.maxHeight = -1.0
		} else {
			this.maxHeight = maxHeight
		}
		if (this.maxHeight >= 0.0 && height > this.maxHeight) {
			height = maxHeight
		}
	}

	/**
	 * Met à jour le numéro de la page où se trouve la boîte
	 * @param Nouveau numéro de page
	 */
	def void setNbPage(int nbPage) {
		this.nbPage = nbPage
	}
	
}