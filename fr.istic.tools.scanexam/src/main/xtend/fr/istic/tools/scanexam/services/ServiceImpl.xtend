package fr.istic.tools.scanexam.services

import fr.istic.tools.scanexam.api.DataFactory
import fr.istic.tools.scanexam.core.CoreFactory
import fr.istic.tools.scanexam.core.GradeEntry
import fr.istic.tools.scanexam.core.Page
import fr.istic.tools.scanexam.core.Question
import fr.istic.tools.scanexam.core.QuestionZone
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.core.templates.CorrectionTemplate
import fr.istic.tools.scanexam.core.templates.CreationTemplate
import fr.istic.tools.scanexam.core.templates.TemplatesFactory
import fr.istic.tools.scanexam.io.TemplateIo
import fr.istic.tools.scanexam.services.api.ServiceEdition
import fr.istic.tools.scanexam.services.api.ServiceGraduation
import fr.istic.tools.scanexam.utils.Tuple3
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.ArrayList
import java.util.Base64
import java.util.Collection
import java.util.Collections
import java.util.List
import java.util.Objects
import java.util.Optional
import org.eclipse.xtend.lib.annotations.Accessors
import org.apache.logging.log4j.LogManager

/**
 * Classe servant de façade aux données concernant la correction
 * @author Antoine Degas, Marius Lumbroso, Théo Giraudet, Thomas Guibert
 */
class ServiceImpl implements ServiceGraduation, ServiceEdition {
	static val logger = LogManager.logger
	/**
	 * Index de la page courante du modèle d'exam
	 */
	protected int pageIndex 
	
	
	/**
	 * La page actuelle de l'examen
	 */
	int currentSheetIndex;
	 
	/**
	 * Question actuelle.
	 */
	int currentQuestionIndex;
	
	int gradeEntryId;
	
	/**
	 * Fichier du template de l'édition d'examen (Fichier de méta données sur le sujet d'examen)
	 */
	CreationTemplate editionTemplate;
	
	/**
	 * Fichier du template de correction d'examen  
	 * (Fichier de méta données sur les corrections de copies déja effectués)
	 */
	CorrectionTemplate graduationTemplate;
	

	/**
	 * Sauvegarde le fichier de correction d'examen sur le disque.
	 * @param path L'emplacement de sauvegarde du fichier.
	 * @param pdfOutputStream le contenu du fichier sous forme de Stream
	 */
	override saveCorrectionTemplate(String path,ByteArrayOutputStream pdfOutputStream) 
	{
		val encoded = Base64.getEncoder().encode(pdfOutputStream.toByteArray());
		graduationTemplate.encodedDocument = new String(encoded);
		pdfOutputStream.close();

		graduationTemplate.studentsheets.clear()
		graduationTemplate.studentsheets.addAll(studentSheets);
		
		TemplateIo.save(new File(path), graduationTemplate);
	}
	
	/**
	 * Charge un fichier de correction d'examen a partir du disque.
	 * @params path L'emplacement du fichier.
	 * @returns "true" si le fichier a bien été chargé, "false"
	 */
	override boolean openCorrectionTemplate(String xmiFile)
	{
		val correctionTemplate = TemplateIo.loadCorrectionTemplate(xmiFile) 
		
		if (correctionTemplate.present) 
        {
            this.graduationTemplate = correctionTemplate.get()
            return true
        }
		return false
	}
	
	
	/**
	 * Charge le document PDF des copies manuscrites,  corrigés
	 * @params path L'emplacement du fichier.
	 * @returns "true" si le fichier a bien été chargé, "false"
	 */
	override boolean initializeCorrection(Collection<StudentSheet> studentSheets) {
		graduationTemplate = TemplatesFactory.eINSTANCE.createCorrectionTemplate
		try {

			for (StudentSheet sheet : studentSheets) {
				for (var i = 0; i < templatePageAmount; i++) {
					val examPage = getPage(i);

					println("test size : " + examPage.questions.size)
					for (var j = 0; j < examPage.questions.size; j++) // TODO +1?
					{
						sheet.grades.add(CoreFactory.eINSTANCE.createGrade());
					}

				}

			}
			graduationTemplate.studentsheets.addAll(studentSheets)
			return true
		} catch (Exception ex) {
			return false;
		}

	}
	
