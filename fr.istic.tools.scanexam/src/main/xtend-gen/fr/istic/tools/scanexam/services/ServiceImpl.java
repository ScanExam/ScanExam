package fr.istic.tools.scanexam.services;

import fr.istic.tools.scanexam.api.DataFactory;
import fr.istic.tools.scanexam.core.CoreFactory;
import fr.istic.tools.scanexam.core.Exam;
import fr.istic.tools.scanexam.core.Grade;
import fr.istic.tools.scanexam.core.GradeEntry;
import fr.istic.tools.scanexam.core.GradeScale;
import fr.istic.tools.scanexam.core.Page;
import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.core.QuestionZone;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.core.templates.CorrectionTemplate;
import fr.istic.tools.scanexam.core.templates.CreationTemplate;
import fr.istic.tools.scanexam.core.templates.TemplatesFactory;
import fr.istic.tools.scanexam.io.TemplateIo;
import fr.istic.tools.scanexam.services.api.ServiceEdition;
import fr.istic.tools.scanexam.services.api.ServiceGraduation;
import fr.istic.tools.scanexam.utils.Tuple3;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Classe servant de façade aux données concernant la correction
 * @author Antoine Degas, Marius Lumbroso, Théo Giraudet, Thomas Guibert
 */
@SuppressWarnings("all")
public class ServiceImpl implements ServiceGraduation, ServiceEdition {
  private static final Logger logger = LogManager.getLogger();
  
  /**
   * Index de la page courante du modèle d'exam
   */
  protected int pageIndex;
  
  /**
   * La page actuelle de l'examen
   */
  private int currentSheetIndex;
  
  /**
   * Question actuelle.
   */
  private int currentQuestionIndex;
  
  private int gradeEntryId;
  
  /**
   * Fichier du template de l'édition d'examen (Fichier de méta données sur le sujet d'examen)
   */
  private CreationTemplate editionTemplate;
  
  /**
   * Fichier du template de correction d'examen
   * (Fichier de méta données sur les corrections de copies déja effectués)
   */
  private CorrectionTemplate graduationTemplate;
  
