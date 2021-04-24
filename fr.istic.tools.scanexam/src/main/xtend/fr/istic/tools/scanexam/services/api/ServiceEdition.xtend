package fr.istic.tools.scanexam.services.api

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.Optional

interface ServiceEdition extends Service {
	
	/**
	 * Permet de lier une Question q à une zone du PDF définie par un Rectangle R
	 * @param q Une Question
	 * @param r Un Rectangle
	 * @author degas
	 */
	def int createQuestion(int pdfPageIndex, float x, float y, float heigth, float width)

	/**
	 * Redimensionne la zone d'une Question
	 * @param id l'ID de la question dont la zone doit être redimensionnée
	 * @param heigth la nouvelle hauteur de la zone
	 * @param width la nouvelle largeur de la zone
	 */
	def void rescaleQuestion(int id, float heigth, float width)

	/**
	 * Déplace la zone d'une Question
	 * @param id l'ID de la question dont la zone doit être déplacée
	 * @param x la nouvelle position x de la zone
	 * @param y la nouvelle position y de la zone
	 */
	def void moveQuestion(int id, float x, float y)
	
	/**
	 * Renomme la Question
	 * @param id l'ID de la question à renommer
	 * @param name le nouveau nom de la question
	 */
	def void renameQuestion(int id, String name)

	/**
	 * Supprime une question
	 * @param id l'ID de la question à supprimer
	 * @return true si l'élément a bien été supprimé, false sinon
	 */
	def boolean removeQuestion(int id)
	

	/**
	 * Modifie la note maximal que l'on peut attribuer a une question.
	 * @param questionId l'ID de la question a laquelle on veut modifier la note maximal possible
	 * @param maxPoint note maximal de la question question a ajouter
	 */
	def void modifyMaxPoint(int questionId, float maxPoint)
	
	/**
	 * Sauvegarde le fichier modèle d'examen sur le disque
	 * @param path L'emplacement de sauvegarde du fichier
	 * @param pdfOutputStream le contenu du fichier sous forme de Stream
	 */
	def void save(ByteArrayOutputStream outputStream, File path)

	/**
	 * Charge un fichier modèle d'examen a partir du disque
	 * @param xmiPath L'emplacement du fichier XMI
	 * @returns un flux vers le contenu du fichier si celui-ci a bien été ouvert, Optional.empty sinon
	 */
	def Optional<ByteArrayInputStream> open(String xmiPath) 
	
	/**
	 * Crée et initialise un nouveau modèle d'Examen
	 * @param pageNumber le nombre de pages du modèle
	 */
	def void initializeEdition(int pageNumber)

}