	//TODO si les pages sont dans le désordre ?
	override int getAbsolutePageNumber(int studentId, int offset) {
		val pageId = studentSheets.findFirst[x | x.id == studentId].posPage.get(0);
		return pageId + offset;
	}
	
	//===================================================
	//          		 StudentSheet
	//===================================================
	
	/**
	 * Défini la copie d'étudiant suivant la copie actuelle comme nouvelle copie courante
	 */
	override nextSheet() {
		if (currentSheetIndex + 1 < studentSheets.size)
			currentSheetIndex++
	}

	/**
	 * Défini la copie d'étudiant précédant la copie actuelle comme nouvelle copie courante
	 */
	override previousSheet() {
		if (currentSheetIndex > 0)
			currentSheetIndex--
	}
	
	/**
	 * Associe un nouveau identifiant d'étudiant à la copie courante
	 * @param id le nouvel identifiant d'étudiant
	 */
	override assignStudentId(String id) {
		studentSheets.get(currentSheetIndex).studentName = id
	}
	
	//===================================================
	//          			 Page
	//===================================================
	
	
	/**
	 * @return l'index de la page courante du modèle d'exam
	 */
	private def getCurrentPage()
	{
		return getPage(pageIndex);
	}
	
	/**
	 * Défini la page suivant la page actuelle comme nouvelle page courante
	 */
	override nextPage() {
		if (pageIndex + 1 < editionTemplate.exam.pages.length)
			pageIndex++
	}

	/**
	 * Défini la page précédant la page actuelle comme nouvelle page courante
	 */
	override previousPage() {
		if (pageIndex > 0) 
			pageIndex--;
	}
	
	/**
	 * @return le nombre de pages de l'Examen
	 */
	override int getPageAmount() {
		return getTemplatePageAmount
	}
	
	//===================================================
	//          		  Question
	//===================================================
	
	/**
	 * Défini la question suivant la question actuelle comme nouvelle question courante
	 */
	override nextQuestion()
	{
		if (currentQuestionIndex + 1 < currentPage.questions.size)
			currentQuestionIndex++
	}
	
	/**
	 * Défini la question précédant la question actuelle comme nouvelle question courante
	 */
	override previousQuestion() {
		if (currentQuestionIndex > 0)
			currentQuestionIndex--
	}
	
	/**
	 * Défini pour question courante la question dont l'ID est passé en paramètre si celle-ci existe, et défini pour page courante la page où se trouve cette question.<br/>
	 * Ne fait rien si la question n'existe pas 
	 * @param id un ID de question
	 */
	override selectQuestion(int id) {
		for(page: editionTemplate.exam.pages) {
			val question = page.questions.findFirst[question | question.id == id]
			if(question !== null) {
				pageIndex = page.id
				currentQuestionIndex = question.id
			}
		}
	}
	
	
	/**
	 * @return le nombre de questions d'une copie d'étudiant
	 */
	override numberOfQuestions() {
		var nbQuestion = 0
		for (var i = 0; i < editionTemplate.exam.pages.size(); i++)
			nbQuestion += editionTemplate.exam.pages.get(i).questions.size
		nbQuestion
	}
	
	//===================================================
	//                   GradeEntry
	//===================================================
	
	/**
	 * Ajoute une nouvelle entrée à la liste des points attribuable à la question
	 * @param questionId l'ID de la question dans laquelle ajouter l'entrée
	 * @param desc la description de l'entrée
	 * @param point le nombre de point de l'entrée
	 * @return l'ID de l'entrée
	 */
	override int addEntry(int questionId, String desc, float point) 
	{
		val DataFactory factory = new DataFactory
		val question = getQuestion(questionId)
		if(question.gradeScale === null)
			question.gradeScale = factory.createGradeScale
		val scale = question.gradeScale
		scale.steps.add(factory.createGradeEntry(gradeEntryId, desc, point))
		gradeEntryId++
	}
	
	/**
	 * Modifie une entrée de la liste des points attribuable à la question
	 * @param questionId l'ID de la question dans laquelle modifier l'entrée
	 * @param gradeEntryId l'ID de l'entrée à modifier
	 * @param desc la nouvelle description de l'entrée
	 * @param point le nouveau nombre de point de l'entrée
	 */
	override modifyEntry(int questionId, int gradeEntryId, String desc, float point) {
		val scale = getQuestion(questionId).gradeScale
		val scaleEntry = scale.steps.findFirst[step | step.id == gradeEntryId]
		if(scaleEntry !== null) {
			scaleEntry.header = desc
			scaleEntry.step = point
		}				
	}
	
