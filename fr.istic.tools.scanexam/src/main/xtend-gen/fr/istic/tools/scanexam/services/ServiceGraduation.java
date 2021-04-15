package fr.istic.tools.scanexam.services;

import fr.istic.tools.scanexam.api.DataFactory;
import fr.istic.tools.scanexam.core.CoreFactory;
import fr.istic.tools.scanexam.core.Grade;
import fr.istic.tools.scanexam.core.GradeEntry;
import fr.istic.tools.scanexam.core.GradeScale;
import fr.istic.tools.scanexam.core.Page;
import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.core.templates.CorrectionTemplate;
import fr.istic.tools.scanexam.core.templates.CreationTemplate;
import fr.istic.tools.scanexam.io.TemplateIo;
import fr.istic.tools.scanexam.utils.Tuple3;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
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
public class ServiceGraduation extends Service {
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
   * Liste des copies visible.
   */
  @Accessors
  private Collection<StudentSheet> studentSheets;
  
  /**
   * Fichier du template de l'édition d'examen (Fichier de méta données sur le sujet d'examen)
   */
  private CreationTemplate creationTemplate;
  
  /**
   * Fichier du template de correction d'examen
   * (Fichier de méta données sur les corrections de copies déja effectués)
   */
  private CorrectionTemplate correctionTemplate;
  
