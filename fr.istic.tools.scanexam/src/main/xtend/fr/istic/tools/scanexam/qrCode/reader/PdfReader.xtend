package fr.istic.tools.scanexam.qrCode.reader

import fr.istic.tools.scanexam.core.StudentSheet
import java.util.Collection

interface PdfReader {

	/**
	 * Lit le PDF spécifié
	 * @return true ssi la lecture du PDF est réussie
	 */
	def boolean readPDf()

	/**
	 * Renvoie la collection des copies complètes uniquement au format de l'API
	 * @return la collection des copies complètes au format de l'API
	 */
	def Collection<StudentSheet> getCompleteStudentSheets()

	/**
	 * Renvoie la collection des copies incomplètes uniquement au format de l'API
	 * @return la collection des copies incomplètes au format de l'API
	 */
	def Collection<StudentSheet> getUncompleteStudentSheets()

	/**
	 * Renvoie le nombre de total de pages du PDF de toutes les copies
	 * @return le nombre de pages du PDF source
	 */
	def int getNbPagesPdf()

	/**
	 * Renvoie le nombre de pages traitées par la lecture du PDF
	 * @return le nombre de pages que le reader a lu du PDF source
	 */
	def int getNbPagesTreated()
}
