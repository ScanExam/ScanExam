package fr.istic.tools.scanexam.box

/** 
 * Donnée d'une boîte en deux dimensions
 * @author Julien Cochet
 */
class Box {
	
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	// Coordonnée X de la boîte
	double x
	// Coordonnée Y de la boîte
	double y
	// Largueur de la boîte
	double width
	// Hauteur de la boîte
	double height
	// Titre de la boîte
	String title
	// Indentifiant de la boîte
	int id
	// Largueur minimum de la boîte (ne peux pas être négatif)
	double minWidth
	// Hauteur minimum de la boîte (ne peux pas être négatif)
	double minHeight
	// Largueur maximum de la boîte (-1 = pas de maximum)
	double maxWidth
	// Hauteur maximum de la boîte (-1 = pas de maximum)
	double maxHeight
	// Numero de page de la box
	int nbPage

	// ----------------------------------------------------------------------------------------------------
	/** 
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
	}

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def void updateCoordinates(double x, double y) {
		setX(x)
		setY(y)
	}

	def void updateSize(double width, double height) {
		setWidth(width)
		setHeight(height)
	}

	def void updateBounds(double minWidth, double minHeight, double maxWidth, double maxHeight) {
		setMinWidth(minWidth)
		setMinHeight(minHeight)
		setMaxWidth(maxWidth)
		setMaxHeight(maxHeight)
	}

	override String toString() {
		return title + "," + x + "," + y + "," + width + "," + height
	}

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * GETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def double getX() {
		return x
	}

	def double getY() {
		return y
	}

	def double getWidth() {
		return width
	}

	def double getHeight() {
		return height
	}

	def String getTitle() {
		return title
	}

	def int getId() {
		return id
	}

	def double getMinWidth() {
		return minWidth
	}

	def double getMinHeight() {
		return minHeight
	}

	def double getMaxWidth() {
		return maxWidth
	}

	def double getMaxHeight() {
		return maxHeight
	}

	def int getNbPage() {
		return nbPage
	}

	def void setNbPage(int nbPage) {
		this.nbPage = nbPage
	}

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * SETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def void setX(double x) {
		this.x = x
	}

	def void setY(double y) {
		this.y = y
	}

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

	def void setTitle(String title) {
		this.title = title
	}

	def void setId(int id) {
		this.id = id
	}

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
	
}
