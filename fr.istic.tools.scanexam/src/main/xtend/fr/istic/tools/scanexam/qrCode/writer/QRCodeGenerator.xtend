package fr.istic.tools.scanexam.qrCode.writer

interface QRCodeGenerator {
	
	/**
	 * Créer toutes les copies d'examen en y insérant les QrCodes correspondant dans chaque pages
	 * 
	 * @param inputFile Chemin du sujet maitre
	 * @param outpuFile Chemin ou mettre le sujet avec QRCode
	 * @param nbCopies Nombre de copies de l'examen souhaité
	 */
	def void createAllExamCopies(String inputFile, int nbCopie)
}
