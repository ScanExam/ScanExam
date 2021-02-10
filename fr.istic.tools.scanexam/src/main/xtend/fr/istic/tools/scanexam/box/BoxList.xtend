package fr.istic.tools.scanexam.box

import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
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
	/** 
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
	/** 
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
	/** 
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def void addBox(Box box) {
		list.add(box)
	}

	def void addBox(double x, double y) {
		list.add(new Box(x, y, minWidth, minHeight, "", minWidth, minHeight, maxWidth, maxHeight))
	}

	def void addBox(double x, double y, String title) {
		list.add(new Box(x, y, minWidth, minHeight, title, minWidth, minHeight, maxWidth, maxHeight))
	}

	def void addBox(double x, double y, double width, double height, String title) {
		list.add(new Box(x, y, width, height, title, minWidth, minHeight, maxWidth, maxHeight))
	}

	def void updateBox(int index, double x, double y, double width, double height) {
		getBox(index).updateCoordinates(x, y)
		getBox(index).updateSize(width, height)
	}

	def void updateBox(Box box, double x, double y, double width, double height) {
		box.updateCoordinates(x, y)
		box.updateSize(width, height)
	}

	def void updateBounds(double minWidth, double minHeight, double maxWidth, double maxHeight) {
		setMinWidth(minWidth)
		setMinHeight(minHeight)
		setMaxWidth(maxWidth)
		setMaxHeight(maxHeight)
	}

	def void removeBox(int index) {
		list.remove(index)
	}

	def void removeBox(Box box) {
		list.remove(box)
	}

	def void clearList() {
		list.clear()
		refreshPanel()
	}

	def void save() {
		var Path file = Paths.get("export.txt")
		try {
			Files.write(file, toStringList(), StandardCharsets.UTF_8)
			System.out.println("Save success")
		} catch (IOException e) {
			System.out.println("Save fail")
			e.printStackTrace()
		}

	}

	def void load(String path) {
		var Path file = Paths.get(path)
		try {
			clearList()
			var List<String> boxes = Files.readAllLines(file)
			for (var int i = 0; i < boxes.size(); i++) {
				var String currentBox = boxes.get(i)
				var String[] currentBoxData = currentBox.split(",")
				var double x = Double.parseDouble(currentBoxData.get(1))
				var double y = Double.parseDouble(currentBoxData.get(2))
				var double width = Double.parseDouble(currentBoxData.get(3))
				var double height = Double.parseDouble(currentBoxData.get(4))
				var String title = currentBoxData.get(0)
				addBox(x, y, width, height, title)
			}
			refreshPanel()
			System.out.println("Load success")
		} catch (IOException e) {
			System.out.println("Load fail")
			e.printStackTrace()
		}

	}

	def List<String> toStringList() {
		var List<String> stringList = new LinkedList<String>()
		for (var int i = 0; i < size(); i++) {
			stringList.add(getBox(i).toString())
		}
		return stringList
	}

	def private void refreshPanel() {
		if (panel !== null) {
			panel.repaint()
		}
	}

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * GETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def List<Box> getList() {
		return list
	}

	def Box getBox(int index) {
		return list.get(index)
	}

	def int size() {
		return list.size()
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

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * SETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def void setList(List<Box> list) {
		this.list = list
	}

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

	def void setPanel(JPanel panel) {
		this.panel = panel
	}
	
}
