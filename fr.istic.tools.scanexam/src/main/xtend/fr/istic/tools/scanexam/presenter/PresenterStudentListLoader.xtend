package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.mailing.StudentDataManager
import fr.istic.tools.scanexam.services.ServiceGraduation
import java.io.File

/**
 * Classe pour charger la liste des élèves
 * @author Julien Cochet
 */
class PresenterStudentListLoader {
	
	final ServiceGraduation service
	
	// ----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	enum LoadState {
		SUCCESS,
		X_NOT_VALID,
		Y_NOT_VALID
	}
	
	new(ServiceGraduation graduation) {
		service = graduation
	}
	
	
	/**
	 * Envoie les informations au service
	 * @param file Chemin du fichier contenant la liste des étudiants
	 * @param firstCell Première case à prendre en compte
	 * @return un LoadState représentant l'état terminal du chargement des données
	 */
	def LoadState loadFile(File file, String firstCell) {
		if(!StudentDataManager.isValidX(firstCell))
			return LoadState.X_NOT_VALID
		else if(!StudentDataManager.isValidY(firstCell))
			return LoadState.Y_NOT_VALID
			
		StudentDataManager.loadData(file, firstCell)
		service.studentListPath = file.absolutePath
		service.studentListShift = firstCell
		return LoadState.SUCCESS
	}
	
	/**
	 * @return le nombre de paires parsée par StudentDataManager, -1 si aucune n'a été parsée
	 */
	def int getNumberPair() {
		StudentDataManager.getNameToMailMap().map(map | map.size).orElse(-1)
	}
	
	/**
	 * @return la liste des données parsées sous forme de String. Chaîne vide si aucune données n'a été parsée
	 */
	def String getStudentList() {
		StudentDataManager.getNameToMailMap()
			.map(map | map.entrySet
					   .map(entry | entry.key + " - " + entry.value)
					   .join("\n"))
			.orElse("")
	}
	
	/**
	 * @return le path vers le fichier contenant la liste des étudiants. Chaîne vide si celui n'est pas défini
	 */
	def String getStudentListPath() {
		return ""
		//return service.studentListPath === null ? "" : service.studentListPath
	}
	
	/**
	 * @return la première case à prendre en compte dans le fichier contenant la liste des étudiants
	 */
	def String getStudentListShift() {
		return "A1"
		//return service.studentListShift
	}
}
