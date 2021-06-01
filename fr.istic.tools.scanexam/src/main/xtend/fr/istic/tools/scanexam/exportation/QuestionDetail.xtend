package fr.istic.tools.scanexam.exportation

import java.util.HashMap
import java.util.Map
import java.util.Map.Entry

import static fr.istic.tools.scanexam.config.LanguageManager.translate

/** 
 * Classe contenant toutes les informations lié à une question
 * @author Julien Cochet
 */
class QuestionDetail {

	// ----------------------------------------------------------------------------------------------------
	/*
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	/* Nom de la question */
	String name
	/* Note attribuée */
	double grade
	/* Barème de la question */
	double scale
	/* Points obtenus (commentaire associé, et nombre de points) */
	Map<String, Float> achieved
	/* Points manqués (commentaire associé, et nombre de points) */
	Map<String, Float> missed

	// ----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * Constructeur
	 * @param name     Nom de la question
	 * @param grade    Note attribué
	 * @param scale    Barème de la question
	 * @param achieved Points obtenus
	 * @param missed   Points manqués
	 */
	new(String name, double grade, double scale, Map<String, Float> achieved, Map<String, Float> missed) {
		this.name = name
		this.grade = grade
		this.scale = scale
		this.achieved = new HashMap(achieved)
		this.missed = new HashMap(missed)
	}

	/** 
	 * Converti les informations de la question en contenu de tableau html
	 * @return Informations de la question sous-forme de contenu de tableau html
	 */
	def String toHtml() {
		var String html = ""
		if (achieved.size() > 0 || missed.size() > 0) {
			html += '''<h3>«name» : «grade»/«scale»</h3>'''
			if (achieved.size() > 0) {
				html += '''<p>«translate("gradeDetail.achievedPoints")»</p>'''
				html += "<table><tbody class=\"achieved\">"
				for (Entry<String, Float> entry : achieved.entrySet()) {
					html +=
						'''<tr><td>«entry.getKey()»</td><td>+«entry.getValue()»«translate("gradeDetail.pointAbbreviation")»</td></tr>'''
				}
				html += "</tbody></table>"
			}
			if (missed.size() > 0) {
				html += '''«translate("gradeDetail.missedPoints")»'''
				html += "<table><tbody class=\"missed\">"
				for (Entry<String, Float> entry : missed.entrySet()) {
					html +=
						'''<tr><td>«entry.getKey()»</td><td>«entry.getValue()»«translate("gradeDetail.pointAbbreviation")»</td></tr>'''
				}
				html += "</tbody></table>"
			}
		}
		return html
	}
}