  /**
   * Sauvegarde le fichier de correction d'examen sur le disque.
   * @param path L'emplacement de sauvegarde du fichier.
   * @param pdfOutputStream le contenu du fichier sous forme de Stream
   */
  @Override
  public void saveCorrectionTemplate(final String path, final ByteArrayOutputStream pdfOutputStream) {
    try {
      final byte[] encoded = Base64.getEncoder().encode(pdfOutputStream.toByteArray());
      String _string = new String(encoded);
      this.graduationTemplate.setEncodedDocument(_string);
      pdfOutputStream.close();
      this.graduationTemplate.getStudentsheets().clear();
      this.graduationTemplate.getStudentsheets().addAll(this.getStudentSheets());
      File _file = new File(path);
      TemplateIo.save(_file, this.graduationTemplate);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Charge un fichier de correction d'examen a partir du disque.
   * @params path le fichier
   * @returns Un inputStream vers le PDF si le template a bien pu être chargé, Optional.empty sinon
   */
  @Override
  public Optional<InputStream> openCorrectionTemplate(final File xmiFile) {
    final Optional<CorrectionTemplate> correctionTemplate = TemplateIo.loadCorrectionTemplate(xmiFile.getAbsolutePath());
    boolean _isPresent = correctionTemplate.isPresent();
    if (_isPresent) {
      this.graduationTemplate = correctionTemplate.get();
      final byte[] decoded = Base64.getDecoder().decode(this.graduationTemplate.getEncodedDocument());
      ByteArrayInputStream _byteArrayInputStream = new ByteArrayInputStream(decoded);
      return Optional.<InputStream>of(_byteArrayInputStream);
    }
    return Optional.<InputStream>empty();
  }
  
  /**
   * Charge le document PDF des copies manuscrites,  corrigés
   * @params path L'emplacement du fichier.
   * @returns "true" si le fichier a bien été chargé, "false"
   */
  @Override
  public boolean initializeCorrection(final Collection<StudentSheet> studentSheets) {
    this.graduationTemplate = TemplatesFactory.eINSTANCE.createCorrectionTemplate();
    try {
      for (final StudentSheet sheet : studentSheets) {
        for (int i = 0; (i < this.getTemplatePageAmount()); i++) {
          {
            final Page examPage = this.getPage(i);
            int _size = examPage.getQuestions().size();
            String _plus = ("test size : " + Integer.valueOf(_size));
            InputOutput.<String>println(_plus);
            for (int j = 0; (j < examPage.getQuestions().size()); j++) {
              sheet.getGrades().add(CoreFactory.eINSTANCE.createGrade());
            }
          }
        }
      }
      this.graduationTemplate.getStudentsheets().addAll(studentSheets);
      return true;
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        return false;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  @Override
  public int getAbsolutePageNumber(final int studentId, final int offset) {
    final Function1<StudentSheet, Boolean> _function = (StudentSheet x) -> {
      int _id = x.getId();
      return Boolean.valueOf((_id == studentId));
    };
    final Integer pageId = IterableExtensions.<StudentSheet>findFirst(this.getStudentSheets(), _function).getPosPage().get(0);
    return ((pageId).intValue() + offset);
  }
  
  /**
   * Défini la copie d'étudiant suivant la copie actuelle comme nouvelle copie courante
   */
  @Override
  public void nextSheet() {
    int _size = this.getStudentSheets().size();
    boolean _lessThan = ((this.currentSheetIndex + 1) < _size);
    if (_lessThan) {
      this.currentSheetIndex++;
    }
  }
  
  /**
   * Défini la copie d'étudiant précédant la copie actuelle comme nouvelle copie courante
   */
  @Override
  public void previousSheet() {
    if ((this.currentSheetIndex > 0)) {
      this.currentSheetIndex--;
    }
  }
  
  /**
   * Associe un nouveau identifiant d'étudiant à la copie courante
   * @param id le nouvel identifiant d'étudiant
   */
  @Override
  public void assignStudentId(final String id) {
    StudentSheet _get = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
    _get.setStudentName(id);
  }
  
  /**
   * @return la liste non modifiable de tous les StudentSheets
   */
  @Override
  public Collection<StudentSheet> getStudentSheets() {
    return Collections.<StudentSheet>unmodifiableList(this.graduationTemplate.getStudentsheets());
  }
  
  /**
   * Défini la copie courante à l'ID spécifié si cet ID est bien un ID valide. Ne fait rien sinon
   * @param id un ID de copie d'étudiant
   */
  @Override
  public void selectSheet(final int id) {
    if (((id > 0) && (id < this.getStudentSheets().size()))) {
      this.currentSheetIndex = id;
    }
  }
  
  /**
   * @return l'index de la page courante du modèle d'exam
   */
  private Page getCurrentPage() {
    return this.getPage(this.pageIndex);
  }
  
  /**
   * Défini la page suivant la page actuelle comme nouvelle page courante
   */
  @Override
  public void nextPage() {
    int _length = ((Object[])Conversions.unwrapArray(this.editionTemplate.getExam().getPages(), Object.class)).length;
    boolean _lessThan = ((this.pageIndex + 1) < _length);
    if (_lessThan) {
      this.pageIndex++;
    }
  }
  
  /**
   * Défini la page précédant la page actuelle comme nouvelle page courante
   */
  @Override
  public void previousPage() {
    if ((this.pageIndex > 0)) {
      this.pageIndex--;
    }
  }
  
  /**
   * @return le nombre de pages de l'Examen
   */
  @Override
  public int getPageAmount() {
    return this.getTemplatePageAmount();
  }
  
  /**
   * Défini la question suivant la question actuelle comme nouvelle question courante
   */
  @Override
  public void nextQuestion() {
    int _size = this.getCurrentPage().getQuestions().size();
    boolean _lessThan = ((this.currentQuestionIndex + 1) < _size);
    if (_lessThan) {
      this.currentQuestionIndex++;
    }
  }
  
  /**
   * Défini la question précédant la question actuelle comme nouvelle question courante
   */
  @Override
  public void previousQuestion() {
    if ((this.currentQuestionIndex > 0)) {
      this.currentQuestionIndex--;
    }
  }
  
  /**
   * Défini pour question courante la question dont l'ID est passé en paramètre si celle-ci existe, et défini pour page courante la page où se trouve cette question.<br/>
   * Ne fait rien si la question n'existe pas
   * @param id un ID de question
   */
  @Override
  public void selectQuestion(final int id) {
    EList<Page> _pages = this.editionTemplate.getExam().getPages();
    for (final Page page : _pages) {
      {
        final Function1<Question, Boolean> _function = (Question question) -> {
          int _id = question.getId();
          return Boolean.valueOf((_id == id));
        };
        final Question question = IterableExtensions.<Question>findFirst(page.getQuestions(), _function);
        if ((question != null)) {
          this.pageIndex = page.getId();
          this.currentQuestionIndex = question.getId();
        }
      }
    }
  }
  
  /**
   * @return le nombre de questions d'une copie d'étudiant
   */
  @Override
  public int numberOfQuestions() {
    int _xblockexpression = (int) 0;
    {
      int nbQuestion = 0;
      for (int i = 0; (i < this.editionTemplate.getExam().getPages().size()); i++) {
        int _nbQuestion = nbQuestion;
        int _size = this.editionTemplate.getExam().getPages().get(i).getQuestions().size();
        nbQuestion = (_nbQuestion + _size);
      }
      _xblockexpression = nbQuestion;
    }
    return _xblockexpression;
  }
  
  /**
   * Ajoute une nouvelle entrée à la liste des points attribuable à la question
   * @param questionId l'ID de la question dans laquelle ajouter l'entrée
   * @param desc la description de l'entrée
   * @param point le nombre de point de l'entrée
   * @return l'ID de l'entrée
   */
  @Override
  public int addEntry(final int questionId, final String desc, final float point) {
    int _xblockexpression = (int) 0;
    {
      final DataFactory factory = new DataFactory();
      final Question question = this.getQuestion(questionId);
      GradeScale _gradeScale = question.getGradeScale();
      boolean _tripleEquals = (_gradeScale == null);
      if (_tripleEquals) {
        question.setGradeScale(factory.createGradeScale());
      }
      final GradeScale scale = question.getGradeScale();
      scale.getSteps().add(factory.createGradeEntry(this.gradeEntryId, desc, point));
      _xblockexpression = this.gradeEntryId++;
    }
    return _xblockexpression;
  }
  
  /**
   * Modifie une entrée de la liste des points attribuable à la question
   * @param questionId l'ID de la question dans laquelle modifier l'entrée
   * @param gradeEntryId l'ID de l'entrée à modifier
   * @param desc la nouvelle description de l'entrée
   * @param point le nouveau nombre de point de l'entrée
   */
  @Override
  public void modifyEntry(final int questionId, final int gradeEntryId, final String desc, final float point) {
    final GradeScale scale = this.getQuestion(questionId).getGradeScale();
    final Function1<GradeEntry, Boolean> _function = (GradeEntry step) -> {
      int _id = step.getId();
      return Boolean.valueOf((_id == gradeEntryId));
    };
    final GradeEntry scaleEntry = IterableExtensions.<GradeEntry>findFirst(scale.getSteps(), _function);
    if ((scaleEntry != null)) {
      scaleEntry.setHeader(desc);
      scaleEntry.setStep(point);
    }
  }
  
  /**
   * Supprime une entrée de la liste des points attribuable à la question
   * @param questionId l'ID de la question dans laquelle supprimer l'entrée
   * @param gradeEntryId l'ID de l'entrée à supprimer
   */
  @Override
  public void removeEntry(final int questionId, final int gradeEntryId) {
    final GradeScale scale = this.getQuestion(questionId).getGradeScale();
    final Function1<GradeEntry, Boolean> _function = (GradeEntry step) -> {
      int _id = step.getId();
      return Boolean.valueOf((_id == gradeEntryId));
    };
    final GradeEntry scaleEntry = IterableExtensions.<GradeEntry>findFirst(scale.getSteps(), _function);
    if ((scaleEntry != null)) {
      scale.getSteps().remove(scaleEntry);
    }
  }
  
  /**
   * @param l'ID de la question à laquelle récupérer la liste d'entrées de note
   * @return une liste d'ID d'entrées pour la question de l'examen dont l'ID est <i>questionId</i>
   */
  @Override
  public List<Tuple3<Integer, String, Float>> getQuestionGradeEntries(final int questionId) {
    GradeScale _gradeScale = this.getQuestion(questionId).getGradeScale();
    boolean _tripleNotEquals = (_gradeScale != null);
    if (_tripleNotEquals) {
      final Function1<GradeEntry, Tuple3<Integer, String, Float>> _function = (GradeEntry entry) -> {
        return Tuple3.<Integer, String, Float>of(Integer.valueOf(entry.getId()), entry.getHeader(), Float.valueOf(entry.getStep()));
      };
      return ListExtensions.<GradeEntry, Tuple3<Integer, String, Float>>map(this.getQuestion(questionId).getGradeScale().getSteps(), _function);
    }
    return List.<Tuple3<Integer, String, Float>>of();
  }
  
  /**
   * Ajoute une entrée (GradeItem) à la note d'une question d'une copie
   * @param questionId l'ID de la question à laquelle ajouter l'entrée
   * @param l'ID de l'entrée dans l'Examen
   * @return boolean indique si les points on bien ete attribuer
   */
  @Override
  public boolean assignGradeEntry(final int questionId, final int gradeEntryId) {
    final Function1<GradeEntry, Boolean> _function = (GradeEntry entry) -> {
      int _id = entry.getId();
      return Boolean.valueOf((_id == gradeEntryId));
    };
    final GradeEntry gradeEntry = IterableExtensions.<GradeEntry>findFirst(this.getQuestion(questionId).getGradeScale().getSteps(), _function);
    boolean _validGradeEntry = this.validGradeEntry(questionId, gradeEntry);
    if (_validGradeEntry) {
      final StudentSheet sheet = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
      sheet.getGrades().get(questionId).getEntries().add(gradeEntry);
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Retire une entrée à la note d'une question d'une copie
   * @param questionId l'ID de la question à laquelle retirer l'entrée
   * @param l'ID de l'entrée dans l'Examen
   */
  @Override
  public void retractGradeEntry(final int questionId, final int gradeEntryId) {
    final EList<GradeEntry> entries = (((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex]).getGrades().get(questionId).getEntries();
    final Function1<GradeEntry, Boolean> _function = (GradeEntry entry) -> {
      int _id = entry.getId();
      return Boolean.valueOf((_id == gradeEntryId));
    };
    final GradeEntry gradeEntry = IterableExtensions.<GradeEntry>findFirst(entries, _function);
    entries.remove(gradeEntry);
  }
  
  /**
   * @param l'ID de la question à laquelle récupérer la liste d'entrées de note
   * @return une liste d'ID d'entrées sélectionnées dans le StudentSheet courant pour la question dont l'ID est <i>questionId</i>
   */
  @Override
  public List<Integer> getQuestionSelectedGradeEntries(final int questionId) {
    List<Integer> _xblockexpression = null;
    {
      int _size = this.getStudentSheets().size();
      int _minus = (_size - 1);
      boolean _greaterThan = (this.currentSheetIndex > _minus);
      if (_greaterThan) {
        return new ArrayList<Integer>();
      }
      final StudentSheet sheet = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
      int _size_1 = sheet.getGrades().size();
      int _minus_1 = (_size_1 - 1);
      boolean _greaterThan_1 = (questionId > _minus_1);
      if (_greaterThan_1) {
        return new ArrayList<Integer>();
      }
      final Function1<GradeEntry, Integer> _function = (GradeEntry entry) -> {
        return Integer.valueOf(entry.getId());
      };
      _xblockexpression = ListExtensions.<GradeEntry, Integer>map(sheet.getGrades().get(questionId).getEntries(), _function);
    }
    return _xblockexpression;
  }
  
  /**
   * Vérification de la validité d'une note lorsque l'on ajoute un grandEntry
   * @return vrai si le nouvelle note est valide, faux sinon
   * Pour être valide, la nouvelle note doit respecter les conditions suivantes :
   * <ul>
   * <li>Être inférieure ou égale à la note maximale possible pour la question</li>
   * <li>Ne pas être inferieure à 0</li>
   * </ul>
   */
  @Override
  public boolean validGradeEntry(final int questionId, final GradeEntry gradeAdd) {
    final float gradeMax = this.getQuestion(questionId).getGradeScale().getMaxPoint();
    final Function1<GradeEntry, Float> _function = (GradeEntry e) -> {
      return Float.valueOf(e.getStep());
    };
    final Function2<Float, Float, Float> _function_1 = (Float acc, Float grade) -> {
      return Float.valueOf(((acc).floatValue() + (grade).floatValue()));
    };
    Float currentGrade = IterableExtensions.<Float>reduce(ListExtensions.<GradeEntry, Float>map((((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex]).getGrades().get(questionId).getEntries(), _function), _function_1);
    if ((currentGrade == null)) {
      currentGrade = Float.valueOf(0f);
    }
    float _step = gradeAdd.getStep();
    final float newGrade = ((currentGrade).floatValue() + _step);
    ServiceImpl.logger.info(Boolean.valueOf(((newGrade <= gradeMax) && (newGrade >= 0))));
    return ((newGrade <= gradeMax) && (newGrade >= 0));
  }
  
  /**
   * @return la note maximal que peut avoir l'étudiant avec les questions auxquelles il a répondu
   */
  @Override
  public float getCurrentMaxGrade() {
    final Function1<Pair<Integer, Grade>, Boolean> _function = (Pair<Integer, Grade> pair) -> {
      boolean _isEmpty = pair.getValue().getEntries().isEmpty();
      return Boolean.valueOf((!_isEmpty));
    };
    final Function1<Pair<Integer, Grade>, Optional<Question>> _function_1 = (Pair<Integer, Grade> pair) -> {
      return this.getQuestionFromIndex((pair.getKey()).intValue());
    };
    final Function1<Optional<Question>, Boolean> _function_2 = (Optional<Question> o) -> {
      boolean _isEmpty = o.isEmpty();
      return Boolean.valueOf((!_isEmpty));
    };
    final Function1<Optional<Question>, Float> _function_3 = (Optional<Question> o) -> {
      return Float.valueOf(o.get().getGradeScale().getMaxPoint());
    };
    final Function2<Float, Float, Float> _function_4 = (Float acc, Float n) -> {
      return Float.valueOf(((acc).floatValue() + (n).floatValue()));
    };
    return (float) IterableExtensions.<Float>reduce(IterableExtensions.<Optional<Question>, Float>map(IterableExtensions.<Optional<Question>>filter(IterableExtensions.<Pair<Integer, Grade>, Optional<Question>>map(IterableExtensions.<Pair<Integer, Grade>>filter(IterableExtensions.<Grade>indexed((((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex]).getGrades()), _function), _function_1), _function_2), _function_3), _function_4);
  }
  
  /**
   * @return la note actuelle de l'étudiant courant
   */
  @Override
  public float getCurrentGrade() {
    return (((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex]).computeGrade();
  }
  
  /**
   * Défini le chemin d'accès vers la liste de tous les étudiants
   * @param le chemin d'accès vers cette liste (non null)
   */
  @Override
  public void setStudentListPath(final String path) {
    Objects.<String>requireNonNull(path);
    this.graduationTemplate.setStudentListPath(path);
  }
  
  /**
   * @return le chemin d'accès vers la liste de tous les étudiants. Null si ce chemin n'est pas défini
   */
  @Override
  public String getStudentListPath() {
    return this.graduationTemplate.getStudentListPath();
  }
  
  /**
   * Défini la position initiale de la liste de tous les étudiants dans le fichier pointé par le chemin d'accès
   * @param la position initialede cette liste (non null)
   */
  @Override
  public void setStudentListShift(final String shift) {
    Objects.<String>requireNonNull(shift);
    this.graduationTemplate.setStudentListShift(shift);
  }
  
  /**
   * @return la position initiale de la liste de tous les étudiants dans le fichier pointé par le chemin d'accès. 'A1' par défaut
   */
  @Override
  public String getStudentListShift() {
    return this.graduationTemplate.getStudentListShift();
  }
  
  @Accessors
  private int questionId;
  
  /**
   * Permet de lier une Question q à une zone du PDF définie par un Rectangle R
   * @param q Une Question
   * @param r Un Rectangle
   * @author degas
   */
  @Override
  public int createQuestion(final int pdfPageIndex, final float x, final float y, final float heigth, final float width) {
    final Question question = CoreFactory.eINSTANCE.createQuestion();
    question.setId(this.questionId);
    question.setGradeScale(CoreFactory.eINSTANCE.createGradeScale());
    question.setZone(CoreFactory.eINSTANCE.createQuestionZone());
    QuestionZone _zone = question.getZone();
    _zone.setX(x);
    QuestionZone _zone_1 = question.getZone();
    _zone_1.setY(y);
    QuestionZone _zone_2 = question.getZone();
    _zone_2.setWidth(width);
    QuestionZone _zone_3 = question.getZone();
    _zone_3.setHeigth(heigth);
    this.getPage(pdfPageIndex).getQuestions().add(question);
    return this.questionId++;
  }
  
  /**
   * Redimensionne la zone d'une Question
   * @param id l'ID de la question dont la zone doit être redimensionnée
   * @param heigth la nouvelle hauteur de la zone
   * @param width la nouvelle largeur de la zone
   */
  @Override
  public void rescaleQuestion(final int id, final float heigth, final float width) {
    final Question question = this.getQuestion(id);
    QuestionZone _zone = question.getZone();
    _zone.setWidth(width);
    QuestionZone _zone_1 = question.getZone();
    _zone_1.setHeigth(heigth);
  }
  
  /**
   * Déplace la zone d'une Question
   * @param id l'ID de la question dont la zone doit être déplacée
   * @param x la nouvelle position x de la zone
   * @param y la nouvelle position y de la zone
   */
  @Override
  public void moveQuestion(final int id, final float x, final float y) {
    final Question question = this.getQuestion(id);
    QuestionZone _zone = question.getZone();
    _zone.setX(x);
    QuestionZone _zone_1 = question.getZone();
    _zone_1.setY(y);
  }
  
  /**
   * Renomme la Question
   * @param id l'ID de la question à renommer
   * @param name le nouveau nom de la question
   */
  @Override
  public void renameQuestion(final int id, final String name) {
    final Question question = this.getQuestion(id);
    question.setName(name);
  }
  
  /**
   * Supprime une question
   * @param id l'ID de la question à supprimer
   */
  @Override
  public void removeQuestion(final int id) {
    EList<Page> _pages = this.editionTemplate.getExam().getPages();
    for (final Page page : _pages) {
      EList<Question> _questions = page.getQuestions();
      for (final Question question : _questions) {
        int _id = question.getId();
        boolean _equals = (_id == id);
        if (_equals) {
          page.getQuestions().remove(question);
        }
      }
    }
  }
  
  /**
   * Modifie la note maximal que l'on peut attribuer a une question.
   * @param questionId, l'ID de la question a laquelle on veut modifier la note maximal possible
   * @param maxPoint, note maximal de la question question a ajouter
   */
  @Override
  public void modifyMaxPoint(final int questionId, final float maxPoint) {
    final GradeScale scale = this.getQuestion(questionId).getGradeScale();
    if ((maxPoint > 0)) {
      scale.setMaxPoint(maxPoint);
    }
  }
  
  /**
   * Sauvegarde le fichier modèle d'examen sur le disque
   * @param path L'emplacement de sauvegarde du fichier
   * @param pdfOutputStream le contenu du fichier sous forme de Stream
   */
  @Override
  public void save(final ByteArrayOutputStream outputStream, final File path) {
    try {
      final byte[] encoded = Base64.getEncoder().encode(outputStream.toByteArray());
      String _string = new String(encoded);
      this.editionTemplate.setEncodedDocument(_string);
      outputStream.close();
      TemplateIo.save(path, this.editionTemplate);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Charge un fichier modèle d'examen a partir du disque
   * @params xmiPath L'emplacement du fichier.
   * @returns un flux vers le contenu du fichier si celui-ci a bien été ouvert, Optional.empty sinon
   */
  @Override
  public Optional<ByteArrayInputStream> open(final String xmiPath) {
    final Optional<CreationTemplate> creationTemplate = TemplateIo.loadCreationTemplate(xmiPath);
    boolean _isPresent = creationTemplate.isPresent();
    if (_isPresent) {
      this.editionTemplate = creationTemplate.get();
      final Exam exam = creationTemplate.get().getExam();
      final byte[] decoded = Base64.getDecoder().decode(creationTemplate.get().getEncodedDocument());
      final Function<Page, Integer> _function = (Page page) -> {
        return Integer.valueOf(page.getQuestions().size());
      };
      final BinaryOperator<Integer> _function_1 = (Integer acc, Integer num) -> {
        return Integer.valueOf(((acc).intValue() + (num).intValue()));
      };
      Integer _get = exam.getPages().stream().<Integer>map(_function).reduce(_function_1).get();
      int _plus = ((_get).intValue() + 1);
      this.questionId = _plus;
      ByteArrayInputStream _byteArrayInputStream = new ByteArrayInputStream(decoded);
      return Optional.<ByteArrayInputStream>of(_byteArrayInputStream);
    }
    return Optional.<ByteArrayInputStream>empty();
  }
  
  /**
   * Retourne la zone associée à une question
   * @param index Index de la question //FIXME (useless?)
   * @author degas
   */
  @Override
  public QuestionZone getQuestionZone(final int pageIndex, final int questionIndex) {
    return this.getQuestion(pageIndex, questionIndex).getZone();
  }
  
  /**
   * Permet de récupérer une Question
   * @param index Index de la question
   * @return Question Retourne une instance de Question
   * @author degas
   */
  protected Question getQuestion(final int pageId, final int questionid) {
    return this.editionTemplate.getExam().getPages().get(pageId).getQuestions().get(questionid);
  }
  
  /**
   * Rend la liste des Questions définies dans un Examen
   * @return List<Question>
   * @author degas
   */
  protected Collection<Question> getQuestions(final int pageId) {
    return Collections.<Question>unmodifiableCollection(this.editionTemplate.getExam().getPages().get(pageId).getQuestions());
  }
  
  /**
   * @param absoluteQuestionId la position absolue d'une question dans l'Examen
   * @return la Question associée à cette position si elle existe, Optional.empty sinon
   */
  protected Optional<Question> getQuestionFromIndex(final int absoluteQuestionId) {
    final Function1<Page, EList<Question>> _function = (Page p) -> {
      return p.getQuestions();
    };
    final Function1<Pair<Integer, Question>, Boolean> _function_1 = (Pair<Integer, Question> p) -> {
      Integer _key = p.getKey();
      return Boolean.valueOf(((_key).intValue() == absoluteQuestionId));
    };
    final Function<Pair<Integer, Question>, Question> _function_2 = (Pair<Integer, Question> q) -> {
      return q.getValue();
    };
    return Optional.<Pair<Integer, Question>>ofNullable(
      IterableExtensions.<Pair<Integer, Question>>findFirst(IterableExtensions.<Question>indexed(IterableExtensions.<Page, Question>flatMap(this.editionTemplate.getExam().getPages(), _function)), _function_1)).<Question>map(_function_2);
  }
  
  /**
   * @return le nombre de pages de l'Examen
   */
  @Override
  public int getTemplatePageAmount() {
    return this.editionTemplate.getExam().getPages().size();
  }
  
  /**
   * @param pageId l'ID de la page à récupérer
   * @return la Page dont l'ID est <i>pageId</i>
   */
  protected Page getPage(final int pageId) {
    return this.editionTemplate.getExam().getPages().get(pageId);
  }
  
  /**
   * @return vrai si un modèle d'examen est chargé, false sinon
   */
  @Override
  public boolean hasExamLoaded() {
    return ((this.editionTemplate != null) && (this.editionTemplate.getExam() != null));
  }
  
  /**
   * Met à jour le nom de l'examen
   * @param name Nouveau nom de l'examen
   */
  @Override
  public void setExamName(final String name) {
    Exam _exam = this.editionTemplate.getExam();
    _exam.setName(name);
  }
  
  /**
   * @param pageIndex l'ID d'une page
   * @return la liste des Questions sur la page dont l'ID est <i>pageIndex</i>
   */
  @Override
  public List<Question> getQuestionAtPage(final int pageIndex) {
    return this.getPage(pageIndex).getQuestions();
  }
  
  /**
   * @param l'ID de la Question
   * @return la Question du modèle correspondant à l'ID spécifié
   */
  protected Question getQuestion(final int id) {
    EList<Page> _pages = this.editionTemplate.getExam().getPages();
    for (final Page page : _pages) {
      {
        final Function1<Question, Boolean> _function = (Question question) -> {
          int _id = question.getId();
          return Boolean.valueOf((_id == id));
        };
        final Question question = IterableExtensions.<Question>findFirst(page.getQuestions(), _function);
        if ((question != null)) {
          return question;
        }
      }
    }
    return null;
  }
  
  /**
   * @return Identifiant de l'examen
   * @author degas
   */
  @Override
  public int getExamId() {
    return this.editionTemplate.getExam().getId();
  }
  
  /**
   * @return Nom de l'examen
   * @author degas
   */
  @Override
  public String getExamName() {
    return this.editionTemplate.getExam().getName();
  }
  
  /**
   * Crée et initialise un nouveau modèle d'Examen
   * @param pageNumber le nombre de pages du modèle
   */
  @Override
  public void initializeEdition(final int pageNumber) {
    this.editionTemplate = TemplatesFactory.eINSTANCE.createCreationTemplate();
    this.editionTemplate.setExam(CoreFactory.eINSTANCE.createExam());
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, pageNumber, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        final Page page = CoreFactory.eINSTANCE.createPage();
        this.editionTemplate.getExam().getPages().add(page);
      }
    }
    this.questionId = 0;
  }
  
  @Pure
  @Override
  public int getQuestionId() {
    return this.questionId;
  }
  
  public void setQuestionId(final int questionId) {
    this.questionId = questionId;
  }
}