	/**
	 * Supprime une entrée de la liste des points attribuable à la question
	 * @param questionId l'ID de la question dans laquelle supprimer l'entrée
	 * @param gradeEntryId l'ID de l'entrée à supprimer
	 */
	override removeEntry(int questionId, int gradeEntryId) {
		val scale = getQuestion(questionId).gradeScale
		val scaleEntry = scale.steps.findFirst[step | step.id == gradeEntryId]
		if(scaleEntry !== null)
			scale.steps.remove(scaleEntry)
	}
	
	/**
     * @param l'ID de la question à laquelle récupérer la liste d'entrées de note
	 * @return une liste d'ID d'entrées pour la question de l'examen dont l'ID est <i>questionId</i>
	 */
	override List<Tuple3<Integer, String, Float>> getQuestionGradeEntries(int questionId) {
		if (getQuestion(questionId).gradeScale !== null)
			return getQuestion(questionId).gradeScale.steps
			.map[entry | Tuple3.of(entry.id, entry.header, entry.step)]
		return List.of
	}
	

	//===========================================================
	// Manipulation d'un GradeEntry à une note d'un StudentSheet
	//===========================================================
	
	
	/**
	 * Ajoute une entrée (GradeItem) à la note d'une question d'une copie
	 * @param questionId l'ID de la question à laquelle ajouter l'entrée
	 * @param l'ID de l'entrée dans l'Examen
	 * @return boolean indique si les points on bien ete attribuer
	 */
	override boolean assignGradeEntry(int questionId, int gradeEntryId) {
		val gradeEntry = getQuestion(questionId).gradeScale.steps.findFirst[entry|entry.id == gradeEntryId]
		if (validGradeEntry(questionId, gradeEntry)) {
			val sheet = studentSheets.get(currentSheetIndex);
			sheet.grades.get(questionId).entries.add(gradeEntry)
			return true
		} else {
			return false
		}
	}
	
	/**
	 * Retire une entrée à la note d'une question d'une copie
	 * @param questionId l'ID de la question à laquelle retirer l'entrée
	 * @param l'ID de l'entrée dans l'Examen
	 */
	override retractGradeEntry(int questionId, int gradeEntryId) {
		val entries = studentSheets.get(currentSheetIndex).grades.get(questionId).entries
		val gradeEntry = entries.findFirst[entry | entry.id == gradeEntryId]
		entries.remove(gradeEntry)
	}
	
    /**
     * @param l'ID de la question à laquelle récupérer la liste d'entrées de note
	 * @return une liste d'ID d'entrées sélectionnées dans le StudentSheet courant pour la question dont l'ID est <i>questionId</i>
	 */
	override List<Integer> getQuestionSelectedGradeEntries(int questionId) {
		if (currentSheetIndex > studentSheets.size - 1)
			return new ArrayList<Integer>();

		val sheet = studentSheets.get(currentSheetIndex);

		if (questionId > sheet.grades.size - 1)
			return new ArrayList<Integer>();

		sheet.grades.get(questionId).entries.map[entry|entry.id]
	}

	
	//FIXME : Probleme lorsque la note maximal est modifier pour une note plus basse, risque de depacement
	/**
	 * Vérification de la validité d'une note lorsque l'on ajoute un grandEntry
	 * @return vrai si le nouvelle note est valide, faux sinon
	 * Pour être valide, la nouvelle note doit respecter les conditions suivantes :
	 * <ul>
	 * <li>Être inférieure ou égale à la note maximale possible pour la question</li>
	 * <li>Ne pas être inferieure à 0</li>
	 * </ul>
	 */
	override boolean validGradeEntry(int questionId,GradeEntry gradeAdd){
		val gradeMax = getQuestion(questionId).gradeScale.maxPoint
		var currentGrade = studentSheets.get(currentSheetIndex).grades.get(questionId)
			.entries
			.map[e | e.step]
			.reduce[acc, grade | acc + grade]
		if (currentGrade === null) currentGrade = 0f
		val newGrade = currentGrade + gradeAdd.step
		logger.info(newGrade <= gradeMax && newGrade >= 0)
		return newGrade <= gradeMax && newGrade >= 0
	}
	
	//===================================================
	//             Informations sur la copies
	//===================================================
	
