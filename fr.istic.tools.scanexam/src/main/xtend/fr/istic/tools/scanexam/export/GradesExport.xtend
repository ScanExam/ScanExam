package fr.istic.tools.scanexam.export

import fr.istic.tools.scanexam.core.StudentSheet
import java.io.File
import java.util.Collection

/**
 * Exportateur de notes au format Excel
 * @author Romain
 */
interface GradesExport {
	
	/**
	 * Methode qui créer un fichier Excel et qui le remplit avec les noms des étudiants et leurs notes
	 */
	def void exportGrades(Collection<StudentSheet> studentSheets, File file)
}