package fr.istic.tools.scanexam.qrCode.writer

import java.io.InputStream

interface QRCodeGenerator {
	
	/**
	 * Créer toutes les copies d'examen en y insérant les QrCodes correspondant dans chaque pages
	 * 
	 * @param inputFile Chemin du sujet maitre
	 * @param nbCopies Nombre de copies de l'examen souhaité
	 * 
	 * @return true si l'opération s'est bien déroulée
	 */
	def boolean createAllExamCopies(InputStream inputFile, int nbCopie)
}
