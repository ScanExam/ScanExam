package fr.istic.tools.scanexam.presenter

import java.io.File
import fr.istic.tools.scanexam.services.ServiceGraduation
import fr.istic.tools.scanexam.mailing.StudentDataManager

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
	
	
	
	new(ServiceGraduation graduation) {
		service = graduation
	}
	
	
	/**
	 * Envoie les informations au service
	 * @param file Chemin du fichier contenant la liste des étudiants
	 * @param firstCell Première case à prendre en compte
	 * @return true si le fichier a bien pu être chargé, false sinon
	 */
	def boolean loadFile(File file, String firstCell) {
		StudentDataManager.loadData(file, firstCell)
		service.studentListPath = file.absolutePath
		service.studentListShift = firstCell
		true
	}
	
}
