package fr.istic.tools.scanexam.mailing

/**
 * Interface pour l'envoie des notes par mail
 * @author Degas
 */
interface GradesExportMail {

	/**
	 * Interface pour l'envoie des notes par mail
	 * @author Degas
	 */
	/**
	 * Méthode permettant d'envoyer les notes par mail
	 * @param user Adresse mail de l'utilisateur
	 * @param password mot de passe de l'adresse mail
	 * @param path chemin d'accés au fichier XMI permettant de faire la correspondance nom de l'élève -> Adresse mail
	 */
	def void exportGradesMail(String user, String password, String path)
}
