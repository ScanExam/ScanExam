package fr.istic.tools.scanexam.exportation

import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map

import static fr.istic.tools.scanexam.config.LanguageManager.translate

/** 
 * Converti les détails de la note en page HTML
 * @author Julien Cochet
 */
class GradeDetailToHtml {

	// ----------------------------------------------------------------------------------------------------
	/*
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	/* Titre de l'examen */
	String title
	/* Nom de l'étudiant */
	String student
	/* Note de l'examen */
	float grade
	/* Barème de l'examen */
	float scale
	/* Liste des questions */
	List<QuestionDetail> questions
	/* Nom du fichier css utilisé */
	String cssName

	// ----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * Constructeur
	 * @param title   Titre de l'examen
	 * @param student String student
	 * @param grade   Note de l'examen
	 * @param scale   Barème de l'examen
	 */
	new(String title, String student, float grade, float scale) {
		this.title = title
		this.student = student
		this.grade = grade
		this.scale = scale
		this.questions = new ArrayList()
	}

	/** 
	 * Ajoute une question aux détails de la note avec des points obtenus et manqués
	 * @param name     Nom de la question
	 * @param grade    Note attribué
	 * @param scale    Barème de la question
	 * @param achieved Points obtenus
	 * @param missed   Points manqués
	 */
	def void addQuestion(String name, double grade, double scale, Map<String, Float> achieved,
		Map<String, Float> missed) {
		questions.add(new QuestionDetail(name, grade, scale, achieved, missed))
	}

	/** 
	 * Ajoute une question aux détails de la note avec des points obtenus seulement
	 * @param name     Nom de la question
	 * @param grade    Note attribué
	 * @param scale    Barème de la question
	 * @param achieved Points obtenus
	 */
	def void addQuestionAchievedOnly(String name, double grade, double scale, Map<String, Float> achieved) {
		addQuestion(name, grade, scale, achieved, new HashMap())
	}

	/** 
	 * Ajoute une question aux détails de la note avec des points manqués seulement
	 * @param name   Nom de la question
	 * @param grade  Note attribué
	 * @param scale  Barème de la question
	 * @param missed Points manqués
	 */
	def void addQuestionMissedOnly(String name, double grade, double scale, Map<String, Float> missed) {
		addQuestion(name, grade, scale, new HashMap(), missed)
	}

	/** 
	 * Retire une question aux détails de la note
	 * @param i Index de la question à retirer
	 */
	def void removeQuestion(int i) {
		questions.remove(i)
	}

	/** 
	 * Converti les détails de la note en html
	 * @return Détails de la note sous-forme d'html
	 */
	def String toHtml() {
		var String html = '''<h1>«title» – «student»</h1>'''
		html += '''<h2>«translate("gradeDetail.note")»«grade»/«scale»</h2>'''
		for (QuestionDetail question : questions) {
			html += question.toHtml()
		}
		return html
	}

	/** 
	 * Converti les détails de la note en page html
	 * @return Détails de la note sous-forme de page html
	 */
	def String toHtmlPage() {
		var String html = "<!DOCTYPE html>"
		html += "<html><head><meta charset=\"utf-8\"></meta>"
		if (cssName !== null && cssName !== "") {
			html += "<link href=\"" + cssName + "\" rel=\"stylesheet\" type=\"text/css\"></link>"
		}
		html += '''<title>«title» – «student»</title>'''
		html += "</head><body>"
		html += toHtml()
		html += "</body></html>"
		return html
	}

	/**
	 * Retourne le nom du fichier css utilisé
	 * @return Nom du fichier css utilisé
	 */
	def String getCssName() {
		return this.cssName
	}

	/**
	 * Met à jour le nom du fichier css utilisé. Laisser vide pour ne pas utiliser de css
	 * @param cssName Nom du fichier css avec son extension
	 */
	def setCssName(String cssName) {
		this.cssName = cssName
	}

}
