package fr.istic.tools.scanexam.qrCode.writer

import java.io.InputStream
import java.io.File
import fr.istic.tools.scanexam.core.QrCodeZone

interface QRCodeGenerator {

	/**
	 * Créer toutes les copies d'examen en y insérant les QrCodes correspondant dans chaque pages
	 * 
	 * @param inputFile Chemin du sujet maitre
	 * @param outputPath chemin de sortie
	 * @param qrCodeZone zone sur le document où insérer le qrcode
	 * @param idExam l'id de l'examen
	 * @param nbCopies Nombre de copies de l'examen souhaité
	 */
	def void createAllExamCopies(InputStream inputFile, File outputPath, QrCodeZone qrCodeZone, String idExam,
		int nbCopie)

	def boolean isFinished()

	def int getNbTreated()

	def int getNumberPagesAllSheets()
}
