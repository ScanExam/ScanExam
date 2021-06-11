package fr.istic.tools.scanexam.view.fx

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.control.ScrollPane
import javafx.scene.control.Label
import fr.istic.tools.scanexam.services.api.ServiceGraduation
import javafx.stage.Stage
import javafx.scene.input.InputEvent
import javafx.scene.Node
import java.util.Collection
import java.util.List

/** 
 * Controlleur de l'UI qui permet de réassigner des pages à des copies manuellement
 * @author Romain Caruana
 */
class ControllerLinkManuallySheets {
	
	/*********************
	 ***** VARIABLES *****
	 *********************/
	
	//Bouton qui fait ignorer la page selectionnée
	@FXML
	var Button buttonIgnore
	
	//Aller à la page précédente
	@FXML
	var Button buttonPrevious
	
	//Aller à la page suivante
	@FXML
	var Button buttonNext
	
	//Sauvegarder l'état et quitter la fenêtre
	@FXML
	var Button buttonSaveAndQuit
	
	//Quitter sans sauvegarder
	@FXML
	var Button buttonCancelAndQuit
	
	//Element root
	@FXML
	var HBox mainPane
	
	//Partie gauche de la fenêtre qui comporte la liste des pages et les éléments de navigation
	@FXML
	var VBox navigationPane
	
	//Partie droite qui contient l'affichage de la page en cours de traitement
	@FXML
	var ScrollPane pagePane
	
	//Texte qui demande de selectionner une page dans la liste
	@FXML
	var Label labelSelect
	
	//Texte qui indique combien de page il reste à traiter
	@FXML
	var Label labelNotTreated
	
	//Service de correction (passerelle vers les données)
	var ServiceGraduation service
	
	//index de la page courante dans la liste
	var int indexCurrentPage
	
	//liste des pages
	var Collection<Integer> failedPages
	
	/********************
	 ***** METHODES *****
	 ********************/
	 
	 def void init(ServiceGraduation serviceGraduation){
	 	println("bonjour")
		//service = serviceGraduation	
		failedPages = List.of(34,56,78,86,154)//serviceGraduation.failedPages
		indexCurrentPage = 0	
	 }
	 
	 /**
	  * Méthode qui sauvegarde les modifications et quitte la fenêtre
	  */
	 def void saveAndQuit(){
	 	println("save and quit")
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
	 }
	 
	 /**
	  * Méthode qui affiche la page suivante
	  */
	  def void nextPage(){
	  	if(indexCurrentPage < failedPages.size)
	  		indexCurrentPage++
	  	else
	  		indexCurrentPage = 0
	 	println(indexCurrentPage + " next page")
	  }
	  
	 /**
	  * Méthode qui affiche la page précédente
	  */
	  def void previousPage(){
	 	if(indexCurrentPage > 0)
	  		indexCurrentPage--
	  	else
	  		indexCurrentPage = failedPages.size-1
	  	
	  	println(indexCurrentPage + " previous page")
	  } 
}