	/**
	 * @return la note maximal que peut avoir l'étudiant avec les questions auxquelles il a répondu 
	 */
	override float getCurrentMaxGrade() {
		studentSheets.get(currentSheetIndex).grades
			.indexed
			.filter[pair | !pair.value.entries.isEmpty]
			.map[pair | getQuestionFromIndex(pair.key)]
			.filter[o | !o.isEmpty]
			.map[ o | o.get.gradeScale.maxPoint]
			.reduce[acc, n | acc + n]
		
	}
	
	/**
	 * @return la note actuelle de l'étudiant courant
	 */
	override float getCurrentGrade() {
		studentSheets.get(currentSheetIndex).computeGrade
	}
	
	//===================================================
	//      Information sur la listes des étudiants
	//===================================================
	
	/**
	 * Défini le chemin d'accès vers la liste de tous les étudiants
	 * @param le chemin d'accès vers cette liste (non null)
	 */
	override setStudentListPath(String path) {
		Objects.requireNonNull(path)
		graduationTemplate.studentListPath = path
	}
	
	/**
	 * @return le chemin d'accès vers la liste de tous les étudiants. Null si ce chemin n'est pas défini
	 */
	override String getStudentListPath() {
		return graduationTemplate.studentListPath
	}
	
	/**
	 * Défini la position initiale de la liste de tous les étudiants dans le fichier pointé par le chemin d'accès
	 * @param la position initialede cette liste (non null)
	 */
	override setStudentListShift(String shift) {
		Objects.requireNonNull(shift)
		graduationTemplate.studentListShift = shift
	}
	
