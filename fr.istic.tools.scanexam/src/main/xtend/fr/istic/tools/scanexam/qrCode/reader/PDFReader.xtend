package fr.istic.tools.scanexam.qrCode.reader

import fr.istic.tools.scanexam.core.StudentSheet
import java.util.Collection

interface PDFReader {
	
	/**
	 * Lit le PDF spécifié
	 */
	def void readPDf()
	
	/**
	 * Renvoie la collection des copies complètes
	 */
	def Collection<StudentSheet> getStudentSheets()
	
	/**
	 * 
	 */
	def int getNbPagesPdf()
	
	/**
	 * 
	 */
	def int getNbPagesTreated()
}