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
import fr.istic.tools.scanexam.services.ExamSingleton;
import fr.istic.tools.scanexam.services.Service;
import fr.istic.tools.scanexam.utils.Tuple3;
import java.io.ByteArrayInputStream;
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
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

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
   * @params path L'emplacement de sauvegarde du fichier.
   */
  public Object saveCorrectionTemplate(final String path) {
    return null;
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
      int index = 0;
      for (final StudentSheet sheet : studentSheets) {
        {
          final Page examPage = ExamSingleton.getPage(index);
          index++;
          for (int i = 0; (i < (examPage.getQuestions().size() + 1)); i++) {
            sheet.getGrades().add(CoreFactory.eINSTANCE.createGrade());
          }
        }
      }
      return true;
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        return false;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
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
    final Function1<GradeEntry, Boolean> _function = new Function1<GradeEntry, Boolean>() {
      public Boolean apply(final GradeEntry step) {
        int _id = step.getId();
        return Boolean.valueOf((_id == gradeEntryId));
      }
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
      final Function1<GradeEntry, Boolean> _function = new Function1<GradeEntry, Boolean>() {
        public Boolean apply(final GradeEntry step) {
          int _id = step.getId();
          return Boolean.valueOf((_id == gradeEntryId));
        }
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
  
  public int getAbsolutePageNumber(final int studentId, final int offset) {
    final Function1<StudentSheet, Boolean> _function = new Function1<StudentSheet, Boolean>() {
      public Boolean apply(final StudentSheet x) {
        int _id = x.getId();
        return Boolean.valueOf((_id == studentId));
      }
    };
    final Integer pageId = IterableExtensions.<StudentSheet>findFirst(this.studentSheets, _function).getPosPage().get(0);
    InputOutput.<String>print(("\nPageid = " + Integer.valueOf(((pageId).intValue() + offset))));
    return ((pageId).intValue() + offset);
  }
  
  /**
   * Passe au prochaine etudiant dans les StudentSheet
   */
  public int nextStudent() {
    int _xifexpression = (int) 0;
    int _size = this.studentSheets.size();
    boolean _lessThan = ((this.currentSheetIndex + 1) < _size);
    if (_lessThan) {
      _xifexpression = this.currentSheetIndex++;
    }
    return _xifexpression;
  }
  
  /**
   * Passe au etudiant précédent dans les StudentSheet
   */
  public int previousStudent() {
    int _xifexpression = (int) 0;
    if ((this.currentSheetIndex > 0)) {
      _xifexpression = this.currentSheetIndex--;
    }
    return _xifexpression;
  }
  
  public int nextPage() {
    int _xifexpression = (int) 0;
    int _length = ((Object[])Conversions.unwrapArray(ExamSingleton.instance.getPages(), Object.class)).length;
    boolean _lessThan = ((this.pageIndex + 1) < _length);
    if (_lessThan) {
      _xifexpression = this.pageIndex++;
    }
    return _xifexpression;
  }
  
  public int previousPage() {
    int _xifexpression = (int) 0;
    if ((this.pageIndex > 0)) {
      _xifexpression = this.pageIndex--;
    }
    return _xifexpression;
  }
  
  /**
   * Renomme l'étudiant
   * @param name le nouveau nom de l'étudiant
   */
  public void renameStudent(final String name) {
    StudentSheet _get = ((StudentSheet[])Conversions.unwrapArray(this.studentSheets, StudentSheet.class))[this.currentSheetIndex];
    _get.setStudentName(name);
  }
  
  /**
   * Index de la page courante du modèle d'exam
   */
  protected Page getCurrentPage() {
    return ExamSingleton.getPage(this.pageIndex);
  }
  
  public Question getCurrentQuestion() {
    final Function1<Question, Boolean> _function = new Function1<Question, Boolean>() {
      public Boolean apply(final Question x) {
        int _id = x.getId();
        return Boolean.valueOf((_id == ServiceGraduation.this.currentQuestionIndex));
      }
    };
    return IterableExtensions.<Question>findFirst(this.getCurrentPage().getQuestions(), _function);
  }
  
  public int nextQuestion() {
    int _xifexpression = (int) 0;
    int _size = this.getCurrentPage().getQuestions().size();
    boolean _lessThan = ((this.currentQuestionIndex + 1) < _size);
    if (_lessThan) {
      _xifexpression = this.currentQuestionIndex++;
    }
    return _xifexpression;
  }
  
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
        final Function1<Question, Boolean> _function = new Function1<Question, Boolean>() {
          public Boolean apply(final Question question) {
            int _id = question.getId();
            return Boolean.valueOf((_id == id));
          }
        };
        final Question question = IterableExtensions.<Question>findFirst(page.getQuestions(), _function);
        if ((question != null)) {
          this.pageIndex = page.getId();
          this.currentQuestionIndex = question.getId();
        }
      }
    }
  }
  
  public int indexOfQuestions(final int indexpage, final int indexquestion) {
    int _xblockexpression = (int) 0;
    {
      int indexQuestion = 0;
      for (int i = 0; (i < (indexpage - 1)); i++) {
        int _indexQuestion = indexQuestion;
        int _size = this.creationTemplate.getExam().getPages().get(i).getQuestions().size();
        indexQuestion = (_indexQuestion + _size);
      }
      int _indexQuestion = indexQuestion;
      indexQuestion = (_indexQuestion + indexquestion);
      _xblockexpression = indexQuestion;
    }
    return _xblockexpression;
  }
  
  /**
   * Ajoute une (n as GradeItem)entrée à la note d'une question d'une copie
   * @param questionId l'ID de la question à laquelle ajouter l'entrée
   * @param l'ID de l'entrée dans l'Examen
   * @return boolean indique si les points on bien ete attribuer
   */
  public boolean addGradeEntry(final int questionId, final int gradeEntryId) {
    final Function1<GradeEntry, Boolean> _function = new Function1<GradeEntry, Boolean>() {
      public Boolean apply(final GradeEntry entry) {
        int _id = entry.getId();
        return Boolean.valueOf((_id == gradeEntryId));
      }
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
  public boolean removeGradeEntry(final int questionId, final int gradeEntryId) {
    boolean _xblockexpression = false;
    {
      final EList<GradeEntry> entries = (((StudentSheet[])Conversions.unwrapArray(this.studentSheets, StudentSheet.class))[this.currentSheetIndex]).getGrades().get(questionId).getEntries();
      final Function1<GradeEntry, Boolean> _function = new Function1<GradeEntry, Boolean>() {
        public Boolean apply(final GradeEntry entry) {
          int _id = entry.getId();
          return Boolean.valueOf((_id == gradeEntryId));
        }
      };
      final GradeEntry gradeEntry = IterableExtensions.<GradeEntry>findFirst(entries, _function);
      _xblockexpression = entries.remove(gradeEntry);
    }
    return _xblockexpression;
  }
  
  /**
   * @param l'ID de la question à laquelle récupérer la liste d'entrées
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
      final Function1<GradeEntry, Integer> _function = new Function1<GradeEntry, Integer>() {
        public Integer apply(final GradeEntry entry) {
          return Integer.valueOf(entry.getId());
        }
      };
      _xblockexpression = ListExtensions.<GradeEntry, Integer>map(sheet.getGrades().get(questionId).getEntries(), _function);
    }
    return _xblockexpression;
  }
  
  /**
   * @param l'ID de la question à laquelle récupérer la liste d'entrées
   * @return une liste d'ID d'entrées pour la question de l'examen dont l'ID est <i>questionId</i>
   */
  public List<Tuple3<Integer, String, Float>> getQuestionGradeEntries(final int questionId) {
    GradeScale _gradeScale = this.getQuestion(questionId).getGradeScale();
    boolean _tripleNotEquals = (_gradeScale != null);
    if (_tripleNotEquals) {
      final Function1<GradeEntry, Tuple3<Integer, String, Float>> _function = new Function1<GradeEntry, Tuple3<Integer, String, Float>>() {
        public Tuple3<Integer, String, Float> apply(final GradeEntry entry) {
          return Tuple3.<Integer, String, Float>of(Integer.valueOf(entry.getId()), entry.getHeader(), Float.valueOf(entry.getStep()));
        }
      };
      return ListExtensions.<GradeEntry, Tuple3<Integer, String, Float>>map(this.getQuestion(questionId).getGradeScale().getSteps(), _function);
    }
    return List.<Tuple3<Integer, String, Float>>of();
  }
  
  /**
   * Ajoute la note a la question courante
   */
  public Grade setGrade(final Grade note) {
    return (((StudentSheet[])Conversions.unwrapArray(this.studentSheets, StudentSheet.class))[this.currentSheetIndex]).getGrades().set(this.indexOfQuestions(this.pageIndex, this.currentQuestionIndex), note);
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
  
  public ByteArrayInputStream getEditionPdfInputStream() {
    final byte[] decoded = Base64.getDecoder().decode(this.creationTemplate.getEncodedDocument());
    return new ByteArrayInputStream(decoded);
  }
  
  @Pure
  public Collection<StudentSheet> getStudentSheets() {
    return this.studentSheets;
  }
  
  public void setStudentSheets(final Collection<StudentSheet> studentSheets) {
    this.studentSheets = studentSheets;
  }
}