	/**
	 * @return la position initiale de la liste de tous les étudiants dans le fichier pointé par le chemin d'accès. 'A1' par défaut
	 */
	override String getStudentListShift() {
		return graduationTemplate.studentListShift
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Accessors int questionId;

	/**
	 * Permet de lier une Question q à une zone du PDF définie par un Rectangle R
	 * @param q Une Question
	 * @param r Un Rectangle
	 * @author degas
	 */
	override int createQuestion(int pdfPageIndex, float x, float y, float heigth, float width) {

		val question = CoreFactory.eINSTANCE.createQuestion();
		question.id = questionId;
		question.gradeScale = CoreFactory.eINSTANCE.createGradeScale();
		question.zone = CoreFactory.eINSTANCE.createQuestionZone();
		question.zone.x = x
		question.zone.y = y
		question.zone.width = width
		question.zone.heigth = heigth
		getPage(pdfPageIndex).questions.add(question);
		return questionId++;
	}

	/**
	 * Redimensionne la zone d'une Question
	 * @param id l'ID de la question dont la zone doit être redimensionnée
	 * @param heigth la nouvelle hauteur de la zone
	 * @param width la nouvelle largeur de la zone
	 */
	override rescaleQuestion(int id, float heigth, float width) {
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
	override moveQuestion(int id, float x, float y) {
		val question = getQuestion(id)
		question.zone.x = x
		question.zone.y = y
	}
	
	/**
	 * Renomme la Question
	 * @param id l'ID de la question à renommer
	 * @param name le nouveau nom de la question
	 */
	override renameQuestion(int id, String name) {
		val question = getQuestion(id)
		question.name = name
	}

	/**
	 * Supprime une question
	 * @param id l'ID de la question à supprimer
	 */
	override removeQuestion(int id) {
		for (page : editionTemplate.exam.pages)
			for (question : page.questions)
				if (question.id == id)
					page.questions.remove(question)
	}

	/**
	 * Modifie la note maximal que l'on peut attribuer a une question.
	 * @param questionId, l'ID de la question a laquelle on veut modifier la note maximal possible
	 * @param maxPoint, note maximal de la question question a ajouter
	 */
	override modifyMaxPoint(int questionId, float maxPoint) {
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
	override save(ByteArrayOutputStream outputStream, File path) {
		val encoded = Base64.getEncoder().encode(outputStream.toByteArray());
		editionTemplate.encodedDocument = new String(encoded);
		outputStream.close();

		TemplateIo.save(path, editionTemplate);
	}

	/**
	 * Charge un fichier modèle d'examen a partir du disque
	 * @params xmiPath L'emplacement du fichier.
	 * @returns un flux vers le contenu du fichier si celui-ci a bien été ouvert, Optional.empty sinon
	 */
	override Optional<ByteArrayInputStream> open(String xmiPath) {
		val creationTemplate = TemplateIo.loadCreationTemplate(xmiPath)

		if (creationTemplate.present) {
			this.editionTemplate = creationTemplate.get()
			val exam = creationTemplate.get().exam
			val decoded = Base64.getDecoder().decode(creationTemplate.get().encodedDocument);

			questionId = exam.pages.stream.map[page|page.questions.size].reduce[acc, num|acc + num].get + 1
			return Optional.of(new ByteArrayInputStream(decoded));
		}
		return Optional.empty;
	}

	/**
	 * Crée un nouveau modèle côté données
	 * @param pageNumber le nombre de pages du modèle
	 */
	override void onDocumentLoad(int pageNumber) {
		editionTemplate = TemplatesFactory.eINSTANCE.createCreationTemplate

		editionTemplate.exam = CoreFactory.eINSTANCE.createExam()

		for (i : 0 ..< pageNumber) {
			val page = CoreFactory.eINSTANCE.createPage()

			editionTemplate.exam.pages.add(page);
		}
		questionId = 0
	}

	/** Retourne la zone associée à une question
	 * @param index Index de la question //FIXME (useless?)
	 * @author degas
	 */
	override QuestionZone getQuestionZone(int pageIndex, int questionIndex) {
		return getQuestion(pageIndex, questionIndex).zone
	}
	
	
	/** Permet de récupérer une Question
	 * @param index Index de la question
	 * @return Question Retourne une instance de Question
	 * @author degas
	 */
	protected def Question getQuestion(int pageId, int questionid)
	{
		return editionTemplate.exam.pages.get(pageId).questions.get(questionid);
	}
	
	/**  Rend la liste des Questions définies dans un Examen
	 * @return List<Question>
	 * @author degas
	 */
	protected def Collection<Question> getQuestions(int pageId)
	{
		return Collections.unmodifiableCollection(editionTemplate.exam.pages.get(pageId).questions);
	}
	
	/**
	 * @param absoluteQuestionId la position absolue d'une question dans l'Examen
	 * @return la Question associée à cette position si elle existe, Optional.empty sinon
	 */
	protected def Optional<Question> getQuestionFromIndex(int absoluteQuestionId) {
		return Optional.ofNullable(
			editionTemplate.exam.pages.flatMap[p | p.questions].indexed.findFirst[p | p.key == absoluteQuestionId]
		).map(q | q.value)
	}
	
	/**
	 * @return le nombre de pages de l'Examen
	 */
	override int getTemplatePageAmount(){
		editionTemplate.exam.pages.size
	}

	/**
	 * @param pageId l'ID de la page à récupérer
	 * @return la Page dont l'ID est <i>pageId</i>
	 */
	protected def Page getPage(int pageId)
	{
		return editionTemplate.exam.pages.get(pageId);
	}
	
	/**
	 * @return vrai si un modèle d'examen est chargé, false sinon
	 */
	override boolean hasExamLoaded() {
		editionTemplate !== null && editionTemplate.exam !== null
	}

	/**
	 * Met à jour le nom de l'examen
	 * @param name Nouveau nom de l'examen
	 */
	override void setExamName(String name) {
		editionTemplate.exam.name = name
	}
	
	/**
	 * @param pageIndex l'ID d'une page
	 * @return la liste des Questions sur la page dont l'ID est <i>pageIndex</i> 
	 */
	override getQuestionAtPage(int pageIndex) {
		getPage(pageIndex).questions
	}
	
	/**
	 * @param l'ID de la Question
	 * @return la Question du modèle correspondant à l'ID spécifié
	 */
	protected def Question getQuestion(int id) {
		for (page : editionTemplate.exam.pages) {
			val question = page.questions.findFirst[question|question.id == id]
			if (question !== null)
				return question
		}
		return null
	}
	
	/**
	 * @return Identifiant de l'examen
	 * @author degas
	 */
	override int getExamId() {
		return editionTemplate.exam.id;
	}

	/**@return Nom de l'examen
	 * @author degas
	 */
	override String getExamName() {
		return editionTemplate.exam.name;
	}
	
	/**
	 * @return la liste non modifiable de tous les StudentSheets
	 */
	override getStudentSheets() {
		return Collections.unmodifiableList(graduationTemplate.studentsheets)
	}
	
}