  /**
   * Sauvegarde le fichier de correction d'examen sur le disque.
   * @param path L'emplacement de sauvegarde du fichier.
   * @param pdfOutputStream le contenu du fichier sous forme de Stream
   */
  public void saveCorrectionTemplate(final String path, final ByteArrayOutputStream pdfOutputStream) {
    try {
      final byte[] encoded = Base64.getEncoder().encode(pdfOutputStream.toByteArray());
      String _string = new String(encoded);
      this.correctionTemplate.setEncodedDocument(_string);
      pdfOutputStream.close();
      this.correctionTemplate.getStudentsheets().clear();
      this.correctionTemplate.getStudentsheets().addAll(this.studentSheets);
      File _file = new File(path);
      TemplateIo.save(_file, this.correctionTemplate);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Charge un fichier de correction d'examen a partir du disque.
   * @params path L'emplacement du fichier.
   * @returns "true" si le fichier a bien été chargé, "false"
   */
  public boolean openCorrectionTemplate(final String xmiFile) {
    final Optional<CorrectionTemplate> correctionTemplate = TemplateIo.loadCorrectionTemplate(xmiFile);
    boolean _isPresent = correctionTemplate.isPresent();
    if (_isPresent) {
      this.correctionTemplate = correctionTemplate.get();
      ExamSingleton.instance = correctionTemplate.get().getExam();
      return true;
    }
    return false;
  }
  
  /**
   * Charge un fichier d'edition d'examen a partir du disque.
   * @params path L'emplacement du fichier.
   * @returns "true" si le fichier a bien été chargé, "false"
   */
  public boolean openCreationTemplate(final String xmiFile) {
    final Optional<CreationTemplate> editionTemplate = TemplateIo.loadCreationTemplate(xmiFile);
    boolean _isPresent = editionTemplate.isPresent();
    if (_isPresent) {
      this.creationTemplate = editionTemplate.get();
      ExamSingleton.instance = editionTemplate.get().getExam();
      return true;
    }
    return false;
  }
  
  /**
   * Charge le document PDF des copies manuscrites,  corrigés
   * @params path L'emplacement du fichier.
   * @returns "true" si le fichier a bien été chargé, "false"
   */
  public boolean initializeCorrection(final Collection<StudentSheet> studentSheets) {
    try {
      for (final StudentSheet sheet : studentSheets) {
        for (int i = 0; (i < ExamSingleton.getTemplatePageAmount()); i++) {
          {
            final Page examPage = ExamSingleton.getPage(i);
            int _size = examPage.getQuestions().size();
            String _plus = ("test size : " + Integer.valueOf(_size));
            InputOutput.<String>println(_plus);
            for (int j = 0; (j < examPage.getQuestions().size()); j++) {
              sheet.getGrades().add(CoreFactory.eINSTANCE.createGrade());
            }
          }
        }
      }
      this.studentSheets = studentSheets;
      return true;
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        return false;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  public int getAbsolutePageNumber(final int studentId, final int offset) {
    final Function1<StudentSheet, Boolean> _function = (StudentSheet x) -> {
      int _id = x.getId();
      return Boolean.valueOf((_id == studentId));
    };
    final Integer pageId = IterableExtensions.<StudentSheet>findFirst(this.studentSheets, _function).getPosPage().get(0);
    return ((pageId).intValue() + offset);
  }
  
  /**
   * Défini la copie d'étudiant suivant la copie actuelle comme nouvelle copie courante
   */
  public int nextSheet() {
    int _xifexpression = (int) 0;
    int _size = this.studentSheets.size();
    boolean _lessThan = ((this.currentSheetIndex + 1) < _size);
    if (_lessThan) {
      _xifexpression = this.currentSheetIndex++;
    }
    return _xifexpression;
  }
  
  /**
   * Défini la copie d'étudiant précédant la copie actuelle comme nouvelle copie courante
   */
  public int previousSheet() {
    int _xifexpression = (int) 0;
    if ((this.currentSheetIndex > 0)) {
      _xifexpression = this.currentSheetIndex--;
    }
    return _xifexpression;
  }
  
  /**
   * Associe un nouveau identifiant d'étudiant à la copie courante
   * @param id le nouvel identifiant d'étudiant
   */
  public void assignStudentId(final String id) {
    StudentSheet _get = ((StudentSheet[])Conversions.unwrapArray(this.studentSheets, StudentSheet.class))[this.currentSheetIndex];
    _get.setStudentName(id);
  }
  
  /**
   * @return l'index de la page courante du modèle d'exam
   */
  private Page getCurrentPage() {
    return ExamSingleton.getPage(this.pageIndex);
  }
  
  /**
   * Défini la page suivant la page actuelle comme nouvelle page courante
   */
  public int nextPage() {
    int _xifexpression = (int) 0;
    int _length = ((Object[])Conversions.unwrapArray(ExamSingleton.instance.getPages(), Object.class)).length;
    boolean _lessThan = ((this.pageIndex + 1) < _length);
    if (_lessThan) {
      _xifexpression = this.pageIndex++;
    }
    return _xifexpression;
  }
  
  /**
   * Défini la page précédant la page actuelle comme nouvelle page courante
   */
  public int previousPage() {
    int _xifexpression = (int) 0;
    if ((this.pageIndex > 0)) {
      _xifexpression = this.pageIndex--;
    }
    return _xifexpression;
  }
  
  /**
   * @return le nombre de pages de l'Examen
   */
  public int getPageAmount() {
    return ExamSingleton.getTemplatePageAmount();
  }
  
  /**
   * Défini la question suivant la question actuelle comme nouvelle question courante
   */
  public int nextQuestion() {
    int _xifexpression = (int) 0;
    int _size = this.getCurrentPage().getQuestions().size();
    boolean _lessThan = ((this.currentQuestionIndex + 1) < _size);
    if (_lessThan) {
      _xifexpression = this.currentQuestionIndex++;
    }
    return _xifexpression;
  }
  
  /**
   * Défini la question précédant la question actuelle comme nouvelle question courante
   */
  public int previousQuestion() {
    int _xifexpression = (int) 0;
    if ((this.currentQuestionIndex > 0)) {
      _xifexpression = this.currentQuestionIndex--;
    }
    return _xifexpression;
  }
  
  /**
   * Défini pour question courante la question dont l'ID est passé en paramètre si celle-ci existe, et défini pour page courante la page où se trouve cette question.<br/>
   * Ne fait rien si la question n'existe pas
   * @param id un ID de question
   */
  public void selectQuestion(final int id) {
    EList<Page> _pages = ExamSingleton.instance.getPages();
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
  public int numberOfQuestions() {
    int _xblockexpression = (int) 0;
    {
      int nbQuestion = 0;
      for (int i = 0; (i < this.creationTemplate.getExam().getPages().size()); i++) {
        int _nbQuestion = nbQuestion;
        int _size = this.creationTemplate.getExam().getPages().get(i).getQuestions().size();
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
  public boolean removeEntry(final int questionId, final int gradeEntryId) {
    boolean _xblockexpression = false;
    {
      final GradeScale scale = this.getQuestion(questionId).getGradeScale();
      final Function1<GradeEntry, Boolean> _function = (GradeEntry step) -> {
        int _id = step.getId();
        return Boolean.valueOf((_id == gradeEntryId));
      };
      final GradeEntry scaleEntry = IterableExtensions.<GradeEntry>findFirst(scale.getSteps(), _function);
      boolean _xifexpression = false;
      if ((scaleEntry != null)) {
        _xifexpression = scale.getSteps().remove(scaleEntry);
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * @param l'ID de la question à laquelle récupérer la liste d'entrées de note
   * @return une liste d'ID d'entrées pour la question de l'examen dont l'ID est <i>questionId</i>
   */
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
  public boolean assignGradeEntry(final int questionId, final int gradeEntryId) {
    final Function1<GradeEntry, Boolean> _function = (GradeEntry entry) -> {
      int _id = entry.getId();
      return Boolean.valueOf((_id == gradeEntryId));
    };
    final GradeEntry gradeEntry = IterableExtensions.<GradeEntry>findFirst(this.getQuestion(questionId).getGradeScale().getSteps(), _function);
    boolean _valideGradeEntry = this.valideGradeEntry(questionId, gradeEntry);
    if (_valideGradeEntry) {
      final StudentSheet sheet = ((StudentSheet[])Conversions.unwrapArray(this.studentSheets, StudentSheet.class))[this.currentSheetIndex];
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
  public boolean retractGradeEntry(final int questionId, final int gradeEntryId) {
    boolean _xblockexpression = false;
    {
      final EList<GradeEntry> entries = (((StudentSheet[])Conversions.unwrapArray(this.studentSheets, StudentSheet.class))[this.currentSheetIndex]).getGrades().get(questionId).getEntries();
      final Function1<GradeEntry, Boolean> _function = (GradeEntry entry) -> {
        int _id = entry.getId();
        return Boolean.valueOf((_id == gradeEntryId));
      };
      final GradeEntry gradeEntry = IterableExtensions.<GradeEntry>findFirst(entries, _function);
      _xblockexpression = entries.remove(gradeEntry);
    }
    return _xblockexpression;
  }
  
  /**
   * @param l'ID de la question à laquelle récupérer la liste d'entrées de note
   * @return une liste d'ID d'entrées sélectionnées dans le StudentSheet courant pour la question dont l'ID est <i>questionId</i>
   */
  public List<Integer> getQuestionSelectedGradeEntries(final int questionId) {
    List<Integer> _xblockexpression = null;
    {
      int _size = this.studentSheets.size();
      int _minus = (_size - 1);
      boolean _greaterThan = (this.currentSheetIndex > _minus);
      if (_greaterThan) {
        return new ArrayList<Integer>();
      }
      final StudentSheet sheet = ((StudentSheet[])Conversions.unwrapArray(this.studentSheets, StudentSheet.class))[this.currentSheetIndex];
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
   * Verification de la validiter d'une note quand on ajoute un grandEntry
   * Elle doit respecter les condition suivant:
   * -La note doit etre superieur a la note maximal possible
   * -La note ne peut etre inferieur a 0
   */
  public boolean valideGradeEntry(final int questionId, final GradeEntry gradeAdd) {
    return true;
  }
  
  /**
   * @return la note maximal que peut avoir l'étudiant avec les questions auxquelles il a répondu
   */
  public float getCurrentMaxGrade() {
    final Function1<Pair<Integer, Grade>, Boolean> _function = (Pair<Integer, Grade> pair) -> {
      boolean _isEmpty = pair.getValue().getEntries().isEmpty();
      return Boolean.valueOf((!_isEmpty));
    };
    final Function1<Pair<Integer, Grade>, Optional<Question>> _function_1 = (Pair<Integer, Grade> pair) -> {
      return ExamSingleton.getQuestionFromIndex((pair.getKey()).intValue());
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
    return (float) IterableExtensions.<Float>reduce(IterableExtensions.<Optional<Question>, Float>map(IterableExtensions.<Optional<Question>>filter(IterableExtensions.<Pair<Integer, Grade>, Optional<Question>>map(IterableExtensions.<Pair<Integer, Grade>>filter(IterableExtensions.<Grade>indexed((((StudentSheet[])Conversions.unwrapArray(this.studentSheets, StudentSheet.class))[this.currentSheetIndex]).getGrades()), _function), _function_1), _function_2), _function_3), _function_4);
  }
  
  /**
   * @return la note actuelle de l'étudiant courant
   */
  public float getCurrentGrade() {
    return (((StudentSheet[])Conversions.unwrapArray(this.studentSheets, StudentSheet.class))[this.currentSheetIndex]).computeGrade();
  }
  
  /**
   * Défini le chemin d'accès vers la liste de tous les étudiants
   * @param le chemin d'accès vers cette liste (non null)
   */
  public void setStudentListPath(final String path) {
    Objects.<String>requireNonNull(path);
    this.correctionTemplate.setStudentListPath(path);
  }
  
  /**
   * @return le chemin d'accès vers la liste de tous les étudiants. Null si ce chemin n'est pas défini
   */
  public String getStudentListPath() {
    return this.correctionTemplate.getStudentListPath();
  }
  
  /**
   * Défini la position initiale de la liste de tous les étudiants dans le fichier pointé par le chemin d'accès
   * @param la position initialede cette liste (non null)
   */
  public void setStudentListShift(final String shift) {
    Objects.<String>requireNonNull(shift);
    this.correctionTemplate.setStudentListShift(shift);
  }
  
  /**
   * @return la position initiale de la liste de tous les étudiants dans le fichier pointé par le chemin d'accès. 'A1' par défaut
   */
  public String getStudentListShift() {
    return this.correctionTemplate.getStudentListShift();
  }
  
  @Pure
  public Collection<StudentSheet> getStudentSheets() {
    return this.studentSheets;
  }
  
  public void setStudentSheets(final Collection<StudentSheet> studentSheets) {
    this.studentSheets = studentSheets;
  }
}
