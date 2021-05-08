package fr.istic.tools.scanexam.services

import fr.istic.tools.scanexam.api.DataFactory
import fr.istic.tools.scanexam.core.CoreFactory
import fr.istic.tools.scanexam.core.GradeEntry
import fr.istic.tools.scanexam.core.Page
import fr.istic.tools.scanexam.core.Question
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
import java.io.InputStream
import java.util.ArrayList
import java.util.Base64
import java.util.Collection
import java.util.Collections
import java.util.List
import java.util.Objects
import java.util.Optional
import org.apache.logging.log4j.LogManager
import fr.istic.tools.scanexam.core.Comment
import java.util.LinkedList
import fr.istic.tools.scanexam.core.TextComment

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
	 
	int gradeEntryId;
	
	int annotationId;
	
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
	override saveCorrectionTemplate(String path, ByteArrayOutputStream pdfOutputStream) 
	{
		val encoded = Base64.getEncoder().encode(pdfOutputStream.toByteArray());
		graduationTemplate.encodedDocument = new String(encoded);
		pdfOutputStream.close();
		TemplateIo.save(new File(path), graduationTemplate);
		//FIXME CACHE MISÈRE POUR https://github.com/ScanExam/ScanExam/issues/34
		saveEdition
	}
	
	/**
	 * Charge un fichier de correction d'examen a partir du disque.
	 * @params path le fichier
	 * @returns Un inputStream vers le PDF si le template a bien pu être chargé, Optional.empty sinon
	 */
	override Optional<InputStream> openCorrectionTemplate(File xmiFile)
	{
		val correctionTemplate = TemplateIo.loadCorrectionTemplate(xmiFile.absolutePath) 
		
		if (correctionTemplate.present) 
        {
            this.graduationTemplate = correctionTemplate.get()
            val decoded = Base64.getDecoder().decode(graduationTemplate.encodedDocument);
            return Optional.of(new ByteArrayInputStream(decoded));
        }
		return Optional.empty
	}
	
	
	/**
	 * Crée une nouvelle correction à partir d'une liste de StudentSheets
	 * @params studentSheets une liste de StudenSheet
	 * @returns "true" si la correction a pu être créée, "false" sinon
	 */
	override boolean initializeCorrection(Collection<StudentSheet> studentSheets) {
		graduationTemplate = TemplatesFactory.eINSTANCE.createCorrectionTemplate
		try {
			for (StudentSheet sheet : studentSheets) {
				for (var i = 0; i < templatePageAmount; i++) {
					val examPage = getPage(i);
					for (var j = 0; j < examPage.questions.size; j++) // TODO +1?
					{
						var grade = CoreFactory.eINSTANCE.createGrade()
						sheet.grades.add(grade);
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
	 * Définit la copie d'étudiant suivant la copie actuelle comme nouvelle copie courante
	 * Si la copie courante est la dernière, ne fait rien
	 */
	override nextSheet() {
		if (currentSheetIndex + 1 < studentSheets.size)
			currentSheetIndex++
	}

	/**
	 * Définit la copie d'étudiant précédant la copie actuelle comme nouvelle copie courante
	 * Si la copie courante est la première, ne fait rien
	 */
	override previousSheet() {
		if (currentSheetIndex > 0)
			currentSheetIndex--
	}
	
	/**
	 * Associe un nouveau identifiant d'étudiant à la copie courante
	 * @param id le nouvel identifiant d'étudiant
	 */
	override assignStudentName(String id) {
		logger.info("Renaming student :" + currentSheetIndex + "with name :" + id)
		studentSheets.get(currentSheetIndex).studentName = id
	}
	
	/**
	 * @return la liste non modifiable de tous les StudentSheets
	 */
	override getStudentSheets() {
		
		if (graduationTemplate === null)
		{
			return List.of();
		}
		return Collections.unmodifiableList(graduationTemplate.studentsheets)
	}
	
	/**
	 * @return le nom de l'etudiant dont l'ID de la copie est id si la copie existe, Optional.empty sinon
	 * @param id l'ID de la copie
	 */
	 override getStudentName(int id){
	 	for (StudentSheet sheet : studentSheets) {
			if (sheet.id === id ) {
				return Optional.of(sheet.studentName);
			}
		}
		return Optional.empty;
	 }
	
	/**
	 * Définit la copie courante à l'ID spécifié si cet ID est bien un ID valide. Ne fait rien sinon
	 * @param id un ID de copie d'étudiant
	 */
	override void selectSheet(int id) {
		if(id >= 0 && id < studentSheets.size)
			currentSheetIndex = id	
	}
	
	//===================================================
	//          			 Page
	//===================================================
	
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
		
		if (getQuestion(questionId).gradeScale !== null) {
			logger.info("IN SERVICE : " + getQuestion(questionId).gradeScale.steps)
			return getQuestion(questionId).gradeScale.steps.map[entry | Tuple3.of(entry.id, entry.header, entry.step)]	
		}
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
		if (validGradeEntry(questionId, gradeEntry,false)) {
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
		if (validGradeEntry(questionId, gradeEntry,true)){
			entries.remove(gradeEntry)
			return true;
		}
		return false;
		
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
	
	override double getQuestionSelectedGradeEntriesTotalWorth(int questionId) {
		if (currentSheetIndex > studentSheets.size - 1) return 0;
		val sheet = studentSheets.get(currentSheetIndex);
		if (questionId > sheet.grades.size - 1)
			return 0;
		var total = 0f;
		for (GradeEntry entry : sheet.grades.get(questionId).entries) {
			logger.warn("Adding" + entry.step + "to " + total )
			total = total + entry.step
		}
		logger.warn("total is :" + total )
		total
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
	override boolean validGradeEntry(int questionId,GradeEntry gradeAdd,boolean removal){
		val gradeMax = getQuestion(questionId).gradeScale.maxPoint
		var currentGrade = studentSheets.get(currentSheetIndex).grades.get(questionId)
			.entries
			.map[e | e.step]
			.reduce[acc, grade | acc + grade]
		if (currentGrade === null) currentGrade = 0f
		var newGrade = currentGrade + gradeAdd.step
		if (removal) newGrade = currentGrade - gradeAdd.step
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
		Optional.ofNullable(studentSheets.get(currentSheetIndex).grades
            .indexed
            .filter[pair | !pair.value.entries.isEmpty]
            .map[pair | getQuestionFromIndex(pair.key)]
            .filter[o | !o.isEmpty]
            .map[ o | o.get.gradeScale.maxPoint]
            .reduce[acc, n | acc + n]).orElse(0f)
	}
	
	/**
	 * Retourne la note actuelle de l'étudiant courant
	 * @return la note actuelle de l'étudiant courant
	 */
	override float getCurrentGrade() {
		studentSheets.get(currentSheetIndex).computeGrade
	}
	
	/**
	 * Retourne le barème total de l'examen
	 * @return le barème total de l'examen
	 */
	override float getGlobalScale() {
		var float globalScale = 0.0f;
		for (i : 0 ..< numberOfQuestions) {
    		globalScale += getQuestion(i).gradeScale.maxPoint
		}
		return globalScale
	}
	
	//===================================================
	//      Information sur la listes des étudiants
	//===================================================
	
	/**
	 * Définit le chemin d'accès vers la liste de tous les étudiants
	 * @param le chemin d'accès vers cette liste (non null)
	 */
	override setStudentListPath(String path) {
		Objects.requireNonNull(path)
		graduationTemplate.studentListPath = path
	}
	
	/**
	 * @return le chemin d'accès vers la liste de tous les étudiants. Null si ce chemin n'est pas Définit
	 */
	override String getStudentListPath() {
		return graduationTemplate.studentListPath
	}
	
	/**
	 * Définit la position initiale de la liste de tous les étudiants dans le fichier pointé par le chemin d'accès
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	File editionFile
	int questionId

	/**
	 * Crée une nouvelle question et la zone associée
	 * @param l'index de la page sur laquelle mettre la question
	 * @param x la coordonnée X de la zone de la question
	 * @param y la coordonnée Y de la zone de la question
	 * @param heigth la hauteur de la zone de la question
	 * @param width la longueur de la zone de la question
	 * @return l'ID de la nouvelle question
	 * @throw IllegalArgumentException si l'index de la page pointe vers une page qui n'existe pas
	 * @author degas
	 */
	override int createQuestion(int pdfPageIndex, float x, float y, float heigth, float width) {
		try {
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
		} catch(IndexOutOfBoundsException e) {
			throw new IllegalArgumentException(pdfPageIndex + " is not a valid Page Index")
		}
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
		var Question toRemove = null;
		for (page : editionTemplate.exam.pages) {
			for (question : page.questions)
				if (question.id == id)
					toRemove = question
			if(toRemove !== null)
				page.questions.remove(toRemove)
			}
		return toRemove !== null
	}

	/**
	 * Modifie la note maximal que l'on peut attribuer a une question.
	 * @param questionId l'ID de la question a laquelle on veut modifier la note maximal possible
	 * @param maxPoint note maximal de la question question a ajouter
	 */
	override modifyMaxPoint(int questionId, float maxPoint) {
		val scale = getQuestion(questionId).gradeScale
		if (maxPoint > 0) {
			scale.maxPoint = maxPoint
		}
	}

	/**
	 * Sauvegarde sous le fichier modèle d'examen sur le disque
	 * @param path L'emplacement de sauvegarde du fichier
	 * @param pdfOutputStream le PDF sous forme de Stream
	 */
	override saveEdition(ByteArrayOutputStream outputStream, File path) {
		val encoded = Base64.getEncoder().encode(outputStream.toByteArray());
		editionTemplate.encodedDocument = new String(encoded);
		outputStream.close();

		TemplateIo.save(path, editionTemplate);
	}
	
	/**
	 * Sauvegarde le fichier modèle d'examen sur le disque
	 */
	def saveEdition() {
		TemplateIo.save(editionFile, editionTemplate)
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
			editionFile = new File(xmiPath)
			return Optional.of(new ByteArrayInputStream(decoded));
		}
		return Optional.empty;
	}

	/**  Rend la liste non modifiable des Questions Définies dans un Examen
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
	 * Crée et initialise un nouveau modèle d'Examen
	 * @param pageNumber le nombre de pages du modèle
	 */
	override initializeEdition(int pageNumber) {
		editionTemplate = TemplatesFactory.eINSTANCE.createCreationTemplate

		editionTemplate.exam = CoreFactory.eINSTANCE.createExam()

		for (i : 0 ..< pageNumber) {
			val page = CoreFactory.eINSTANCE.createPage()
			page.questions.forEach[q | q.gradeScale = CoreFactory.eINSTANCE.createGradeScale]
			editionTemplate.exam.pages.add(page);
		}
		questionId = 0
	}
	
	//===================================================
	//      Annotations
	//===================================================
	
	//SEE API FOR DOC (WILL ADD HERE LATER)
	override addNewAnnotation(double x, double y, double width, double height, double pointerX, double pointerY, String text, int questionId, int studentId) {
		val DataFactory factory = new DataFactory
		val annot = factory.createTextComment(annotationId,text,x as float,y as float,width as float,height as float,pointerX as float,pointerY as float)
		
		val sheet = studentSheets.get(currentSheetIndex)
		sheet.grades.get(questionId).comments.add(annot)
		annotationId++;
	}
	
	def Comment getAnnotationWithId(List<Comment> comments,int annotId){
		for (Comment c : comments) {
			if (c.id == annotId)
				return c
		}
		null
	}
	
	override getAnnotationIds(int questionId, int studentId) {
		var result = new LinkedList<Integer>();
		val sheet = studentSheets.get(currentSheetIndex)
		val comments = sheet.grades.get(questionId).comments
		for (Comment c : comments) {
			result.add(c.id);
		}
		result
	}
	
	override getAnnotationText(int annotationId,int questionId,int studentId) {
		val sheet = studentSheets.get(currentSheetIndex)
		val comments = sheet.grades.get(questionId).comments
		val annot = getAnnotationWithId(comments, annotationId)
		if (annot !== null)
			return (annot as TextComment).text
		return "Annotation not found"
	}
	
	override getAnnotationX(int annotationId,int questionId,int studentId) {
		val sheet = studentSheets.get(currentSheetIndex)
		val comments = sheet.grades.get(questionId).comments
		val annot = getAnnotationWithId(comments, annotationId)
		if (annot !== null)
			return annot.x
		return 0
	}
	
	override getAnnotationY(int annotationId,int questionId,int studentId) {
		val sheet = studentSheets.get(currentSheetIndex)
		val comments = sheet.grades.get(questionId).comments
		val annot = getAnnotationWithId(comments, annotationId)
		if (annot !== null)
			return annot.y
		return 0
	}
	
	override getAnnotationHeight(int annotationId,int questionId,int studentId) {
		val sheet = studentSheets.get(currentSheetIndex)
		val comments = sheet.grades.get(questionId).comments
		val annot = getAnnotationWithId(comments, annotationId)
		if (annot !== null)
			return annot.x
		return 0
	}
	
	override getAnnotationWidth(int annotationId,int questionId,int studentId) { //TODO height and width not in model
		val sheet = studentSheets.get(currentSheetIndex)
		val comments = sheet.grades.get(questionId).comments
		val annot = getAnnotationWithId(comments, annotationId)
		if (annot !== null)
			return annot.x
		return 0
	}
	
	override getAnnotationPointerX(int annotationId,int questionId,int studentId) {
		val sheet = studentSheets.get(currentSheetIndex)
		val comments = sheet.grades.get(questionId).comments
		val annot = getAnnotationWithId(comments, annotationId)
		if (annot !== null)
			return annot.pointerX
		return 0
	}
	
	override getAnnotationPointerY(int annotationId,int questionId,int studentId) {
		val sheet = studentSheets.get(currentSheetIndex)
		val comments = sheet.grades.get(questionId).comments
		val annot = getAnnotationWithId(comments, annotationId)
		if (annot !== null)
			return annot.pointerY
		return 0
	}
	
	override removeAnnotation(int annotationId,int questionId,int studentId) {
		val sheet = studentSheets.get(currentSheetIndex)
		val comments = sheet.grades.get(questionId).comments
		val annot = getAnnotationWithId(comments, annotationId)
		if (annot !== null)
			comments.remove(annot)
	}
	
	override updateAnnotation(double x, double y, double width, double height, double pointerX, double pointerY, String text,int annotationId, int questionId, int studentId) {
		val sheet = studentSheets.get(currentSheetIndex)
		val comments = sheet.grades.get(questionId).comments
		val annot = getAnnotationWithId(comments, annotationId)
		if (annot !== null){
			annot.x = x as float
			annot.y = y as float
			annot.pointerX = pointerX as float
			annot.pointerY = pointerY as float
			(annot as TextComment).text  = text
		}
			
		
	}
	
}