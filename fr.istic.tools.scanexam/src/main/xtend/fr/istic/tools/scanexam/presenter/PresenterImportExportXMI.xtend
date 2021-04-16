package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.services.api.ServiceGraduation
import java.util.Objects

/**
 * Presenter used to import and export xmi files from the view
 * @author Benjamin Danlos
 */
class PresenterImportExportXMI {
	
	val ServiceGraduation service
	
	/**
	 * Constructor
	 * @param graduation instance of ServiceGraduation (not null)
	 */
	new(ServiceGraduation graduation) {
		Objects.requireNonNull(graduation)
		service = graduation
	}
	
	/**
	 * utilisé pour charger une sauvegarde de correction d'examen
	 * Charge un fichier de correction d'examen a partir du disque.
	 * @params pathXmiFile L'emplacement du fichier.
	 * @returns "true" si le fichier a bien été chargé, "false"
	 */
	def loadExamCorrectionTemplate(String pathXmiFile){
		Objects.requireNonNull(pathXmiFile)
		if(service.openCorrectionTemplate(pathXmiFile)){
			//requireViewUpdate()
			return true
		}else{
			return false
		}	
	}
	
	/**
	 * utilisé pour charger une sauvegarde de création d'examen
	 * Charge un fichier de creation d'examen a partir du disque.
	 * @params pathXmiFile L'emplacement du fichier.
	 * @returns "true" si le fichier a bien été chargé, "false"
	 */
	def loadExamCreationTemplate(String pathXmiFile){
		Objects.requireNonNull(pathXmiFile)
		if(service.openCreationTemplate(pathXmiFile)){
			//requireViewUpdate()
			return true
		}else{
			return false
		}	
	}
	
	
	/**
	 * utilisé pour sauvegarder une correction d'examen en cours
 	 * Sauvegarde le fichier de correction d'examen sur le disque.
	 * @params pathXmiFile L'emplacement du fichier.
	 * @returns "true" si le fichier a bien été chargé, "false"
	 */
	 //méthode du service pas implémentée donc ca fait une erreur et je vais pas push un erreur voyons
	/*def saveExamCorrectionTemplate(String pathXmiFile){
		Objects.requireNonNull(pathXmiFile)
		if(service.saveCorrectionTemplate(pathXmiFile)){
			//requireViewUpdate()
			return true
		}else{
			return false
		}	
	}*/
	
	/**
	 * utilisé pour sauvegarder une création d'examen en cours
	 * Sauvegarde le fichier de creation d'examen sur le disque.
	 * @params pathXmiFile L'emplacement du fichier.
	 * @returns "true" si le fichier a bien été chargé, "false"
	 */
	 // methode du service pas implémentée non plus
	/*def saveExamCreationTemplate(String pathXmiFile){
		Objects.requireNonNull(pathXmiFile)
		if(service.saveCreationTemplate(pathXmiFile)){
			//requireViewUpdate()
			return true
		}else{
			return false
		}
	}*/
	
}