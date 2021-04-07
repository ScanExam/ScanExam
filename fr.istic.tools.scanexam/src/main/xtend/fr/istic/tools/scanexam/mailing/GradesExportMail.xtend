package fr.istic.tools.scanexam.mailing

import java.io.File
import java.util.Collection
import fr.istic.tools.scanexam.core.StudentSheet

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
	 * @param path chemin d'accès au fichier XMI permettant de faire la correspondance nom de l'élève -> Adresse mail
	 */
	def void exportGradesMail(File pdf)
	
	def void exportGradesMail1(File pdf, String mail, String mdp, int taille,String nameExam, Collection<StudentSheet> studentSheetss)
}
