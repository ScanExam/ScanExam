package fr.istic.tools.scanexam.services

import fr.istic.tools.scanexam.core.CoreFactory
import fr.istic.tools.scanexam.core.templates.CreationTemplate
import fr.istic.tools.scanexam.core.templates.TemplatesFactory
import fr.istic.tools.scanexam.io.TemplateIo
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.Base64
import java.util.Optional
import org.eclipse.xtend.lib.annotations.Accessors

import static fr.istic.tools.scanexam.services.ExamSingleton.*
import fr.istic.tools.scanexam.core.QuestionZone
import java.io.File

/**
 * Classe servant de façade aux données concernant la création du modèle
 * @author Antoine Degas, Marius Lumbroso, Théo Giraudet
 */
class ServiceEdition extends Service
{
	CreationTemplate template;

	@Accessors int questionId;

	/**
	 * Permet de lier une Question q à une zone du PDF définie par un Rectangle R
	 * @param q Une Question
	 * @param r Un Rectangle
	 * @author degas
	 */
	def int createQuestion(int pdfPageIndex, float x, float y, float heigth, float width) {

		val question = CoreFactory.eINSTANCE.createQuestion();
		question.id = questionId;
		question.gradeScale = CoreFactory.eINSTANCE.createGradeScale();
		question.zone = CoreFactory.eINSTANCE.createQuestionZone();
		question.zone.x = x
		question.zone.y = y
		question.zone.width = width
		question.zone.heigth = heigth
		ExamSingleton.getPage(pdfPageIndex).questions.add(question);
		return questionId++;
	}

	/**
	 * Redimensionne la zone d'une Question
	 * @param id l'ID de la question dont la zone doit être redimensionnée
	 * @param heigth la nouvelle hauteur de la zone
	 * @param width la nouvelle largeur de la zone
	 */
	def rescaleQuestion(int id, float heigth, float width) {
		val question = getQuestion(id);
		question.zone.width = width
		question.zone.heigth = heigth
	}

	/**
	 * Déplace la zone d'une Question
	 * @param id l'ID de la question dont la zone doit être déplacée
	 * @param x la nouvelle position x de la zone
	 * @param y la nouvelle position y de la zone
	 */
	def moveQuestion(int id, float x, float y) {
		val question = getQuestion(id)
		question.zone.x = x
		question.zone.y = y
	}
	
	/**
	 * Renomme la Question
	 * @param id l'ID de la question à renommer
	 * @param name le nouveau nom de la question
	 */
	def renameQuestion(int id, String name) {
		val question = getQuestion(id)
		question.name = name
	}

	/**
	 * Supprime une question
	 * @param id l'ID de la question à supprimer
	 */
	def removeQuestion(int id) {
		for (page : ExamSingleton.instance.pages)
			for (question : page.questions)
				if (question.id == id)
					page.questions.remove(question)
	}

	/**
	 * Modifie la note maximal que l'on peut attribuer a une question.
	 * @param questionId, l'ID de la question a laquelle on veut modifier la note maximal possible
	 * @param maxPoint, note maximal de la question question a ajouter
	 */
	def modifyMaxPoint(int questionId, float maxPoint) {
		val scale = getQuestion(questionId).gradeScale
		if (maxPoint > 0) {
			scale.maxPoint = maxPoint
		}
	}

	/**
	 * Sauvegarde le fichier modèle d'examen sur le disque
	 * @param path L'emplacement de sauvegarde du fichier
	 * @param pdfOutputStream le contenu du fichier sous forme de Stream
	 */
	def save(ByteArrayOutputStream outputStream, File path) {
		val encoded = Base64.getEncoder().encode(outputStream.toByteArray());
		template.encodedDocument = new String(encoded);
		outputStream.close();

		template.exam = ExamSingleton.instance

		TemplateIo.save(path, template);
	}

	/**
	 * Charge un fichier modèle d'examen a partir du disque
	 * @params xmiPath L'emplacement du fichier.
	 * @returns un flux vers le contenu du fichier si celui-ci a bien été ouvert, Optional.empty sinon
	 */
	def Optional<ByteArrayInputStream> open(String xmiPath) {
		val creationTemplate = TemplateIo.loadCreationTemplate(xmiPath)

		if (creationTemplate.present) {
			this.template = creationTemplate.get()
			ExamSingleton.instance = creationTemplate.get().exam
			val decoded = Base64.getDecoder().decode(creationTemplate.get().encodedDocument);

			questionId = ExamSingleton.instance.pages.stream.map[page|page.questions.size].reduce[acc, num|acc + num].
				get + 1
			return Optional.of(new ByteArrayInputStream(decoded));
		}
		return Optional.empty;
	}

	/**
	 * Crée un nouveau modèle côté données
	 * @param pageNumber le nombre de pages du modèle
	 */
	override void onDocumentLoad(int pageNumber) {
		template = TemplatesFactory.eINSTANCE.createCreationTemplate

		ExamSingleton.instance = CoreFactory.eINSTANCE.createExam()

		for (i : 0 ..< pageNumber) {
			val page = CoreFactory.eINSTANCE.createPage()

			ExamSingleton.instance.pages.add(page);
		}
		questionId = 0
	}

	/** Retourne la zone associée à une question
	 * @param index Index de la question //FIXME (useless?)
	 * @author degas
	 */
	def QuestionZone getQuestionZone(int pageIndex, int questionIndex) {
		return ExamSingleton.getQuestion(pageIndex, questionIndex).zone
	}
}
