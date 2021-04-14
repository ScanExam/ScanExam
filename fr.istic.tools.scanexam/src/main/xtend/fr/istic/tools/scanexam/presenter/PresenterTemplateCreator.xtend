package fr.istic.tools.scanexam.presenter

import java.io.File

/**
 * Presenter pourla création d'un modèle d'examen
 * @author Julien Cochet
 */
class PresenterTemplateCreator {
	
	/* Presenter de l'édition de l'examen */
	val PresenterEdition presenter
	
	/* Nom du modèle */
	var String templateName
	
	/* Fichier du modèle */
	var File templateFile
	
	/**
	 * Constructeur
	 * @param presenter Presenter de l'édition de l'examen
	 */
	new (PresenterEdition presenter) {
		this.presenter = presenter
	}
	
	/**
	 * Crée le modèle à partir du pdf de l'examen
	 * @param file Pdf de l'examen
	 */
	def void createTemplate() {
		
		presenter.getPresenterPdf.create(templateName, templateFile)
	}
	
	/**
	 * Met à jour le nom du modèle
	 * @param templateFile Nouveau nom du modèle
	 */
	def void setTemplateName(String templateName) {
		this.templateName = templateName
	}
	
	/**
	 * Met à jour le fichier pdf de l'examen
	 * @param templateFile Nouveau pdf de l'examen
	 */
	def void setTemplateFile(File templateFile) {
		this.templateFile = templateFile
	}
	
	/**
	 * Vérifie que le nom du modèle est valide
	 * @param name Nom du modèle
	 * @return true si le nom est valide, false sinon
	 */
	def boolean checkName(String name) {
		true
	}
	
	/**
	 * Vérifie que le chemin du fichier du modèle est valide
	 * @param filepath Chemin du fichier
	 * @return true si le chemin du fichier est valide, false sinon
	 */
	def boolean checkFilepath(String filepath) {
		true
	}
	
}