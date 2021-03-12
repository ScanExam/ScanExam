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
	
	/**
	 * Méthode permettant d'envoyer les notes par mail
	 * @param user Adresse mail de l'utilisateur
	 * @param password mot de passe de l'adresse mail
	 * @param path chemin d'accés au fichier XMI permettant de faire la correspondance nom de l'élève -> Adresse mail
	 */
	 def void exportGradesMail(String user, String password, String path)
}