package fr.istic.tools.scanexam.qrCode.writer

import java.io.InputStream

interface QRCodeGenerator {
	
	/**
	 * Créer toutes les copies d'examen en y insérant les QrCodes correspondant dans chaque pages
	 * 
	 * @param inputFile Chemin du sujet maitre
	 * @param outputPath chemin de sortie
	 * @param idExam l'id de l'examen
	 * @param nbCopies Nombre de copies de l'examen souhaité
	 */
	def void createAllExamCopies(InputStream inputFile, InputStream outputPath, String idExam, int nbCopie)
}
