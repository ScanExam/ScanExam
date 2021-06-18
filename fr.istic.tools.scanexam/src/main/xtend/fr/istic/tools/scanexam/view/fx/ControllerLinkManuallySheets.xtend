package fr.istic.tools.scanexam.view.fx

import javafx.fxml.FXML
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.control.ScrollPane
import javafx.scene.control.Label
import fr.istic.tools.scanexam.services.api.ServiceGraduation
import javafx.stage.Stage
import javafx.scene.input.InputEvent
import javafx.scene.Node
import java.util.List
import java.util.ArrayList
import org.apache.pdfbox.pdmodel.PDDocument
import javafx.scene.image.ImageView
import javafx.embed.swing.SwingFXUtils
import java.awt.image.BufferedImage
import org.apache.pdfbox.rendering.ImageType
import org.apache.pdfbox.rendering.PDFRenderer
import javafx.scene.control.TextField
import javafx.event.EventHandler
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.geometry.Insets
import javafx.scene.layout.Background

/** 
 * Controlleur de l'UI qui permet de réassigner des pages à des copies manuellement
 * @author Romain Caruana
 */
class ControllerLinkManuallySheets {
	
	/*********************
	 ***** VARIABLES *****
	 *********************/
	
	//partie qui coontient la liste 
	@FXML
	var ScrollPane listPane
	
	//Image de la page du PDF
	@FXML
	var ImageView pageImageView
	
	//Service de correction (passerelle vers les données)
	var ServiceGraduation service
	
	//index de la page courante dans la liste
	var int indexCurrentPage
	
	//liste des pages
	var List<Integer> failedPages
	
	//le document PDF
	var PDDocument document
	
	//le pdf
	var PDFRenderer pdfRenderer
	
	//le composant liste
	var FailedPageItemList pageItemList
	
	/********************
	 ***** METHODES *****
	 ********************/
	 
	 /**
	  * Initialise la fenêtre
	  */
	 def void init(ServiceGraduation serviceGraduation, PdfManager pdfManager){
		service = serviceGraduation	
		failedPages = List.of(2,3,5,7)//service.failedPages
		indexCurrentPage = 0
		
		this.document = PDDocument.load(pdfManager.pdfInputStream)
		
		pdfRenderer = new PDFRenderer(document)
		
		updateImageView
		pageItemList = new FailedPageItemList(this)
		listPane.content = pageItemList
	 }
	 
	 def getFailedPages(){
	 	failedPages
	 }
	 
	 def getIndexCurrentPage(){
	 	indexCurrentPage
	 }
	 
	 def setIndexCurrentPage(int i){
	 	indexCurrentPage = i
	 }
	 
	 /**
	  * Méthode qui sauvegarde les modifications et quitte la fenêtre
	  */
	 def void saveAndQuit(){
	 	println("save and quit")
	 	//TODO faire la sauvegarde dans le modèle
	 }
	 
	 /**
	  * Méthode qui quitte la fenêtre sans sauvegarder
	  */
	 def void cancelAndQuit(InputEvent e){
	 	println("cancel and quit")
	 	val Node source = e.getSource() as Node
    	val Stage stage = source.getScene().getWindow() as Stage
    	stage.close();
	 }
	 
	 /**
	  * Méthode qui ignore la page sélectionnée et la retire du champ d'action
	  */
	 def void ignorePage(){
	 	println("ignore page")
	 		 	
	 	var List<Integer> temp = new ArrayList<Integer>()
	 	for(i : 0 ..< failedPages.size){
	 		if(i != indexCurrentPage)
	 			temp.add(failedPages.get(i))
	 	}
	 	
	 	if(failedPages.size > 1)
	 		failedPages = temp
	 	else
	 		failedPages = new ArrayList
	 	
	 	
	 	if(failedPages.size > 0)
	 		println("on continue")
	 	else
	 		indexCurrentPage = -1
	 	
	 	updateStatement
	 }
	 
