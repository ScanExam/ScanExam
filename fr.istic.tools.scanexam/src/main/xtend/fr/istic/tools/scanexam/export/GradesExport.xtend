package fr.istic.tools.scanexam.export

/**
 * Exportateur de notes au format Excel
 * @author Romain
 */
interface GradesExport {
	
	/**
	 * Methode qui créer un fichier Excel et qui le remplit avec les noms des étudiants et leurs notes
	 */
	def void exportGrades()
}