package fr.istic.tools.scanexam.qrCode.reader

import java.util.Set

interface PDFReader {
	/**
	 * Lit le PDF spécifié au constructeur et le rentre dans une structure de donnée
	 */
	def void readPDf()
	
	/**
	 * @return un set de copies extraites du PDF
	 */
	def Set<Copie> getSheets()
}