	 /**
	  * Méthode qui affiche la page suivante
	  */
	  def void nextPage(){
	  	if(indexCurrentPage != -1){
		  	if(failedPages.size>1) {
			  	if(indexCurrentPage < failedPages.size - 1)
			  		indexCurrentPage++
			  	else
			  		indexCurrentPage = 0
			  	println("next")			 	
			 	updateStatement
		  }
	 	}
	  }
	  
	 /**
	  * Méthode qui affiche la page précédente
	  */
	  def void previousPage(){
	  	if(indexCurrentPage != -1){
		  	if(failedPages.size>1) {
			 	if(indexCurrentPage > 0)
			  		indexCurrentPage--
			  	else
			  		indexCurrentPage = failedPages.size-1
			  	println("prev")		  	
			  	updateStatement
		  }
	  	}
	  }
	  
	  
	  /**
	   * Méthode qui met à jour l'état courant de la fenêtre
	   */
	  def void updateStatement(){
	  	pageItemList.updateList
	  	
	  	updateImageView
	  	//TODO à compléter au fur et a mesure
	  }
	  
	  /**
	  * Affiche l'image de la page
	  */
	  def void updateImageView(){
	  	if(indexCurrentPage >= failedPages.size){
	  		indexCurrentPage = 0
	  		pageItemList.updateList
	  		
	  	}
	  	
	  	var BufferedImage bim
	  	if(indexCurrentPage != -1)
	  		bim = pdfRenderer.renderImageWithDPI(failedPages.get(indexCurrentPage), 300, ImageType.RGB)
	  	
	  	pageImageView.image = SwingFXUtils.toFXImage(bim, null)
	  	pageImageView.fitHeight = 650
	  	pageImageView.fitWidth = 500
	  	
	  }
	  
}

class FailedPageItem extends HBox {
	
	int num
	
	//numéro de la page
	Label numPage
	
	//le champ à compléter avec la chaîne de caractère
	TextField field
	
	//la liste complète des pages
	FailedPageItemList list
	
	new(Integer num, FailedPageItemList list){
		this.num = num
		this.list = list
		numPage = new Label(num.toString)
		field = new TextField
		this.children.add(numPage)
		this.children.add(field)
		setupEvents
	}
	
	def setList(FailedPageItemList list){
		this.list = list
	}
	
	def getNum(){
		return num
	}
	
	def void setFocus(boolean b) {//sets the color of the zone and the item in the list
		if (b) {
			color = FxSettings.ITEM_HIGHLIGHT_COLOR
			//this.name.styleClass.add("focusedText")	
		}
		else {
			color = FxSettings.ITEM_NORMAL_COLOR
			//this.name.styleClass.remove("focusedText")	
		}
	}
	def setColor(Color color){
		var bf = new BackgroundFill(color,CornerRadii.EMPTY,Insets.EMPTY);
		this.background = new Background(bf)
	}
	
	
	def setupEvents(){
		//val me = this
		this.onMouseClicked = new EventHandler<MouseEvent>(){
			
			override handle(MouseEvent event) {
				//TODO
				list.controller.indexCurrentPage = list.controller.failedPages.indexOf(num)
				//println(num)
				list.controller.updateStatement
			}
			
		}
	}
}

class FailedPageItemList extends VBox {
	//il y a un champ children pour append des éléments à la VBox
	ControllerLinkManuallySheets controller
	
	new (ControllerLinkManuallySheets controller){
		this.controller = controller
		updateList
	}
	
	def getController(){
		controller
	}
	
	def addItem(FailedPageItem item) {
		item.list = this;
		children.add(item)
	}
	
	def removeItem(FailedPageItem item) {
		children.remove(item)
	}
	
	def clearItems(){
		children.clear
	}
	
	def updateList(){
		clearItems
		println("current index : " + controller.indexCurrentPage)
		for(page : 0 ..< controller.failedPages.size){
			var item = new FailedPageItem(controller.failedPages.get(page), this)
			this.children.add(item) 
			if(page == controller.indexCurrentPage)
				item.focus = true
			else
				item.focus = false
		}
	}
}